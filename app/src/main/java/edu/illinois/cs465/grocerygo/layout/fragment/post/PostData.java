package edu.illinois.cs465.grocerygo.layout.fragment.post;

public class PostData {
    String name;
    String time;
    String remark;
    String destination;
    String distance;

    PostData(String name, String time, String remark, String destination, String distance)
    {
        this.name = name;
        this.time = time;
        this.remark = remark;
        this.destination = destination;
        this.distance = distance;
    }
}
