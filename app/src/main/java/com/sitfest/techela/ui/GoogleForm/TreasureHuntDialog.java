package com.sitfest.techela.ui.GoogleForm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.sitfest.techela.R;

import java.util.Objects;

public class TreasureHuntDialog extends DialogFragment {
    private Context mContext;
    String text = "", title = "", extras = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setCancelable(true);
        assert getArguments() != null;
        text = getArguments().getString("text");
        title = getArguments().getString("title");
        extras = getArguments().getString("extras");


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.treasurehunt_dialog, null);
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext);

        builder.setView(view);
        builder.setTitle(title);

        TextView clue_text = view.findViewById(R.id.clue_text);
        TextView clue_extras = view.findViewById(R.id.clue_extras);
        clue_text.setText(text);
        clue_extras.setText(extras);

        clue_extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(extras)));
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

}

