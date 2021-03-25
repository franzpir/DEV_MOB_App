package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.http.HttpDataLoader;
import com.example.dev_mob_houet_piron.data.http.HttpDataSaver;
import com.example.dev_mob_houet_piron.data.room.repository.LocalBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.model.SharePackage;
import com.example.dev_mob_houet_piron.view.dialog.ISimpleDialog;

public class SharePresenter {

    private final ISimpleDialog loadingDialog;
    private final ISimpleDialog successDialog;
    private final ISimpleDialog errorDialog;

    public SharePresenter(ISimpleDialog loadingDialog, ISimpleDialog successDialog, ISimpleDialog errorDialog){
        this.loadingDialog = loadingDialog;
        this.successDialog = successDialog;
        this.errorDialog = errorDialog;
    }

    public void download(String code) {
        Thread t = new Thread(new HttpDataLoader(code, (sharePackage, errorMessage) -> {
            loadingDialog.dismissDialog();
            handleLoadResponse(sharePackage, errorMessage);
        }));
        loadingDialog.displayDialog("Téléchargement des caches...", false);
        t.start();
    }

    public void upload() {
        SharePackage sharePackage = new SharePackage(LocalPlaceRepository.getInstance().getPlacesSync(), LocalBadgeRepository.getInstance().getBadgesSync());
        Thread t = new Thread(new HttpDataSaver(sharePackage, (code, errorMessage) -> {
            loadingDialog.dismissDialog();
            handleSaveResponse(code, errorMessage);
        }));
        loadingDialog.displayDialog("Sauvegarde des caches...", false);
        t.start();
    }

    private void handleLoadResponse(SharePackage sharePackage, String errorMessage) {
        if(errorMessage != null)
            errorDialog.displayDialog("ERREUR: " + errorMessage, true);
        else {
            successDialog.displayDialog("Téléchargement effectué avec succès!", true);
            LocalPlaceRepository.getInstance().addAll(sharePackage.getPlaces());
            LocalBadgeRepository.getInstance().addAll(sharePackage.getBadges());
        }
    }

    private void handleSaveResponse(String code, String errorMessage) {
        if (errorMessage == null)
            successDialog.displayDialog("Sauvegarde effectuée avec succès, votre code de partage est : " + code, true);
        else
            errorDialog.displayDialog("ERREUR: " + errorMessage, true);
    }
}
