package com.example.dev_mob_houet_piron.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.data.room.repository.PersonRepository;
import com.example.dev_mob_houet_piron.model.Person;
import com.example.dev_mob_houet_piron.view.FirstLaunchActivity;
import com.example.dev_mob_houet_piron.view.MainActivity;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayLandingPage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstLaunchPresenter {

    private Context context;
    private IDisplayLandingPage landingPage;
    private boolean isFirstLaunch = true;

    public FirstLaunchPresenter(IDisplayLandingPage landingPage) {
        this.context = (Context)landingPage;
        this.landingPage = landingPage;
    }

    public void checkFirstApplicationUse() {
        LiveData<Person> person = PersonRepository.getInstance().getPerson("user1");
        person.observe((LifecycleOwner) context, new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person givenPerson) {
                if(givenPerson != null)
                    launchMainActivity();
                person.removeObserver(this);
            }
        });
    }

    public void storeUserInformations(String name) {
        if(name.length() == 0 || name == null) {
            Toast.makeText(context, R.string.firstLaunch_errorOnName, Toast.LENGTH_LONG).show();
        } else {
            Person person = new Person();
            person.setId("user1");
            person.setName(name);
            person.setRegisterDate(new Date());
            PersonRepository.getInstance().insertPerson(person);
            Toast.makeText(context, context.getResources().getString(R.string.firstLaunch_helloToast) + " " + name, Toast.LENGTH_LONG).show();
            launchMainActivity();
        }
    }

    private void launchMainActivity() {
        landingPage.goToMain();
    }
}
