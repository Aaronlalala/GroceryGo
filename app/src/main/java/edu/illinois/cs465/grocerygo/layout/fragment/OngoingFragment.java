package edu.illinois.cs465.grocerygo.layout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.activity.ChatActivity;
import edu.illinois.cs465.grocerygo.layout.activity.RatingActivity;

public class OngoingFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMap;
    private GoogleMap googleMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ongoing_fragment, container, false);
        mMap = view.findViewById(R.id.map);
        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        MapsInitializer.initialize(getActivity());
        int errorCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this.getActivity());

        if (ConnectionResult.SUCCESS != errorCode) {
            GooglePlayServicesUtil.getErrorDialog(errorCode,
                    this.getActivity(), 0).show();
        } else {
//            googleMap = mMap.getMap();
            if (googleMap != null) {
//                initVolley();
                LatLng cur = new LatLng(40.116890, -88.222130);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15));
                googleMap.addMarker(new MarkerOptions().position(cur));
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm= getFragmentManager();
            FragmentTransaction ft= fm.beginTransaction();
            mapFragment= SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        FrameLayout ContactDriverBtn = view.findViewById(R.id.contact_driver1);
        ContactDriverBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            startActivity(intent);
        });

        FrameLayout RatingBtn = view.findViewById(R.id.rate_driver);
        RatingBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), RatingActivity.class);
            startActivity(intent);
        });
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng cur = new LatLng(40.116890, -88.222130);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15));
        googleMap.addMarker(new MarkerOptions().position(cur));
    }
}
