package edu.illinois.cs465.grocerygo.layout.fragment.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
        holder.bindView(historyData.get(position));

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
        public RatingBar ratingBar;
        public TextView destination;
        public TextView tips;
        private TextView rateHint;
        public View view;
        private boolean isRated = false;

        public void bindView(HistoryData historyData) {
            theProfilePic.setImageResource(historyData.imageId);
            name.setText(historyData.name);
            time.setText(historyData.time);
            isRated = historyData.isRated;
            if (historyData.isRated) {
                ratingBar.setRating((float) historyData.stars);
                tips.setText("tips: $" + historyData.tips);
            } else {
                ratingBar.setVisibility(View.GONE);
                rateHint.setVisibility(View.VISIBLE);
                rateHint.setTextColor(0xFFFE2C55);
                tips.setText("Leave tips");
            }
            destination.setText(historyData.destination);
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: grab components to display
            root = itemView.findViewById(R.id.historyItemRoot);
            theProfilePic = itemView.findViewById(R.id.history_ProfilePic);
            name = itemView.findViewById(R.id.history_name);
            time = itemView.findViewById(R.id.history_time);
            rateHint = itemView.findViewById(R.id.go_to_rate);
            destination = itemView.findViewById(R.id.history_destination);
            ratingBar = itemView.findViewById(R.id.history_rating_bar);
            tips = itemView.findViewById(R.id.tips);

            // FIXME: It seems this below line is useless
            view = itemView;

            // Set onclick listener for recycler view item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null && !isRated) {
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
