package com.example.dev_mob_houet_piron.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.PlaceListPresenter;
import com.example.dev_mob_houet_piron.view.interfaces.IPlaceItem;

public class CheckablePlaceAdapter extends RecyclerView.Adapter<CheckablePlaceAdapter.ViewHolder> {

    private Context context;
    private PlaceListPresenter presenter;

    public CheckablePlaceAdapter(Context context, PlaceListPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CheckablePlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_item_checkable, parent, false);
        return new CheckablePlaceAdapter.ViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckablePlaceAdapter.ViewHolder holder, int position) {
        presenter.onBindPlaceRowViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        if(presenter == null)
            return 0;
        return presenter.getPlaceListSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements IPlaceItem {

        private final PlaceListPresenter presenter;
        private TextView title;
        private CheckBox checkbox;
        private String id;

        public ViewHolder(@NonNull View itemView, PlaceListPresenter presenter) {
            super(itemView);
            this.presenter = presenter;
            title = itemView.findViewById(R.id.place_item_checkable_title);
            checkbox = itemView.findViewById(R.id.place_item_checkable_checkbox);

            checkbox.setOnClickListener(event -> {
                if(checkbox.isChecked())
                    presenter.addSelectedPlace(this.id);
                else
                    presenter.removeSelectedPlace(this.id);
            });
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void setTitle(String text) {
            title.setText(text);
        }
    }
}
