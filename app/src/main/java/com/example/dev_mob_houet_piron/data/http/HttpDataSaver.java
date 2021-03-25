package com.example.dev_mob_houet_piron.data.http;

import com.example.dev_mob_houet_piron.data.interfaces.ISharePackageSaverCallback;
import com.example.dev_mob_houet_piron.model.SharePackage;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDataSaver implements Runnable {

    private static final String REQUESTHANDLER_URL = "https://192.168.128.13/~e190046/DevMob/JsonFileHandler";
    private final SharePackage sharePackage;
    private final ISharePackageSaverCallback callback;
    private String errorMessage;

    public HttpDataSaver(SharePackage sharePackage, ISharePackageSaverCallback callback){
        this.sharePackage = sharePackage;
        this.callback = callback;
    }

    @Override
    public void run() {
        String code = Save();
        callback.callback(code, errorMessage);
    }

    /**
     * Stocke un objet du type "SharePackage" sur Dartagnan, et retourne
     * l'id attribué à cet objet. Cet id sera utile pour télécharger
     * le SharePackage ainsi uploadé
     *
     * ATTENTION: Nécéssite d'être connecté au VPN de l'helmo, renvoie un message d'erreur
     * à son callback si ce n'est pas le cas.
     *
     * Il est préférable de lancer cette méthode depuis un thread car le délai peut être long
     *
     * @return l'id de l'objet stocké sur le serveur.
     */
    public String Save() {
        try {
            String packageAsJSON = parsePackageAsJSON(sharePackage);
            byte[] placesAsPOSTParameter = String.format("places=%s", packageAsJSON).getBytes();
            HttpURLConnection con = initConnection(placesAsPOSTParameter.length);
            return (sendPlaces(con, placesAsPOSTParameter))? readResponse(con) : null;
        } catch (IOException ex) {
            this.errorMessage = "Connection au service de sauvegarde impossible";
            return null;
        }
    }

    private String readResponse(HttpURLConnection con) {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))){
           return in.readLine();
        } catch (IOException e) {
            this.errorMessage = "Une erreur est survenue lors de la récupération de l'id de partage";
            return null;
        }
    }

    private boolean sendPlaces(HttpURLConnection con, byte[] placesAsPOSTParameter) {
        try(DataOutputStream os = new DataOutputStream(con.getOutputStream())) {
            os.write(placesAsPOSTParameter);
            return true;
        } catch (IOException e) {
            this.errorMessage = "Impossible de sauvegarder les données sur le service de sauvegarde";
            return false;
        }
    }

    private HttpURLConnection initConnection(int bufferSize) throws IOException {
        bypassCertificateValidation();
        URL url = new URL(REQUESTHANDLER_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setDoOutput( true );
        con.setInstanceFollowRedirects( false );
        con.setRequestMethod( "POST" );
        con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty( "charset", "utf-8");
        con.setRequestProperty( "Content-Length", Integer.toString(bufferSize));
        con.setUseCaches( false );
        con.connect();
        return con;
    }

    private void bypassCertificateValidation() {
        //Nécéssaire pour valider de force le certificat de l'helmo
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("192.168.128.13"));
    }

    private String parsePackageAsJSON(SharePackage sharePackage) {
        Gson gson = new Gson();
        return gson.toJson(sharePackage);
    }
}
