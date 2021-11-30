import java.util.HashMap;


public class Client implements ITestable {
    private HashMap<Hotel, ReservationSet> reservationsHistory;
    private int id;
    private int age;
    private String name;
    private String city;

    public Client(int a_id, int a_age, String a_name, String a_city) {
        reservationsHistory = new HashMap<Hotel, ReservationSet>();
        id = a_id;
        age = a_age;
        city = a_city;
        name = a_name;
    }


    public HashMap<Hotel, ReservationSet> getReservationsHistory() {
        return reservationsHistory;
    }
    // getters
    public void addReservationSet(Hotel hotel, ReservationSet reset) {
        reservationsHistory.put(hotel, reset);
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean checkConstraints() {

        //const 2
        for (Hotel h : reservationsHistory.keySet()
        ) {
            ReservationSet r = reservationsHistory.get(h);
            if (r.getReservations().size() >= 5) {
                for (Reservation res : r.getReservations()
                ) {
                    if (res.getRoomCategory().getType() == RoomCategory.RoomType.VIP) {
                       // if(constrain9())
                            return true;
                    }
                }
            }
            else
            //if(constrain9())
                return true;
        }
        return false;
    }
    //constraint 9
    public boolean constrain9(){
        for (Hotel h : reservationsHistory.keySet()
        ) {
            HashMap<Service, HotelService> hashmap_services = h.getServices();
            for (Service s : hashmap_services.keySet()
            ) {
                if (s instanceof VipService) {
                    ReservationSet r = reservationsHistory.get(h);
                    for (Reservation res : r.getReservations()
                    ) {
                        Booking b = res.getBookings();
                        if (b.getReview() == null)
                            return false;
                    }
                }
            }
            return true;

        }
        return false;
    }
    public static boolean checkAllIntancesConstraints(Model model) {
        for (Client c :model.ClientAllInstances()
        ) {
            c.checkConstraints();

        }
        return true;
    }
}
