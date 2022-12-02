package edu.illinois.cs465.grocerygo.layout.activity;

import static edu.illinois.cs465.grocerygo.constant.Constant.POST_FRAGMENT_TAG;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.fragment.OngoingFragment;

public class PostDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail_activity);
        initView();
    }

    private void initView() {
        ImageView postView = findViewById(R.id.back);
        postView.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Bundle extras = getIntent().getExtras();
        Fragment fragment;
        if (extras != null) {
            fragment = new OngoingFragment(extras, true);
        } else {
            fragment = new OngoingFragment();
        }
        Log.d("detail_tag", extras.getString("startTime"));
        Log.d("detail_tag", extras.getString("destination"));
//        View rootView = fragment.getView();
//        TextView userId = rootView.findViewById(R.id.user_id);
        //userId.setText(extras.getString("userName"));
        ft.add(R.id.post_detail_container, fragment);
        ft.commit();
    }

}
