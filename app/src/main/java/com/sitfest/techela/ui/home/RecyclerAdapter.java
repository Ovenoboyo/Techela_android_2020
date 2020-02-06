package com.sitfest.techela.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sitfest.techela.R;
import com.sitfest.techela.ui.EventDetails.EventDetailsFragment;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final String[] EventName;
    private final String[] EventDesc;
    private final String[] EventDescLong;
    private final String[] EventTime;
    private final String[] EventVenue;
    private final int[] EventPic;
    private final int[] EventBanner;
    private static LayoutInflater inflater = null;
    private final FragmentActivity context;

    public RecyclerAdapter(FragmentActivity Activity, String[] EventName, String[] EventDesc, int[] EventPic, int[] EventBanner, String[] EventTime, String[] EventVenue, String[] EventDescLong) {
        this.EventName = EventName;
        this.EventDesc = EventDesc;
        this.EventDescLong = EventDescLong;
        this.EventPic = EventPic;
        this.EventBanner = EventBanner;
        this.EventTime = EventTime;
        this.EventVenue = EventVenue;
        this.context = Activity;
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
        holder.itemView.setOnClickListener(v -> {
            EventDetailsFragment nextFrag= new EventDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("eventName", EventName[position]);
            bundle.putString("eventDesc", EventDescLong[position]);
            bundle.putString("eventTime", EventTime[position]);
            bundle.putString("eventVenue", EventVenue[position]);
            bundle.putInt("eventBanner", EventBanner[position]);
            nextFrag.setArguments(bundle);
            context.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right, android.R.anim.slide_out_right )
                    .replace(R.id.nav_host_fragment, nextFrag, null)
                    .addToBackStack(null)
                    .commit();
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
