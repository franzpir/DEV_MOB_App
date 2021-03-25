package com.example.dev_mob_houet_piron.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.IProvidePlaceListData;
import com.example.dev_mob_houet_piron.view.CacheDisplayActivity;
import com.example.dev_mob_houet_piron.view.interfaces.IPlaceItem;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Context context;
    private IProvidePlaceListData dataProvider;

    public PlaceAdapter(Context context, IProvidePlaceListData dataProvider) {
        this.context = context;
        this.dataProvider = dataProvider;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cache_item, parent, false);
        return new PlaceAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dataProvider.onBindPlaceRowViewAtPosition((IPlaceItem)holder, position);
    }

    @Override
    public int getItemCount() {
       if(dataProvider == null) {
           return 0;
       } else {
           return dataProvider.getPlaceListSize();
       }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements IPlaceItem {

        private final Context context;
        private LinearLayout cacheItem;
        private TextView title;
        private String id;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = itemView.findViewById(R.id.cacheItem_Title);
            cacheItem = itemView.findViewById(R.id.cacheItem);

            cacheItem.setOnClickListener((v) -> {
                context.startActivity(CacheDisplayActivity.newIntent(context, this.id));
            });
        }

        @Override
        public void setTitle(String text) {
            title.setText(text);
        }

        @Override
        public void setId(String id) { this.id = id; }

    }
}
