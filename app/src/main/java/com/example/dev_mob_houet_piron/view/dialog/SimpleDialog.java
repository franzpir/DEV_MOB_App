package com.example.dev_mob_houet_piron.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.dev_mob_houet_piron.R;

public class SimpleDialog implements ISimpleDialog {

    private final Activity activity;
    private final int xmlView;
    private AlertDialog dialog;

    public SimpleDialog(Activity activity, int xmlView){
        this.xmlView = xmlView;
        this.activity = activity;
    }

    @Override
    public void displayDialog(String loadingMessage, boolean canBeClosed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(buildView(loadingMessage));
        builder.setCancelable(canBeClosed);

        activity.runOnUiThread(() -> {
            dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    public void dismissDialog(){
        dialog.dismiss();
    }

    private View buildView(String loadingMessage) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(xmlView, null);
        TextView loadingText = view.findViewById(R.id.under_text);
        loadingText.setText(loadingMessage);
        return view;
    }
}
