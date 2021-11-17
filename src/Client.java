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
        for (Hotel h : reservationsHistory.keySet()
        ) {
            ReservationSet r = reservationsHistory.get(h);
            if (r.getReservations().size() >= 5) {
                for (Reservation res : r.getReservations()
                ) {
                    if (res.getRoomCategory().getType() == RoomCategory.RoomType.VIP) {
                        return true;
                    }
                }
            }
            else
                return true;
        }
        return false;
    }
    public static boolean checkAllIntancesConstraints(Model model) {
        return true;
    }
}
