package edu.illinois.cs465.grocerygo.layout.fragment.post;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.illinois.cs465.grocerygo.R;

/**
 * Provide views to RecyclerView with data from .
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    //all posts' data
    public List<PostData> postList = Collections.emptyList();

    /**
     * Initialize the dataset of the PostAdapter.
     */
    public PostAdapter(List<PostData> postList)
    {
        System.out.println(postList);
        this.postList = postList;
    }

    // Create new views (invoked by the layout manager)
    //PostViewHolder 把post_item的layout和自己联系在一起
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);

        return new PostViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    //PostData给PostViewHolder提供具体的数据绑定（List<PostData>)
    @Override
    public void
    onBindViewHolder(PostViewHolder viewHolder, final int position)
    {
        viewHolder.theName.setText(postList.get(position).name);
        viewHolder.theTime.setText(postList.get(position).time);
        viewHolder.theRemark.setText(postList.get(position).remark);
        viewHolder.theDestination.setText(postList.get(position).destination);
        viewHolder.theDistance.setText(postList.get(position).distance);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return postList.size();
    }
}