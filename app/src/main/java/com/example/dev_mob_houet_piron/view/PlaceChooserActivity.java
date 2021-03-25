package com.example.dev_mob_houet_piron.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.PlaceListPresenter;
import com.example.dev_mob_houet_piron.view.adapter.CheckablePlaceAdapter;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayPlaceList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PlaceChooserActivity extends FragmentActivity implements IDisplayPlaceList {
    //On met un nom dont on est certain qu'il sera unique au seins du système android
    //Une bonne pratique est de mettre l'identifiant de l'app
    static String CHOOSER_IDS = "com.example.dev_mob_houet_piron.view.chooser_ids";

    private FloatingActionButton chooseValidationBtn;
    private PlaceListPresenter presenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_chooser);
        chooseValidationBtn = findViewById(R.id.place_choose_validation);
        recyclerView = findViewById(R.id.place_chooser_place_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CheckablePlaceAdapter(this, null));

        this.presenter = new PlaceListPresenter(this);
        presenter.loadPlaces();
    }

    @Override
    public void onStart() {
        super.onStart();
        chooseValidationBtn.setOnClickListener(v -> setSelectedPlacesResult(presenter.getSelectedPlaces()));
    }

    private void setSelectedPlacesResult(List<String> placeIds) {
        setResult(Activity.RESULT_OK, new Intent().putStringArrayListExtra(CHOOSER_IDS, (ArrayList<String>)placeIds));
        finish();
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, PlaceChooserActivity.class);
    }

    @Override
    public void loadView() {
        recyclerView.setAdapter(new CheckablePlaceAdapter(this, presenter));
    }
}
