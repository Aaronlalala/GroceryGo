package edu.illinois.cs465.grocerygo.layout.fragment.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.illinois.cs465.grocerygo.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    public Context context;
    public ArrayList<HistoryData> historyData;
    private final HistoryRecyclerViewInterface recyclerViewInterface;

    public HistoryAdapter(ArrayList<HistoryData> historyData, Context context,
                          HistoryRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.historyData = historyData;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the history items to recycler view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        holder.theProfilePic.setImageResource(historyData.get(position).imageId);
        holder.name.setText(historyData.get(position).name);
        holder.time.setText(historyData.get(position).time);
        // FIXME: Set default number of stars
//        holder.ratingBar.setDefaultStars(historyData.get(position).stars);
        holder.tips.setText(historyData.get(position).tips);
        holder.destination.setText(historyData.get(position).destination);

    }

    @Override
    public int getItemCount() {
        return this.historyData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public ImageView theProfilePic;
        public TextView name;
        public TextView time;
        public View ratingBar;
        public TextView destination;
        public TextView tips;
        public View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: grab components to display
            root = itemView.findViewById(R.id.historyItemRoot);
            theProfilePic = itemView.findViewById(R.id.history_ProfilePic);
            name = itemView.findViewById(R.id.history_name);
            time = itemView.findViewById(R.id.history_time);
            destination = itemView.findViewById(R.id.history_destination);
            // FIXME: need to justify number of stars
            ratingBar = itemView.findViewById(R.id.history_rating_bar);
            tips = itemView.findViewById(R.id.tips);

            // FIXME: It seems this below line is useless
            view = itemView;

            // Set onclick listener for recycler view item
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
