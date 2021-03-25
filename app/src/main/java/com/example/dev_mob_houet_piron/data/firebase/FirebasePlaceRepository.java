package com.example.dev_mob_houet_piron.data.firebase;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dev_mob_houet_piron.model.Place;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebasePlaceRepository {

    private static FirebasePlaceRepository instance;
    private FirebaseFirestore firestore;
    private MutableLiveData<List<Place>> observablePlaces = new MutableLiveData<>();

    private FirebasePlaceRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Retourne toutes les places contenues dans Firebase sous forme de liste observable d'objets "Place"
     * @return Liste observable contenant toutes les places présentes dans Firebase
     */
    public LiveData<List<Place>> getPlaces() {
        firestore.collection("PlacesV2").addSnapshotListener((snapshot, ex) -> {
            if(ex == null && snapshot != null) {
                List<Place> places = getPlacesFromSnapshot(snapshot);
                observablePlaces.setValue(places);
            } else if (ex != null) {
                Log.e("Firebase", ex.toString());
            }
        });
        return observablePlaces;
    }

    /**
     * Retourne les objets "Place" ayant les ids précisés en paramètre contenus dans Firebase sous forme de liste observable
     * @param ids les ids des places qu'on souhaite obtenir
     * @return Liste observable contenant les places ayant les ids indiqués en paramètre
     */
    public LiveData<List<Place>> getPlaces(List<String> ids) {
        MutableLiveData<List<Place>> observablePlacesBasedOnIds = new MutableLiveData<>();
        observablePlacesBasedOnIds.setValue(new ArrayList<>());
        firestore.collection("PlacesV2").whereIn("id", ids).get().addOnCompleteListener(places -> {
            List<Place> foundedPlaces = new ArrayList<>();
            for(DocumentSnapshot document : places.getResult().getDocuments()) {
                Place place = document.toObject(Place.class);
                foundedPlaces.add(place);
            }
            observablePlacesBasedOnIds.setValue(foundedPlaces);
        });
        return observablePlacesBasedOnIds;
    }

    /**
     * Retourne une place contenue dans Firebase sur base de son id
     * @param cacheId l'id de la cahce que l'on souhaite obtenir
     * @return un objet Place observable
     */
    public LiveData<Place> getPlace(String cacheId) {
        MutableLiveData<Place> observablePlace = new MutableLiveData<>();
        firestore.collection("PlacesV2").whereEqualTo("id", cacheId).get()
                .addOnCompleteListener(task -> observablePlace.setValue(task.getResult().getDocuments().get(0).toObject(Place.class)));
        return observablePlace;
    }

    /**
     * Retourne l'instance du FirebasePlaceRepository
     * @return l'instance du FirebasePlaceRepository
     */
    public static FirebasePlaceRepository getInstance() {
        if(instance == null)
            instance = new FirebasePlaceRepository();
        return instance;
    }

    private List<Place> getPlacesFromSnapshot(QuerySnapshot snapshot) {
        List<Place> places = new ArrayList<>();
        List<DocumentSnapshot> documents = snapshot.getDocuments();
        for(DocumentSnapshot document : documents){
            Place place = document.toObject(Place.class);
            if(place != null) {
                places.add(place);
            }
        }
        return places;
    }
}
