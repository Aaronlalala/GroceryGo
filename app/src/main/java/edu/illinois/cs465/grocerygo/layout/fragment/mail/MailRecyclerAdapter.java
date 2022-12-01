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
import edu.illinois.cs465.grocerygo.layout.activity.ContactInfo;

public class MailRecyclerAdapter extends RecyclerView.Adapter<MailRecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<ContactInfo> contactInfoArrayList;
    private final MailRecyclerViewInterface recyclerViewInterface;

    public MailRecyclerAdapter(Context context, ArrayList<ContactInfo> contactInfoArrayList,
                               MailRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.contactInfoArrayList = contactInfoArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MailRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_recycler_view_row, parent, false);

        return new MailRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MailRecyclerAdapter.MyViewHolder holder, int position) {
        // Assigning values to the views in the layout file based on the position of teh recycler view
        holder.contactName.setText(contactInfoArrayList.get(position).getContactName());
        holder.imageView.setImageResource(contactInfoArrayList.get(position).getContactImage());
    }

    @Override
    public int getItemCount() {
        // How many items in total
        return this.contactInfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabbing the views from contact_recycler_view_row file
        ImageView imageView;
        TextView contactName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.contact_imageView);
            contactName = itemView.findViewById(R.id.contact_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                             recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
