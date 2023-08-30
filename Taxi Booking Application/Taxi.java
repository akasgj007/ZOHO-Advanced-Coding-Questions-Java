import java.util.*;

public class Taxi {

  static int taxiCount = 0;
  int id;
  boolean booked;
  char currentSpot;
  int freeTime;
  int totalEarnings;
  List<String> trips; // details of all trips of this taxi

  public Taxi() {
    booked = false;
    currentSpot = 'A';
    freeTime = 6;
    totalEarnings = 0;
    trips = new ArrayList<>();
    taxiCount += 1;
    id = taxiCount;
  }

  public void setDetails(
    boolean booked,
    char currentSpot,
    int freeTime,
    int totalEarnings,
    String tripDetail
  ) {
    this.booked = booked;
    this.currentSpot = currentSpot;
    this.freeTime = freeTime;
    this.totalEarnings = totalEarnings;
    this.trips.add(tripDetail);
  }

  public void printDetails() {
    System.out.println(
      "Taxi - " + this.id + " | Total Earnings - " + this.totalEarnings + "\n"
    );
    System.out.println(
      "TaxiID      BookingID       CustomerID      From        To      PickupTime      DropTime        Amount"
    );
    System.out.println(
      "----------------------------------------------------------------------------------------------------------------"
    );
    if (trips.size() == 0) {
      System.out.println("No data found...");
    } else {
      for (String trip : trips) {
        System.out.println(id + "           " + trip);
      }
    }

    System.out.println(
      "----------------------------------------------------------------------------------------------------------------\n\n"
    );
  }

  public void printTaxiDetails() {
    System.out.println(
      "Taxi - " +
      this.id +
      " | Total Earnings - " +
      this.totalEarnings +
      " | Current Spot - " +
      this.currentSpot +
      " | Free Time - " +
      this.freeTime
    );
  }
}
