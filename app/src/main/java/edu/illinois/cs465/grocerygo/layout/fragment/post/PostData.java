package edu.illinois.cs465.grocerygo.layout.fragment.post;

import java.util.Date;

public class PostData {
    int imageId;
    Double distanceDouble;
    String name;
    String time;
    String remark;
    String destination;
    String distance;
    boolean isMypost;

    PostData( Double distanceDouble, int imgId, String name, String time, String remark, String destination, String distance, boolean isMypost)
    {
        this.distanceDouble = distanceDouble;
        this.imageId = imgId;
        this.name = name;
        this.time = time;
        this.remark = remark;
        this.destination = destination;
        this.distance = distance;
        this.isMypost = isMypost;
    }
}
