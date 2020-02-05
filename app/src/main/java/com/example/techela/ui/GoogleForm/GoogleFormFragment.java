package com.example.techela.ui.GoogleForm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.techela.MainActivity;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class GoogleFormFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    private final ArrayList<String> barcodesList = new ArrayList<>();
    private final String googleForm = "https://docs.google.com/forms/d/e/1FAIpQLSfCNvyFKYyddYyVPpskXuB93cq-VIdDPVbk0LUr3wIyhZkcWw/viewform?vc=0&c=0&w=1";
    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        barcodesList.add("ghhgkgkhgkhgkhgkhg");
    }

    public GoogleFormFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).getToolbar();
        toolbar.setTitle("Register");

        return mScannerView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String status = check_barcode(rawResult.getText());
        if (status != null) {
            push_status(status);
            getRemainingCodes();
        } else if ((check_googleform(rawResult.getText()))) {
            openForm();
        }
        Handler handler = new Handler();
        handler.postDelayed(() -> mScannerView.resumeCameraPreview(GoogleFormFragment.this), 2000);
    }

    private  void openForm() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleForm));
        startActivity(browserIntent);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private String check_barcode(String barcode) {
        if (barcodesList.contains(barcode)) {
            return barcode;
        } else {
            return null;
        }
    }

    private boolean check_googleform(String barcode) {
        return barcode.equals(googleForm);
    }

    private void push_status(String uuid) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference statusNode = rootRef.child("LuckyStatus");
        DatabaseReference userNode = statusNode.child(user.getUid());
        Map<String, String> data = new HashMap<>();
        data.put(uuid, uuid);
        userNode.setValue(data);


    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private void getRemainingCodes() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference statusNode = rootRef.child("LuckyStatus");
        DatabaseReference userNode = statusNode.child(user.getUid());
        userNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Toast.makeText(getContext(), "Remaning QR Codes to find: " + (10 - dataSnapshot.getChildrenCount()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}