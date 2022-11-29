package edu.illinois.cs465.grocerygo.layout.fragment.post;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
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
    public Context context;
    /**
     * Initialize the dataset of the PostAdapter.
     */
    public PostAdapter(List<PostData> postList, Context context)
    {
        //Log.d("tttttttt", postList.toString());
        this.context = context;
        this.postList = postList;
    }


    //变量接收接口
    private OnItemClickListener mOnItemClickListener;
    //定义接口：点击事件
    public interface OnItemClickListener {
        void onItemClick(View view, int position);//单击
        void onItemLongClick(View view, int position);//长按
    }
    //设置接口接收的方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    //PostViewHolder 把post_item的layout和自己联系在一起
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);

        TextView time = v.findViewById(R.id.theTime);
        Drawable clock = context.getResources().getDrawable(R.drawable.clock);
        clock.setBounds(-20,  0, 50, 50);
        time.setCompoundDrawables(clock, null, null, null);

        TextView destinationButton = v.findViewById(R.id.theDestination);
        Drawable des = context.getResources().getDrawable(R.drawable.flag);
        des.setBounds(-10,  0, 45, 45);
        destinationButton.setCompoundDrawables(des, null, null, null);

        TextView distanceText = v.findViewById(R.id.theDistance);
        Drawable dis = context.getResources().getDrawable(R.drawable.marker);
        dis.setBounds(0,  0, 50, 50);
        distanceText.setCompoundDrawables(dis, null, null, null);

        return new PostViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    //PostData给PostViewHolder提供具体的数据绑定（List<PostData>)
    @Override
    public void
    onBindViewHolder(PostViewHolder viewHolder, final int position)
    {
        viewHolder.theProfilePic.setImageResource(postList.get(position).imageId);
        viewHolder.theName.setText(postList.get(position).name);
        viewHolder.theTime.setText(postList.get(position).time);
        viewHolder.theRemark.setText(postList.get(position).remark);
        viewHolder.theDestination.setText(postList.get(position).destination);
        viewHolder.theDistance.setText(postList.get(position).distance);
        @ColorInt int color = Color.parseColor("#60F1C586");
        if(postList.get(position).isMypost && postList.get(position).name.equals("Elysia")) viewHolder.root.setBackgroundColor(color);
        viewHolder.root.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return postList.size();
    }
}