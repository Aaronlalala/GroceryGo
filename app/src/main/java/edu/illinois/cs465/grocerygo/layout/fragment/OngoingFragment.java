package edu.illinois.cs465.grocerygo.layout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.event.DeleteEvent;
import edu.illinois.cs465.grocerygo.event.PostEvent;
import edu.illinois.cs465.grocerygo.layout.activity.ChatActivity;
import edu.illinois.cs465.grocerygo.layout.activity.RatingActivity;

public class OngoingFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMap;
    private GoogleMap googleMap;
    private String activity;
    private String userName;
    private String distanceToDriver;
    private String destination;
    private String startTime;
    private int imageId;
    private String remark;
    private boolean isDetail;

    // Default constructor for OngoingFragment.
    public OngoingFragment() {

    }
    // Pass in a isPost tell whether use this fragment in PostDetailActivity
//    public OngoingFragment(String activity) {
//        this.activity = activity;
//    }
    public OngoingFragment(Bundle extras, boolean isDetail) {
        this.activity = extras.getString("activity");
        this.userName = extras.getString("userName");
        this.distanceToDriver = extras.getString("distanceToDriver");
        this.destination = extras.getString("destination");
        this.startTime = extras.getString("startTime");
        this.imageId = extras.getInt("imageId");
        //this.imageId = Integer.valueOf(extras.getString("imageId"));
        this.remark = extras.getString("remark");
        this.isDetail = isDetail;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ongoing_fragment, container, false);
        mMap = view.findViewById(R.id.map);
        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        if(isDetail)initDriverData(view);
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

    private void initDriverData(View view){
        TextView userNameTV = view.findViewById(R.id.user_id);
        userNameTV.setText(this.userName);
        TextView distanceToDriverTV = view.findViewById(R.id.distanceToDriver);
        distanceToDriverTV.setText(this.distanceToDriver);
        TextView destinationTV = view.findViewById(R.id.destination);
        destinationTV.setText(this.destination);
        TextView startTimeTV = view.findViewById(R.id.startTime);
        startTimeTV.setText(this.startTime);
        ImageView image = view.findViewById(R.id.avatar);
        image.setImageResource(this.imageId);
        TextView remarkTV = view.findViewById(R.id.remarkDetail);
        remarkTV.setText(this.remark);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView contactBtn = getView().findViewById(R.id.contact_driver_button);
        TextView joinBtn = getView().findViewById(R.id.join_button);
        TextView rateBtn = getView().findViewById(R.id.rate_driver_button);
        TextView plateNumber = getView().findViewById(R.id.textView);
        ImageView deleteBtn = getView().findViewById(R.id.delete_button);
        ImageView numberItem = view.findViewById(R.id.numberItem);
        TextView license = view.findViewById(R.id.driver_license);
        ImageView licenseVeri = view.findViewById(R.id.license_veri);
        TextView number = view.findViewById(R.id.number);
        TextView textView2 = view.findViewById(R.id.textView2);
        TextView dis = view.findViewById(R.id.distanceToDriver);
        if (this.activity == null) {
            contactBtn.setVisibility(View.VISIBLE);
        } else if (this.activity.equals("post")) {
            contactBtn.setVisibility(View.VISIBLE);
            joinBtn.setVisibility(View.VISIBLE);
            plateNumber.setVisibility(View.INVISIBLE);
        } else if (this.activity.equals("history")) {
            contactBtn.setVisibility(View.VISIBLE);
            rateBtn.setVisibility(View.VISIBLE);
        } else if(this.activity.equals("myPost")){
            deleteBtn.setVisibility(View.VISIBLE);
            plateNumber.setVisibility(View.INVISIBLE);
            numberItem.setVisibility(View.INVISIBLE);
            number.setVisibility(View.INVISIBLE);
            license.setVisibility(View.INVISIBLE);
            licenseVeri.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            dis.setVisibility(View.INVISIBLE);
        }

        SupportMapFragment mapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm= getFragmentManager();
            FragmentTransaction ft= fm.beginTransaction();
            mapFragment= SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        TextView RequestBtn = view.findViewById(R.id.join_button);
        RequestBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("button", "request");
            startActivity(intent);
        });

        TextView ContactDriverBtn = view.findViewById(R.id.contact_driver_button);
        ContactDriverBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            startActivity(intent);
        });

        TextView RatingBtn = view.findViewById(R.id.rate_driver_button);
        RatingBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), RatingActivity.class);
            startActivity(intent);
        });

        deleteBtn.setOnClickListener(view1 -> {
            EventBus.getDefault().post(new DeleteEvent(true));
            getActivity().finish();
        });
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng cur = new LatLng(40.116890, -88.222130);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15));
        googleMap.addMarker(new MarkerOptions().position(cur));
    }
}
