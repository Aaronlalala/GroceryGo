package edu.illinois.cs465.grocerygo.layout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.fragment.mail.ChatRecyclerAdapter;
import edu.illinois.cs465.grocerygo.layout.fragment.mail.Message;

public class ChatActivity extends AppCompatActivity {

    public RecyclerView chatRecyclerView;
    public ChatRecyclerAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        initView();
    }

    private void initView() {
        View sendMessage = findViewById(R.id.layoutSend);
        View backBtn = findViewById(R.id.imageBack);

        sendMessage.setOnClickListener(view -> {
            TextView inputMessage = findViewById(R.id.inputMessage);
            String inputMessageString = inputMessage.getText().toString();
            int image = R.drawable.girl;
            chatAdapter.messages.add(new Message(inputMessageString, image));
            inputMessage.setText("");
        });

        backBtn.setOnClickListener(view -> {
            this.finish();
        });

        // If user clicks request to join button to enter the chatting activity,
        // automatically send a request component.
        Bundle extras = getIntent().getExtras();
        if (extras!= null && extras.getString("button").equals("request")) {
            findViewById(R.id.request_view).setVisibility(View.VISIBLE);
            findViewById(R.id.request_image).setVisibility(View.VISIBLE);
        }


        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatAdapter = new ChatRecyclerAdapter(this, new ArrayList<>());
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
