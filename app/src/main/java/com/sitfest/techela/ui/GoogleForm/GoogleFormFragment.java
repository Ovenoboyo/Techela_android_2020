package com.sitfest.techela.ui.GoogleForm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.sitfest.techela.MainActivity;
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
    private static final int PERMISSION_REQUEST_CAMERA = 69;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        barcodesList.add("aeb47571-6b6b-4b92-ba19-282111c8d669");
        barcodesList.add("f163ff1f-195d-4935-bf10-e46c4bf90efb");
        barcodesList.add("bb13359a-9a8d-4502-a650-d9608e748980");
        barcodesList.add("cf93eb79-8a1d-4511-ba91-2b17a5341de6");
        barcodesList.add("2e169a33-91c4-4c71-9e6d-ea203e735365");
        barcodesList.add("c4a1ce8d-7b32-4f17-adbb-527859357446");
        barcodesList.add("21459418-1db1-4f8d-b001-4faa88bc8a59");
        barcodesList.add("68acb286-e46a-492f-86bc-4ada96e7ca6b");
        barcodesList.add("db9d56a6-c55b-4797-b9f2-b4b7d7eb0f09");
        barcodesList.add("96ad1a17-36ac-4c2b-8cda-a2a20e37cc19");

    }

    public GoogleFormFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).getToolbar();
        toolbar.setTitle("Register");

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this.getContext(), "Camera Permission is required to run QR Scanner", Toast.LENGTH_LONG).show();
            } else {
                // No explanation needed; request the permission

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length <= 0
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getContext(), "Camera Permission is required to run QR Scanner", Toast.LENGTH_LONG).show();
            }
        }
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
        long time= System.currentTimeMillis();
        data.put("timestamp", String.valueOf(time));
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
                 Toast.makeText(getContext(), "Remaning QR Codes to find: " + (11 - dataSnapshot.getChildrenCount()), Toast.LENGTH_LONG).show(); // one node is timestamp
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}