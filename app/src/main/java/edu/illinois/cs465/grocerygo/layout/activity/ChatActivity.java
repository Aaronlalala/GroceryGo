package edu.illinois.cs465.grocerygo.layout.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.dialog.TimePickerDialog;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);
        initView();
    }

    private void initView() {
        Button dismissBtn = findViewById(R.id.button_goback);
        dismissBtn.setOnClickListener(view -> {
            this.finish();
        });

        Button acceptBtn = findViewById(R.id.accept);
        acceptBtn.setOnClickListener(view -> {
            this.finish();
        });



    }
}
