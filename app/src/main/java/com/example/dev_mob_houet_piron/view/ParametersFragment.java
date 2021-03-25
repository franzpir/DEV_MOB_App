package com.example.dev_mob_houet_piron.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.BadgeListPresenter;
import com.example.dev_mob_houet_piron.presenter.ParametersPresenter;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayParameters;
import com.example.dev_mob_houet_piron.view.interfaces.IFragmentDisplayer;

public class ParametersFragment extends Fragment implements IDisplayParameters {

    private ParametersPresenter presenter;
    private EditText userName;
    private Button takePicture;
    private Button send;
    private Context context;

    public static ParametersFragment newInstance() {
        ParametersFragment fragment = new ParametersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters, container, false);

        this.userName = view.findViewById(R.id.parametersFragment_editText);
        this.takePicture = view.findViewById(R.id.parametersFragment_takePicture);
        this.send = view.findViewById(R.id.parametersFragment_send);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.presenter = new ParametersPresenter(this);
        presenter.loadPerson();
    }

    @Override
    public void setCurrentValues(String name) {
        this.userName.setText(name);
    }

    @Override
    public void onStart() {
        super.onStart();
        takePicture.setOnClickListener(v -> context.startActivity(CameraActivity.newIntent(context)));
        send.setOnClickListener(v -> {
            if(presenter.updateUser(this.userName.getText().toString()))
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.parametersFragment_informationsSaved), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getContext(), R.string.firstLaunch_errorOnName, Toast.LENGTH_LONG).show();
        });
    }
}