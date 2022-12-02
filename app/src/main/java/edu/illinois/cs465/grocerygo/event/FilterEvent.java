package edu.illinois.cs465.grocerygo.event;

public class FilterEvent {
    public String date;
    public String dateAndTime;
    public String destination;
    public FilterEvent(String date, String dateAndTime, String destination){
        this.date = date;
        this.dateAndTime = dateAndTime;
        this.destination =destination;
    }
}
