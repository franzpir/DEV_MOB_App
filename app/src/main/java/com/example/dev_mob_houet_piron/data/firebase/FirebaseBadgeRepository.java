package com.example.dev_mob_houet_piron.data.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dev_mob_houet_piron.model.Badge;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseBadgeRepository {

    private static FirebaseBadgeRepository instance;
    private FirebaseFirestore firestore;
    private MutableLiveData<List<Badge>> observableBadges = new MutableLiveData<>();

    private FirebaseBadgeRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Retourne tous les badges_success contenus dans Firebase sous forme de liste observable d'objets "Badge"
     * @return Liste observable contenant tous les badges_success présents dans Firebase
     */
    public LiveData<List<Badge>> getBadges() {
        firestore.collection("Badge").addSnapshotListener((snapshot, ex) -> {
            if(ex == null && snapshot != null) {
                List<Badge> badges = getBadgesFromSnapshot(snapshot);
                observableBadges.setValue(badges);
            }
        });
        return observableBadges;
    }

    /**
     * Retourne un badge contenu dans Firebase sur base de son id
     * @param badgeId l'id du badge que l'on souhaite obtenir
     * @return un objet Badge observable
     */
    public LiveData<Badge> getBadge(String badgeId) {
        MutableLiveData<Badge> observableBadge = new MutableLiveData<>();
        firestore.collection("Badge").whereEqualTo("id", badgeId).get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    loadBadgeFromDocument(document, observableBadge);
                });
        return observableBadge;
    }

    /**
     * Retourne l'instance du FirebaseBadgeRepository
     * @return l'instance du FirebaseBadgeRepository
     */
    public static FirebaseBadgeRepository getInstance() {
        if(instance == null) {
            instance = new FirebaseBadgeRepository();
        }
        return instance;
    }

    private List<Badge> getBadgesFromSnapshot(QuerySnapshot snapshot) {
        List<Badge> badges = new ArrayList<>();
        List<DocumentSnapshot> documents = snapshot.getDocuments();
        for(DocumentSnapshot document : documents) {
            badges.add(loadBadgeFromDocument(document));
        }
        return badges;
    }

    private void loadBadgeFromDocument(DocumentSnapshot document, MutableLiveData<Badge> observableBadge) {
        Badge badge = new Badge(document.get("title").toString(), document.get("description").toString());
        badge.setId(document.get("id").toString());
        getCachesIdsFor(document.getId()).observeForever(caches -> {
            badge.setCachesIds(caches);
            observableBadge.setValue(badge);
        });
    }

    private Badge loadBadgeFromDocument(DocumentSnapshot document) {
        Badge badge = new Badge(document.get("title").toString(), document.get("description").toString());
        badge.setId(document.get("id").toString());
        getCachesIdsFor(document.getId()).observeForever(caches -> {
            badge.setCachesIds(caches);
        });
        return badge;
    }

    private LiveData<List<String>> getCachesIdsFor(String id) {
        MutableLiveData<List<String>> observableIds = new MutableLiveData<>();
        List<String> ids = new ArrayList<>();
        firestore.collection("Badge/" + id + "/PlacesId").addSnapshotListener((snapshot, ex) -> {
            if(ex == null && snapshot != null) {
                List<DocumentSnapshot> documents = snapshot.getDocuments();
                for(DocumentSnapshot document : documents) {
                    ids.add(document.get("id").toString());
                }
                observableIds.setValue(ids);
            }
        });
        return observableIds;
    }
}
