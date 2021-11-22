import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Hotel implements ITestable {
    private String name;
    private HashMap<Client, ReservationSet> allReservation;
    private HashMap<Service, HotelService> services;
    private HashMap<Integer, Room> rooms;
    private String city;
    private Group group;
    private int rate;

    public Hotel(String city, String name, int rate) {
        this.city = city;
        this.name = name;
        this.rate = rate;
        rooms = new HashMap<Integer, Room>();
        allReservation = new HashMap<Client, ReservationSet>();
        services = new HashMap<Service, HotelService>();

    }

    public static boolean checkAllIntancesConstraints(Model model) {
        return true;
    }

    public void addReservationSet(Client client, ReservationSet reservationSet) {
        allReservation.put(client, reservationSet);
    }

    public void addService(Service service, HotelService hotelService) {
        services.put(service, hotelService);
    }

    public void addRoom(int roomNumber, Room room) {
        rooms.put(roomNumber, room);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public HashMap<Client, ReservationSet> getAllReservation() {
        return allReservation;
    }

    public HashMap<Service, HotelService> getServices() {
        return services;
    }

    public int getRate() {
        return rate;
    }

    @Override
    public boolean checkConstraints() {
        //constrint 6
        /*double all = rooms.keySet().size(); //todo: לבטל השחרה
        double vips = 0;
        for (Integer num: rooms.keySet()
             ) {
            if(rooms.get(num).getRoomCategory().getType() == RoomCategory.RoomType.VIP)
                vips++;
        }
        if((vips/all)>0.1)
            return false;*/

        //const 7
        if (this.city.toLowerCase().equals("las vegas"))

            for (Client c : allReservation.keySet()
            ) {
                if (c.getAge() < 21)
                    return false;
            }

        if (const10() && const11() && const12()&&const13())
            return true;
        return false;
    }

    private boolean const10() {
        if (this.rate == 5) {
            double avg = 0;
            int counter = 0;
            for (Integer num : rooms.keySet() // going throw all rooms -> booking ->reviw
            ) {
                HashMap<Date, Booking> bookingshash = rooms.get(num).getBookings();
                for (Date d : bookingshash.keySet()
                ) {
                    Booking b = bookingshash.get(d);
                    if (b.getReview() != null) {
                        avg += b.getReview().getRank();
                        counter++;
                    }
                }

            }
            avg = avg / counter;
            if (avg < 7.5)
                return false;
        }
        return true;
    }

    private boolean const11() {
        HashMap<String, Integer> names = new HashMap<>();
        for (Service s : services.keySet()
        ) {
            if (names.containsKey(s.getServiceName()))
                return false;
            names.put(s.getServiceName(), 1);
        }
        return true;
    }

    private boolean const12() {
        HashMap<Integer, Integer> price_year = new HashMap<Integer, Integer>();
        int year;

        for (Service s : services.keySet()
        ) {
            HotelService hotelService = services.get(s);
            HashSet<Booking> givenServices = hotelService.getGivenServices();
            int price = hotelService.getPrice();
            for (Booking b : givenServices
            ) {
                year = b.getDate().getYear();
                if (price_year.containsKey(year)) {
                    price_year.put(year, price_year.get(year) + price);
                } else {
                    price_year.put(year, price);
                }
            }
        }
        for (int year1 : price_year.keySet()
        ) {
            for (int year2 : price_year.keySet()
            ) {
                if (year1 == year2)
                    continue;
                if (year1 > year2 && price_year.get(year1) < price_year.get(year2))
                    return false;
            }
        }
        return true;
    }

    private boolean const13() {

        HashMap<Date, Booking> bookings_of_room = new HashMap<>();
        Booking b;
        ArrayList<HotelService> hotel_services;
        for (Integer num : rooms.keySet()
        ) {
            Room room = rooms.get(num);
            bookings_of_room = room.getBookings();
            for (Date d : bookings_of_room.keySet()
            ) {
                b = bookings_of_room.get(d);
                hotel_services = b.getServices();
                for (HotelService hs : hotel_services
                ) {
                    Service s = hs.getService();
                    if (!s.serviceAtHotels.containsKey(this))
                        return false;
                }
            }
        }
        return true;
    }
}
