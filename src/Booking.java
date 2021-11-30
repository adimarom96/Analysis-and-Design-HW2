import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Booking implements  ITestable{
    private Date date;
    private Room room;
    private ArrayList<HotelService> services;
    private Reservation reservation;
    private Review review;

    public Booking(Date a_date, Room a_room){
        date = a_date;
        room = a_room;
        services = new ArrayList<>();
    }

    public void addService(HotelService s){
        services.add(s);
    }

    public void addReview(Review a_review) {review  = a_review; }

    public void addReservation(Reservation r){
        reservation = r;
    }

    public void assignRoom(Room room){
        this.room = room;
    }

    // getters

    public Date getDate() {
        return date;
    }

    public Room getRoom() {
        return room;
    }

    public ArrayList<HotelService> getServices() {
        return services;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Review getReview() {
        return review;
    }

    @Override
    public boolean checkConstraints() {
        // constraint 3
        if(const3()&&const8()&&const9())
            return true;
        return false;

    }
    private boolean const3(){
        return  (this.reservation.getReservationSet().getHotel() == this.getRoom().getHotel());
    }

    private boolean const8(){
        HashMap< RoomCategory.RoomType,Integer> hashMap= new HashMap<>();
        hashMap.put(RoomCategory.RoomType.BASIC,1);
        hashMap.put(RoomCategory.RoomType.SUITE,2);
        hashMap.put(RoomCategory.RoomType.VIP,3);
        if(this.reservation != null && this.room !=null ) {
            RoomCategory roomCategory = reservation.getRoomCategory();
            if(roomCategory==null)
                return false;
            RoomCategory.RoomType roomType1 = roomCategory.getType();
            Room room = this.getRoom();
            RoomCategory.RoomType roomType2 = room.getRoomCategory().getType();
            int room1 = hashMap.get(roomType1);
            int room2 = hashMap.get(roomType2);
            if(room1<=room2) {
                return true;
            }

        }
        return false;
    }
    private  boolean const9(){
        for (HotelService hs:this.getServices()
        ) {
            Service service = hs.getService();
            if (service instanceof VipService) {
                if(this.review == null)
                    return false;
            }
        }

        return true;
    }

    public static boolean checkAllIntancesConstraints(Model model){
        for (Booking b:model.BookingAllInstances()
             ) {
            b.checkConstraints();
//            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
//                System.out.println(ste);
//            }

        }
        return true;
    }
}
