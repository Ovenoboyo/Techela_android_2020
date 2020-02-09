package com.sitfest.techela.ui.GoogleForm;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<String> mData;
    private ArrayList<String> clues;
    private ArrayList<String> clueExtras;
    private FragmentActivity activity;

    public ListViewAdapter(ArrayList<String> mData, ArrayList<String> clues, ArrayList<String> clueExtras, FragmentActivity activity) {
        this.mData = mData;
        this.clues = clues;
        this.clueExtras = clueExtras;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            result = convertView;
        }

        // TODO replace findViewById by ViewHolder
        TextView text = ((TextView) result.findViewById(android.R.id.text1));
        text.setText(getItem(position));

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreasureHuntDialog dialog = new TreasureHuntDialog();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Clue No. "+position);
                bundle.putString("text", clues.get(position));
                bundle.putString("extras", clueExtras.get(position));
                FragmentManager fm = activity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                dialog.setArguments(bundle);
                dialog.show(ft, "TreasureDialogFragment");
                ft.addToBackStack("TreasureDialogFragment");
            }
        });

        return result;
    }
}