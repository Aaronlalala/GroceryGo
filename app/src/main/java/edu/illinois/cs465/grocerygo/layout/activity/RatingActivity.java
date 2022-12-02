package edu.illinois.cs465.grocerygo.layout.activity;

import android.content.Intent;
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

import org.greenrobot.eventbus.EventBus;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.event.PostEvent;
import edu.illinois.cs465.grocerygo.event.RateEvent;

public class RatingActivity extends AbstractActivity {
    private int position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
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
            else{
                float rating = score.getRating() - 1.0f;
                float money = 0.0f;
                if (tips.getText() != null && !tips.getText().toString().equals("")) {
                    money = Float.parseFloat(tips.getText().toString());
                }
                EventBus.getDefault().post(new RateEvent(position, money, rating));
                this.finish();
            }
        });

        ImageView dismissBtn = findViewById(R.id.btn_dismiss);
        dismissBtn.setOnClickListener(view -> {
            this.finish();
        });
    }
}
