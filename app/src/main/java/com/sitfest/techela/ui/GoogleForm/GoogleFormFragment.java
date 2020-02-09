package com.sitfest.techela.ui.GoogleForm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.sitfest.techela.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.sitfest.techela.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.Context.MODE_PRIVATE;

public class GoogleFormFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    private final ArrayList<String> barcodesList = new ArrayList<>();
    private final String googleForm = "https://docs.google.com/forms/d/e/1FAIpQLSfCNvyFKYyddYyVPpskXuB93cq-VIdDPVbk0LUr3wIyhZkcWw/viewform?vc=0&c=0&w=1";
    private FirebaseUser user;
    private ArrayList<String> cluesList = new ArrayList<>();
    private ArrayList<String> cluesExtrasList = new ArrayList<>();
    private ArrayList<String> treasureQRList = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CAMERA = 69;
    private DatabaseReference userNode;
    private ArrayList<String> clues = new ArrayList<>();
    private ListViewAdapter listViewAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(true);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Getting data...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();

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

        treasureQRList.addAll(Arrays.asList("0f3f915e-0d0b-4de7-b8ef-d5d7100ee063", "424f4102-b1de-43e0-8cb6-4dfaff0098ed", "410b0a91-cee8-4876-b146-aaf7ffa8739c", "226e25e8-5f28-4dc0-8939-2351e2d28499", "a40f943c-3f4b-44f9-a5d7-0ca75c82f59a", "1a7df67e-346a-49f1-a707-7e8b55316cb1", "a27e9f98-50e8-4035-bb1f-1cfc3b24e37b", "1d2f0746-665a-4337-85b5-4675b11cd1ae", "384ffcdb-3774-4155-9fe2-b96cd3b15d74", "2382ace5-db06-4a4b-887d-ad9e851b1fe2"));

    }

    public GoogleFormFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.google_form, container, false);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference huntNode = rootRef.child("TreasureHunt");
        DatabaseReference cluesNode = huntNode.child("Clues");
        userNode = huntNode.child(user.getUid());
        cluesNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot numbers: dataSnapshot.getChildren()) {
                    cluesList.add(Integer.parseInt(numbers.getKey()), numbers.getValue().toString());
                }
                DatabaseReference clueExtrasNode = huntNode.child("ClueExtras");
                clueExtrasNode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot numbers: dataSnapshot.getChildren()) {
                            cluesExtrasList.add(Integer.parseInt(numbers.getKey()), numbers.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                mScannerView = root.findViewById(R.id.zxscan);
                mScannerView.setResultHandler(GoogleFormFragment.this);
                mScannerView.startCamera();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ListView listView = root.findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("TreasureHunt", MODE_PRIVATE);
        Log.d("test", "onCreateView: "+sharedPreferences.getAll());
        for (String index : treasureQRList) {
            String barcode = sharedPreferences.getString(""+treasureQRList.indexOf(""+index), null);
            if (barcode != null) {
                clues.add("Clue No. "+treasureQRList.indexOf(""+index));
            }
        }

        if (clues.isEmpty()) {
            clues.add("You haven't found any clues yet");
        }

        listViewAdapter = new ListViewAdapter(clues, cluesList, cluesExtrasList, getActivity());
        listView.setAdapter(listViewAdapter);

        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).getToolbar();
        toolbar.setTitle("QR Scanner");

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

        return root;
    }

    private void updateClues(int index) {
        clues.add("Clue No. "+index);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mScannerView != null) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        String status = check_barcode(rawResult.getText());
        if (status != null) {
            push_status(status);
            getRemainingCodes();
        } else if ((check_googleform(rawResult.getText()))) {
            openForm();
        } else {
            check_treasure(rawResult.getText());
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
        if (mScannerView != null) {
            mScannerView.stopCamera();
        }
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

    private void showClue(int index) {
        if (index == 0) {
            clues.remove(0);
            updateClues(index);
        } else {
            updateClues(index);
        }
        listViewAdapter.notifyDataSetChanged();

        TreasureHuntDialog dialog = new TreasureHuntDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", "Clue No. "+index);
        bundle.putString("text", cluesList.get(index));
        bundle.putString("extras", cluesExtrasList.get(index));
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        dialog.setArguments(bundle);
        dialog.show(ft, "TreasureDialogFragment");
        ft.addToBackStack("TreasureDialogFragment");
    }

    private void putTreasure(int index, String barcode, SharedPreferences sharedPreferences) {

        SharedPreferences.Editor Edit = sharedPreferences.edit();
        Edit.putString(String.valueOf(index), barcode);
        Edit.apply();
        HashMap<String, String> data = new HashMap<>();
        data.put("timestamp", String.valueOf(System.currentTimeMillis()));
        data.put(String.valueOf(index), barcode);
        userNode.child(String.valueOf(index)).setValue(data);
    }

    private void check_treasure(String barcode) {
        if (treasureQRList.contains(barcode)) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("TreasureHunt", MODE_PRIVATE);
            int index = treasureQRList.indexOf(barcode);
            if (index == 0) {
                    putTreasure(index, barcode, sharedPreferences);
                    showClue(index);

            } else if (treasureQRList.contains(sharedPreferences.getString(String.valueOf(index - 1), ""))) {
                    putTreasure(index, barcode, sharedPreferences);
                    showClue(index);
            } else {
                Toast.makeText(getContext(), "Not scanned in a correct sequence", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void push_status(String uuid) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference statusNode = rootRef.child("LuckyStatus");
        DatabaseReference userNode = statusNode.child(user.getUid()).child(uuid);
        DatabaseReference timestamp = statusNode.child(user.getUid()).child("timestamp");
        userNode.setValue(uuid);
        long time= System.currentTimeMillis();
        timestamp.setValue(String.valueOf(time));


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