package com.example.techela.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techela.R;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final String[] EventName;
    private final String[] EventDesc;
    private final int[] EventPic;
    private final int[] EventBanner;
    private static LayoutInflater inflater = null;

    RecyclerAdapter(FragmentActivity Activity, String[] EventName, String[] EventDesc, int[] EventPic, int[] EventBanner) {
        this.EventName = EventName;
        this.EventDesc = EventDesc;
        this.EventPic = EventPic;
        this.EventBanner = EventBanner;
        inflater = (LayoutInflater) ((Context) Activity).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.events_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.EventName.setText(EventName[position]);
        holder.EventDesc.setText(EventDesc[position]);
        holder.EventPic.setImageResource(EventPic[position]);
        holder.EventBanner.setImageResource(EventBanner[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Set onClicks
            }
        });
    }

    @Override
    public int getItemCount() {
        return EventName.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView EventName;
        final TextView EventDesc;
        final ImageView EventPic;
        final ImageView EventBanner;

        ViewHolder(View itemView) {
            super(itemView);
            EventName = itemView.findViewById(R.id.EventName);
            EventDesc = itemView.findViewById(R.id.EventDesc);
            EventPic = itemView.findViewById(R.id.EventPic);
            EventBanner = itemView.findViewById(R.id.EventBanner);
        }
    }
}
