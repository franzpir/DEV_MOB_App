package com.example.dev_mob_houet_piron.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.BadgeListPresenter;
import com.example.dev_mob_houet_piron.view.adapter.BadgeAdapter;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayBadgeList;
import com.example.dev_mob_houet_piron.view.interfaces.IFragmentDisplayer;

public class BadgeListFragment extends Fragment implements IDisplayBadgeList {

    private BadgeListPresenter presenter;
    private RecyclerView officialRecyclerView;
    private RecyclerView unOfficialRecyclerView;
    private Context context;
    private AppCompatButton addBadgeBtn;
    private IFragmentDisplayer fragmentDisplayer;

    public static BadgeListFragment newInstance() {
        BadgeListFragment fragment = new BadgeListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_badge_list, container, false);

        addBadgeBtn = view.findViewById(R.id.badge_list_add_badge);

        officialRecyclerView = view.findViewById(R.id.badgesFragment_officialBadgesRV);
        officialRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        unOfficialRecyclerView = view.findViewById(R.id.badgesFragment_unofficialBadgesRV);
        unOfficialRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addBadgeBtn.setOnClickListener(v -> fragmentDisplayer.loadFragment(AddBadgeFragment.newInstance()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.presenter = new BadgeListPresenter(this);
        presenter.loadBadges();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentDisplayer = (IFragmentDisplayer)context;
        this.context = context;
    }

    @Override
    public void loadView() {
        officialRecyclerView.setAdapter(new BadgeAdapter(context, presenter, true));
        unOfficialRecyclerView.setAdapter(new BadgeAdapter(context, presenter, false));
    }

}