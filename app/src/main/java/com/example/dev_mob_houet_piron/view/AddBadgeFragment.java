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
import com.example.dev_mob_houet_piron.presenter.AddBadgePresenter;
import com.example.dev_mob_houet_piron.view.interfaces.IFragmentDisplayer;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import static com.example.dev_mob_houet_piron.view.PlaceChooserActivity.CHOOSER_IDS;

public class AddBadgeFragment extends Fragment {

    private final int REQUEST_CODE_CACHE_CHOOSER = 0;
    private AddBadgePresenter presenter;
    private Context context;
    private IFragmentDisplayer fragmentDisplayer;

    private String title;
    private String description;
    private List<String> placesId;

    private TextInputEditText titleInput;
    private TextInputEditText descriptionInput;
    private Button cacheChooserBtn;
    private Button saveBtn;

    public static AddBadgeFragment newInstance() {
        AddBadgeFragment fragment = new AddBadgeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_badge, container, false);
        this.presenter = new AddBadgePresenter();

        cacheChooserBtn = view.findViewById(R.id.place_choose_btn);
        saveBtn = view.findViewById(R.id.save_badge_btn);
        titleInput = view.findViewById(R.id.add_badge_name);
        descriptionInput = view.findViewById(R.id.add_badge_description);

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
        cacheChooserBtn.setOnClickListener(v -> startActivityForResult(PlaceChooserActivity.newIntent(context), REQUEST_CODE_CACHE_CHOOSER));
        saveBtn.setOnClickListener(v -> sendData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CACHE_CHOOSER) {
            Log.d("AddBadge", "End of activity");
            this.placesId = data.getStringArrayListExtra(CHOOSER_IDS);
        }
    }

    private void sendData() {
        try{
            Log.d("AddBadge", "Selected places: " + this.placesId);
            this.title = verifyInputValidity(titleInput.getText(), true);
            this.description = verifyInputValidity(descriptionInput.getText(), true);
            presenter.addBadge(this.title, this.description, this.placesId);
            fragmentDisplayer.loadFragment(BadgeListFragment.newInstance());
        } catch(IllegalArgumentException e) {
            Toast.makeText(getContext(), getResources().getString(R.string.addBadgeFragment_error), Toast.LENGTH_LONG).show();
            Log.d("AddBadge", "Invalid input found !");
        }
    }

    private String verifyInputValidity(Editable input, boolean notNull) {
        if(input == null || ((notNull && input.toString().equals(""))))
            throw new IllegalArgumentException("Invalid input found!");
        return input.toString();
    }

}