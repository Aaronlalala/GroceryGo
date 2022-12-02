package edu.illinois.cs465.grocerygo.layout.fragment.post;


import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.illinois.cs465.grocerygo.R;

public class PostViewHolder extends RecyclerView.ViewHolder{
    public LinearLayout root;
    public ImageView theProfilePic;
    public TextView theName;
    public TextView theTime;
    public TextView theRemark;
    public TextView theDestination;
    public TextView theDistance;
    public View view;

    public PostViewHolder(View v)
    {
        super(v);
        root = (LinearLayout)v.findViewById(R.id.postItemRoot);
        theProfilePic = (ImageView)v.findViewById(R.id.theProfilePic);
        theName = (TextView)v.findViewById(R.id.theName);
        theTime = (TextView) v.findViewById(R.id.theTime);
        theRemark = (TextView)v.findViewById(R.id.theRemark);
        theDestination = (TextView)v.findViewById(R.id.theDestination);
        theDistance = (TextView)v.findViewById(R.id.theDistance);
        view  = v;
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               //Log.d("post","Element " + getAdapterPosition() + " clicked.");
//            }
//        });
    }

    public void setClockDrawable(int viewId, Drawable d){
        Button b = this.view.findViewById(viewId);
        b.setCompoundDrawables(d, null, null, null);
    }

}
