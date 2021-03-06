import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Room implements  ITestable{
    private RoomCategory roomCategory;
    private HashMap<Date,Booking> bookings;
    private int number;
    private Hotel hotel;


    public Room(int number){
        this.number = number;
        bookings = new HashMap<Date,Booking>();
    }

    public void setHotel(Hotel h){ hotel = h; }

    public void asignRoomCategory(RoomCategory roomCategory){
        this.roomCategory = roomCategory;
    }

    public void addBooking(Booking booking, Date date) {
        bookings.put(date, booking);
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public HashMap<Date, Booking> getBookings() {
        return bookings;
    }

    public int getNumber() {
        return number;
    }

    public Hotel getHotel(){ return hotel; }

    @Override
    public boolean checkConstraints() {
        //const 5
        if(this.getRoomCategory().getType()== RoomCategory.RoomType.VIP){
            for (Date d:bookings.keySet()
                 ) {
               Booking b= bookings.get(d);
               ArrayList<HotelService> hotelserv=  b.getServices();
                for (HotelService s:hotelserv
                     ) {
                    Service x = s.getService();
                    if(!(x instanceof VipService)) {

                        return false;
                                            }
                }
            }
        }
        return true;
    }

    public static boolean checkAllIntancesConstraints(Model model){
        for (Room b:model.RoomAllInstances()
        ) {

            b.checkConstraints();

        }
        return true;
    }
}
