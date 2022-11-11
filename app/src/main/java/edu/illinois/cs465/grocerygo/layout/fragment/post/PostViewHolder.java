package edu.illinois.cs465.grocerygo.layout.fragment.post;


import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.illinois.cs465.grocerygo.R;

public class PostViewHolder extends RecyclerView.ViewHolder{
    public TextView theName;
    public Button theTime;
    public TextView theRemark;
    public Button theDestination;
    public TextView theDistance;
    public View view;

    public PostViewHolder(View v)
    {
        super(v);
        theName = (TextView)v.findViewById(R.id.theName);
        theTime = (Button) v.findViewById(R.id.theTime);
        theRemark = (TextView)v.findViewById(R.id.theRemark);
        theDestination = (Button)v.findViewById(R.id.theDestination);
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
