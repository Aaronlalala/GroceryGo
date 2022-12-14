package edu.illinois.cs465.grocerygo.layout.fragment.history;

public class HistoryData {
    int imageId;
    String tips;
    String name;
    String time;
    String destination;
    // FIXME: delete if this member is useless
    boolean isRated;
    float stars;

    public HistoryData(int imageId, String name, String time, String destination) {
        this.imageId = imageId;
        this.name = name;
        this.time = time;
        this.destination = destination;
        this.isRated = false;
    }

    public HistoryData(int imageId, String tips, String name, String time, String destination, float stars, boolean isRated) {
        this.imageId = imageId;
        this.tips = tips;
        this.name = name;
        this.time = time;
        this.destination = destination;
        this.isRated = isRated;
        this.stars = stars;
    }
}
