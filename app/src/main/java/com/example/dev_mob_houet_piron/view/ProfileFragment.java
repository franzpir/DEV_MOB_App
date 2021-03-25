package com.example.dev_mob_houet_piron.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.BadgeListPresenter;
import com.example.dev_mob_houet_piron.presenter.ProfilePresenter;
import com.example.dev_mob_houet_piron.view.adapter.BadgeAdapter;
import com.example.dev_mob_houet_piron.view.adapter.PlaceAdapter;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayProfileInfo;

import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements IDisplayProfileInfo {

    private ProfilePresenter presenter;
    private TextView username, registerDate, foundedCaches;
    private CircleImageView profilePicture;
    private RecyclerView recyclerView;
    private Context context;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.presenter = new ProfilePresenter(this);

        this.presenter = new ProfilePresenter(this);
        this.username = view.findViewById(R.id.user_profile_name);
        this.registerDate = view.findViewById(R.id.user_profile_registerDate);
        this.profilePicture = view.findViewById(R.id.user_profile_picture);
        this.foundedCaches = view.findViewById(R.id.user_profile_foundedCaches);

        this.recyclerView = view.findViewById(R.id.user_profile_foundedCachesRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.presenter = new ProfilePresenter(this);
        presenter.loadPerson();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void showProfile(String name, int nbrOfFoundCaches, Date registerDate) {
        initializeProfilePicture();
        initializeFoundedCaches(nbrOfFoundCaches);
        this.username.setText(name);
        this.registerDate.setText(registerDate.toString());
    }

    @Override
    public void loadView() {
        Log.d("ProfileFragment", "loadView called");
        recyclerView.setAdapter(new PlaceAdapter(context, presenter));
    }


    private void initializeFoundedCaches(int foundedCaches) {
        switch(foundedCaches) {
            case 0:
                this.foundedCaches.setText("Vous n'avez pas encore trouvé de géocache !");
                break;
            case 1:
                this.foundedCaches.setText(foundedCaches + " Géocache trouvée");
                break;
            default:
                this.foundedCaches.setText(foundedCaches + " Géocaches trouvées");
                break;
        }
    }

    private void initializeProfilePicture() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        File image = new File(sharedPreferences.getString("path", ""));
        if(image.exists()) {
            Bitmap bitmapImage = BitmapFactory.decodeFile(image.getAbsolutePath());
            this.profilePicture.setImageBitmap(bitmapImage);
            this.profilePicture.setRotation(90);
        }
    }
}