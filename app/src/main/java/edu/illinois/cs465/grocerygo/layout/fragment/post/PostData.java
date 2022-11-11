package edu.illinois.cs465.grocerygo.layout.fragment.post;

public class PostData {
    int imageId;
    String name;
    String time;
    String remark;
    String destination;
    String distance;

    PostData(int imgId, String name, String time, String remark, String destination, String distance)
    {
        this.imageId = imgId;
        this.name = name;
        this.time = time;
        this.remark = remark;
        this.destination = destination;
        this.distance = distance;
    }
}
