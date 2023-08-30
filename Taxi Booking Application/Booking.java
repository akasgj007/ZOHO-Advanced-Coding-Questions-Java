import java.util.*;

public class Booking {

  private static List<Taxi> createTaxis(int numberOfTaxis) {
    List<Taxi> taxis = new ArrayList<>();
    for (int i = 0; i < numberOfTaxis; i++) {
      taxis.add(new Taxi());
    }
    return taxis;
  }

  private static List<Taxi> getFreeTaxis(
    List<Taxi> taxis,
    char pickupPoint,
    int pickupTime
  ) {
    List<Taxi> freeTaxis = new ArrayList<>();
    for (Taxi t : taxis) {
      if (
        t.freeTime <= pickupTime && // taxi should be free
        (
          Math.abs((t.currentSpot - '0') - (pickupPoint - '0')) <=
          (pickupTime - t.freeTime)
        ) // taxi should
        // have enough
        // have time to
        // reach
        // customer
        // before
        // pickuptime
        // ('B' - 'F') 3 <= 1 (8 - 7)
      ) freeTaxis.add(t);
    }
    return freeTaxis;
  }

  private static void bookTaxi(
    int customerId,
    char pickupPoint,
    char dropPoint,
    int pickupTime,
    List<Taxi> freeTaxis
  ) {
    // to find nearest
    int min = 999;

    // distance betweem pickup and drop
    int distanceBetweenPickupAndDrop = 0;

    // current trip earning
    int earnings = 0;

    // when taxi will free next time
    int nextFreeTime = 0;

    // where taxi is after the trip is over
    char nextSpot = 'X';

    // booked Taxi
    Taxi bookedTaxi = null;

    // all details of current trip as string
    String tripDetails = "";

    for (Taxi t : freeTaxis) {
      int distanceBetweeenCustomerAndTaxi =
        Math.abs((t.currentSpot - '0') - (pickupPoint - '0')) * 15;

      if (distanceBetweeenCustomerAndTaxi < min) {
        bookedTaxi = t;

        // distance between pickup and drop => (pickup - drop)*15
        distanceBetweenPickupAndDrop =
          Math.abs((dropPoint - '0') - (pickupPoint - '0')) * 15;

        // calculate fare for this trip (5 is for first 5 km is 100 already calculated)
        earnings = 100 + (distanceBetweenPickupAndDrop - 5) * 10;

        // drop time calculation /15 is for already *15
        int dropTime = pickupTime + distanceBetweenPickupAndDrop / 15;

        // when taxi will be free nextime
        nextFreeTime = dropTime;

        // where will taxi be after drop
        nextSpot = dropPoint;

        // customer id is same to booking id
        tripDetails =
          customerId +
          "                " +
          customerId +
          "              " +
          pickupPoint +
          "            " +
          dropPoint +
          "        " +
          pickupTime +
          "               " +
          dropTime +
          "              " +
          earnings;
      }
    }
    // setting the corresponding details to alloted taxi
    bookedTaxi.setDetails(
      true,
      nextSpot,
      nextFreeTime,
      bookedTaxi.totalEarnings + earnings,
      tripDetails
    );
    // BOOKED SUCCESSFULLY
    System.out.println("Taxi - " + bookedTaxi.id + " is booked\n\n");
  }

  public static void main(String[] args) {
    List<Taxi> taxis = createTaxis(4);
    try (Scanner sc = new Scanner(System.in)) {
      int id = 1;

      while (true) {
        System.out.println("Please enter one of the options from below");
        System.out.println("1. Book a Taxi");
        System.out.println("2. Print All Taxi Details");

        int option = sc.nextInt();

        switch (option) {
          case 1:
            {
              int customerId = id;
              System.out.println("Enter Pickup Point :");
              char pickupPoint = sc.next().charAt(0);
              System.out.println("Enter Drop Point :");
              char dropPoint = sc.next().charAt(0);
              System.out.println("Enter Pickup Time : ");
              int pickupTime = sc.nextInt();

              if (
                (pickupPoint >= 'A' && pickupPoint <= 'F') &&
                (dropPoint >= 'A' && dropPoint <= 'F')
              ) {
                // get all the free taxis that can reach customer on or before time
                List<Taxi> freeTaxis = getFreeTaxis(
                  taxis,
                  pickupPoint,
                  pickupTime
                );

                if (freeTaxis.size() == 0) {
                  System.out.println("No Taxis are available at this time...");
                  System.out.println("Exitting...");
                  return;
                }

                // sort taxis based on the earnings
                Collections.sort(
                  freeTaxis,
                  (a, b) -> a.totalEarnings - b.totalEarnings
                );

                // book free taxi nearest to us
                bookTaxi(
                  customerId,
                  pickupPoint,
                  dropPoint,
                  pickupTime,
                  freeTaxis
                );
                id++;
              } else {
                System.out.println(
                  "Valid pickup and drop points are A,B,C,D,E,F."
                );
                System.out.println("Exitting...");
                return;
              }
            }
            break;
          case 2:
            {
              // two functions to print details
              System.out.println(
                "**********************************************************************************************************"
              );
              for (Taxi t : taxis) t.printTaxiDetails();
              System.out.println(
                "**********************************************************************************************************"
              );
              System.out.println();
              for (Taxi t : taxis) t.printDetails();
              break;
            }
          default:
            {
              System.out.println("Please enter valid option!!!");
              return;
            }
        }
      }
    }
  }
}
