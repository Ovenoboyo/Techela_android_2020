package com.example.techela.ui.GoogleForm;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techela.R;
import com.example.techela.ui.home.RecyclerAdapter;

import java.util.Objects;

public class GoogleFormFragment extends Fragment {

    public GoogleFormFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.google_form, container, false);
//        TextView formLink = root.findViewById(R.id.form_link);
//        formLink.setText(Objects.requireNonNull(getActivity()).getString(R.string.open_link));

//        formLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getFormLink()));
//                startActivity(browserIntent);
//            }
//        });

        ImageView formQr = root.findViewById(R.id.qr_code);
        Drawable qr = getResources().getDrawable(R.drawable.qr_code, Objects.requireNonNull(getActivity()).getTheme());
        formQr.setImageDrawable(qr);

        return root;
    }

    private String getFormLink() {
        return "https://docs.google.com/forms/d/e/1FAIpQLSfCNvyFKYyddYyVPpskXuB93cq-VIdDPVbk0LUr3wIyhZkcWw/viewform?vc=0&c=0&w=1";
    }
}