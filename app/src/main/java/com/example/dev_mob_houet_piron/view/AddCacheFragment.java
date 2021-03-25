package com.example.dev_mob_houet_piron.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.AddPlacePresenter;
import com.example.dev_mob_houet_piron.view.interfaces.IFragmentDisplayer;
import com.google.android.material.textfield.TextInputEditText;

public class AddCacheFragment extends Fragment {

    private final int REQUEST_CODE_COORDINATE_PICKER = 0;
    private AddPlacePresenter presenter;
    private Context context;
    private IFragmentDisplayer fragmentDisplayer;

    private double latitude;
    private double longitude;
    private String name;
    private String description;

    private TextInputEditText nameInput;
    private TextInputEditText descriptionInput;
    private Button placePickerBtn;
    private Button saveBtn;

    public static AddCacheFragment newInstance() {
        AddCacheFragment fragment = new AddCacheFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cache, container, false);
        this.presenter = new AddPlacePresenter();

        placePickerBtn = view.findViewById(R.id.place_pick_btn);
        saveBtn = view.findViewById(R.id.save_btn);
        nameInput = view.findViewById(R.id.add_cache_name);
        descriptionInput = view.findViewById(R.id.add_cache_description);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentDisplayer = (IFragmentDisplayer)context;
    }

    @Override
    public void onStart() {
        super.onStart();
        placePickerBtn.setOnClickListener(v -> startActivityForResult(PlacePickerActivity.newIntent(context), REQUEST_CODE_COORDINATE_PICKER));
        saveBtn.setOnClickListener(v -> sendData());
    }

    private void sendData() {
        try{
            this.name = verifyInputValidity(nameInput.getText(), true);
            this.description = verifyInputValidity(descriptionInput.getText(), true);
            presenter.addPlace(this.longitude, this.latitude, this.name, this.description);
            fragmentDisplayer.loadFragment(MapFragment.newInstance());
        } catch(IllegalArgumentException e) {
            Toast.makeText(getContext(), getResources().getString(R.string.addBadgeFragment_error), Toast.LENGTH_LONG).show();
            Log.d("AddCache", "Veuillez compléter tout les champs");
        }
    }

    private String verifyInputValidity(Editable input, boolean notNull) {
        if(input == null || ((notNull && input.toString().equals(""))))
            throw new IllegalArgumentException("Invalid input found!");
        return input.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_COORDINATE_PICKER) {
            this.latitude = data.getDoubleExtra(PlacePickerActivity.PICKER_LATITUDE, 0);
            this.longitude = data.getDoubleExtra(PlacePickerActivity.PICKER_LONGITUDE, 0);
        }
    }

}