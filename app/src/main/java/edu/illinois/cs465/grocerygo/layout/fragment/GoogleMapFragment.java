package edu.illinois.cs465.grocerygo.layout.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;
    }

}
