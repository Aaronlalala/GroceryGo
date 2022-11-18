package edu.illinois.cs465.grocerygo.layout.activity;

import static edu.illinois.cs465.grocerygo.constant.Constant.POST_FRAGMENT_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.illinois.cs465.grocerygo.R;

public class PostDetailActivity extends AppCompatActivity {

    private MapView mMap;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail_activity);

        mMap = findViewById(R.id.map);
        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        MapsInitializer.initialize(this);
        int errorCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS != errorCode) {
            GooglePlayServicesUtil.getErrorDialog(errorCode,
                    this, 0).show();
        } else {
//            googleMap = mMap.getMap();
            if (googleMap != null) {
//                initVolley();
                LatLng cur = new LatLng(40.116890, -88.222130);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15));
                googleMap.addMarker(new MarkerOptions().position(cur));
            }
        }

        initView();
    }

    private void initView() {
        ImageView postView = findViewById(R.id.back);



        postView.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        });

        FrameLayout ContactDriverBtn = findViewById(R.id.contact_driver);
        ContactDriverBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });

        FrameLayout JoinBtn = findViewById(R.id.join);
        JoinBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });
    }


    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng cur = new LatLng(40.116890, -88.222130);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15));
        googleMap.addMarker(new MarkerOptions().position(cur));
    }
}
