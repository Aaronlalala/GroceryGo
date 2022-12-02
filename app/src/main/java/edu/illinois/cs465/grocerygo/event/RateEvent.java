package edu.illinois.cs465.grocerygo.event;

public class RateEvent {
    public int position;
    public float tips;
    public float rating;

    public RateEvent(int position, float tips, float rating) {
        this.position = position;
        this.tips = tips;
        this.rating = rating;
    }
}
