package edu.illinois.cs465.grocerygo.layout.fragment.mail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.illinois.cs465.grocerygo.R;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder> {

    public Context context;
    // Create an array list to store all contacts sent messages.
    // Message object represents each item of in the recyclerView.
    // Since we don't use database, neither read of output data into files, this will only a
    // temporary storage.
    public ArrayList<Message> messages;

    public ChatRecyclerAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }


    @NonNull
    @Override
    public ChatRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_container_sent_message, parent, false);

        return new ChatRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerAdapter.MyViewHolder holder, int position) {
        // Assigning values to each item
        holder.textView.setText(messages.get(position).getMessage());
        holder.imageView.setImageResource(messages.get(position).getContact_image());
    }

    @Override
    public int getItemCount() {
        // Total items
        return messages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.messageImage);
            this.textView = itemView.findViewById(R.id.textMessage);
        }
    }
}
