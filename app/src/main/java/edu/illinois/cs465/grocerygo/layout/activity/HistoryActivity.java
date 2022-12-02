package edu.illinois.cs465.grocerygo.layout.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.fragment.post.PostFragment;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        initView();
    }

    private void initView() {
        ImageView dismissBtn = findViewById(R.id.btn_dismiss);
        dismissBtn.setOnClickListener(view -> this.finish());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new PostFragment("true");
        ft.add(R.id.container, fragment);
        ft.commit();
    }
}
