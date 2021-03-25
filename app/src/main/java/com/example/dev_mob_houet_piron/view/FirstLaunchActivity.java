package com.example.dev_mob_houet_piron.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.FirstLaunchPresenter;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayLandingPage;
import com.google.android.material.textfield.TextInputEditText;

public class FirstLaunchActivity extends AppCompatActivity implements View.OnClickListener, IDisplayLandingPage {

    private static final int REQUEST_ACCESS_FINE = 444719;
    private FirstLaunchPresenter firstLaunchPresenter;
    private TextInputEditText name;

    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);

        findViewById(R.id.firstLaunch_takePicture).setOnClickListener(this);
        findViewById(R.id.firstLaunch_send).setOnClickListener(this);
        name = findViewById(R.id.firstLaunch_nameForm);
        firstLaunchPresenter = new FirstLaunchPresenter(this);

        firstLaunchPresenter.checkFirstApplicationUse();

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.firstLaunch_takePicture :
                checkPermissions();
                break;
            case R.id.firstLaunch_send :
                firstLaunchPresenter.storeUserInformations(this.name.getText().toString());
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            for(int i = 0; i < permissions.length; i++) {
                if (grantResults.length > 0 && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.firstLaunch_permissionNotGrantedToast), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            launchCameraActivity();
        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                launchCameraActivity();
            } else {
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE);
            }
        }
    }

    private void launchCameraActivity() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
