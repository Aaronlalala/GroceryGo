package edu.illinois.cs465.grocerygo.event;

public class PostEvent {
    public double distance;
    public String name;
    public String time;
    public String remark;
    public String destination;
    public PostEvent(double distance, String name, String time, String remark, String destination) {
        this.distance = distance;
        this.name = name;
        this.time = time;
        this.remark = remark;
        this.destination = destination;
    }
}
