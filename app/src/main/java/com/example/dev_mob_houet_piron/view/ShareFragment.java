package com.example.dev_mob_houet_piron.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.SharePresenter;

public class ShareFragment extends Fragment {

    private SharePresenter presenter;
    private Button downloadBtn;
    private Button uploadBtn;
    private EditText codeInput;

    public static ShareFragment newInstance(SharePresenter presenter) {
        ShareFragment fragment = new ShareFragment();
        fragment.presenter = presenter;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        codeInput = view.findViewById(R.id.code_input);
        downloadBtn = view.findViewById(R.id.download_button);
        uploadBtn = view.findViewById(R.id.upload_button);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        downloadBtn.setOnClickListener(v -> presenter.download(codeInput.getText().toString()));
        uploadBtn.setOnClickListener(v -> presenter.upload());
    }

}