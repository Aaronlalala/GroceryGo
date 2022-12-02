package edu.illinois.cs465.grocerygo.event;

public class DeleteEvent {
   public boolean deleteMypost;
   public DeleteEvent(boolean d){
       this.deleteMypost = d;
   }
}
