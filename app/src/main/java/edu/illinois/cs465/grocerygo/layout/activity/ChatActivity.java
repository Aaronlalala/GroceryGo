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
import edu.illinois.cs465.grocerygo.layout.fragment.mail.Chat_RecyclerAdapter;
import edu.illinois.cs465.grocerygo.layout.fragment.mail.Message;

public class ChatActivity extends AppCompatActivity {

    public RecyclerView chatRecyclerView;
    public Chat_RecyclerAdapter chatAdapter;

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
//            contact_messages.add(new Message(inputMessageString, image));
            inputMessage.setText("");
        });

        backBtn.setOnClickListener(view -> {
            this.finish();
        });

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatAdapter = new Chat_RecyclerAdapter(this, new ArrayList<>());
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
