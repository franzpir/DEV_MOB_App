package com.example.dev_mob_houet_piron.data.http;

import com.example.dev_mob_houet_piron.data.interfaces.ISharePackageLoaderCallback;
import com.example.dev_mob_houet_piron.model.SharePackage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class HttpDataLoader implements Runnable {

    private static final String BASE_URL = "https://192.168.128.13/~e190046/DevMob/SharedFiles/";
    private static final String URL_SUFFIX = ".json";
    private final String code;
    private final ISharePackageLoaderCallback callback;
    private String message;

    public HttpDataLoader(String code, ISharePackageLoaderCallback callback){
        this.code = code;
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.callback(Load(), this.message);
    }

    /**
     * Charge un SharePackage stocké sur Dartagnan sur base de son id.
     * Ce SharePackage contient des places et des badges_success, il est stocké sur Dartagnan
     * sous forme de fichier .json
     *
     * ATTENTION: Nécéssite d'être connecté au VPN de l'helmo, renvoie un message d'erreur
     * à son callback si ce n'est pas le cas.
     *
     * Il est préférable de lancer cette méthode depuis un thread car le délai peut être long
     *
     * @return objet du type "SharePackage" contenant les badges_success et les places, sous réserve que
     * l'id entré soit valide et que la connexion au serveur réussisse
     */
    public SharePackage Load() {
        bypassCertificateValidation();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(getStreamFromURL(getFullPath())))){
            TimeUnit.SECONDS.sleep(1);
            Gson gson = new Gson();
            return gson.fromJson(in, new TypeToken<SharePackage>(){}.getType());
        } catch(FileNotFoundException e){
            this.message = "Le code fourni est introuvable";
        } catch (IOException ex){
            this.message = "Impossible de se connecter au serveur de partage, êtes-vous bien connecté au VPN de l'HELMo?";
        } catch (Exception e) {
            this.message = "Téléchargement impossible";
        }
        return null;
    }

    private InputStream getStreamFromURL(String fullPath) throws IOException{
        URL url = new URL(fullPath);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(1000);
        return connection.getInputStream();
    }

    private String getFullPath() {
        return BASE_URL + code + URL_SUFFIX;
    }

    private static void bypassCertificateValidation() {
        //Nécéssaire pour valider de force le certificat de l'helmo
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("192.168.128.13"));
    }
}
