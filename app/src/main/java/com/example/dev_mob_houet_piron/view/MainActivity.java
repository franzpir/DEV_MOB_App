package com.example.dev_mob_houet_piron.view;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.data.room.repository.LocalBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.presenter.ParametersPresenter;
import com.example.dev_mob_houet_piron.presenter.SharePresenter;
import com.example.dev_mob_houet_piron.view.dialog.SimpleDialog;
import com.example.dev_mob_houet_piron.view.interfaces.IFragmentDisplayer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IFragmentDisplayer {

    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNav;
    private Map<Integer, Fragment> redirections = new HashMap<Integer, Fragment>(){{
        put(R.id.navigation_profile, ProfileFragment.newInstance());
        put(R.id.navigation_share, ShareFragment.newInstance(new SharePresenter(
                new SimpleDialog(MainActivity.this, R.layout.dialog_loading),
                new SimpleDialog(MainActivity.this, R.layout.dialog_success),
                new SimpleDialog(MainActivity.this, R.layout.dialog_error))));
        put(R.id.navigation_map, MapFragment.newInstance());
        put(R.id.navigation_badges, BadgeListFragment.newInstance());
        put(R.id.navigation_settings, ParametersFragment.newInstance());
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalPlaceRepository.getInstance();
        LocalBadgeRepository.getInstance();

        loadFragment(redirections.get(R.id.navigation_map));

        initBottomNav();

    }

    private void initBottomNav() {
        bottomNav = findViewById(R.id.activity_mainpage_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navItemSelectedListener);
        bottomNav.setSelectedItemId(R.id.navigation_map);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener = item -> {
        try {
            loadFragment(redirections.get(item.getItemId()));
        } catch (Exception e) {
            Log.e(TAG, e.getStackTrace().toString());
        }
        return true;
    };

    public void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_mainpage_fragment_container, fragment).commit();
    }
}