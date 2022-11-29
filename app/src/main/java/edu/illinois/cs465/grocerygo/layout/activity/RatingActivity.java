package edu.illinois.cs465.grocerygo.layout.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import edu.illinois.cs465.grocerygo.R;

public class RatingActivity extends AbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_layout);
        initView();
    }

    private void initView() {
//        EditText message = findViewById(R.id.note);
        RatingBar score = findViewById(R.id.score);
        @ColorInt int color = Color.parseColor("#60F1C586");
        score.setDrawingCacheBackgroundColor(color);
        EditText tips = findViewById(R.id.tips);
        tips.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        TextView finishBtn = findViewById(R.id.finish);
        finishBtn.setOnClickListener(view -> {
            if (score.getRating() == 0.0f) {
                Toast.makeText(this, "Please Give Your Rate!", Toast.LENGTH_SHORT).show();
            }
            else{this.finish();}
        });

        ImageView dismissBtn = findViewById(R.id.btn_dismiss);
        dismissBtn.setOnClickListener(view -> {
            this.finish();
        });
    }
}
