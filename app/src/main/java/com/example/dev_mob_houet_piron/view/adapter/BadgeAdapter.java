package com.example.dev_mob_houet_piron.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.BadgeListPresenter;
import com.example.dev_mob_houet_piron.view.BadgeDisplayActivity;
import com.example.dev_mob_houet_piron.view.interfaces.IBadgeItem;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {

    private boolean isOfficial;
    private Context context;
    private BadgeListPresenter presenter;

    public BadgeAdapter(Context context, BadgeListPresenter presenter, boolean isOfficial) {
        this.context = context;
        this.presenter = presenter;
        this.isOfficial = isOfficial;
    }

    @NonNull
    @Override
    public BadgeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.badge_item, parent, false);
        return new BadgeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeAdapter.ViewHolder holder, int position) {
        String id;
        if(isOfficial)
            id = presenter.bindOfficialBadgeToItem(holder, position);
        else
            id = presenter.bindUnOfficialBadgeToItem(holder, position);
        holder.itemView.setOnClickListener(view -> context.startActivity(BadgeDisplayActivity.newIntent(context, id)));
    }

    @Override
    public int getItemCount() {
        if(presenter == null)
            return 0;
        return (isOfficial) ? presenter.getOfficialBadgesSize() : presenter.getUnOfficialBadgesSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements IBadgeItem {

        private TextView title;
        private TextView description;
        private TextView nbrFound;
        private TextView nbrToFind;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.badgeItem_Title);
            description = itemView.findViewById(R.id.badgeItem_Description);
            nbrFound = itemView.findViewById(R.id.badge_nbr_found);
            nbrToFind = itemView.findViewById(R.id.badge_needed_to_complete);
        }

        @Override
        public void setTitle(String text) {
            title.setText(text);
        }

        @Override
        public void setDescription(String text) {
            description.setText(text);
        }

        @Override
        public void setNbrFound(int nbrFound) {
            this.nbrFound.setText(String.valueOf(nbrFound));
        }

        @Override
        public void setNbrExpected(int nbrToFind) {
            this.nbrToFind.setText(String.valueOf(nbrToFind));
        }
    }
}
