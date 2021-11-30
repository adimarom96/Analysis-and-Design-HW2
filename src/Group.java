import java.util.HashSet;
import java.util.LinkedList;

public class Group implements  ITestable{
    private int groupId;
    HashSet<Hotel> hotels;

    public Group(int id){
        hotels = new HashSet<Hotel>();
        groupId = id;
    }



    public void addHotelToGroup(Hotel hotel){
        hotels.add(hotel);
    }

    //getters

    public int getGroupId() {
        return groupId;
    }

    public HashSet<Hotel> getHotels() {
        return hotels;
    }

    @Override
    public boolean checkConstraints() {
        // constraint 1
        Hotel hotel=null;
        for (Hotel h : hotels
             ) {
            hotel = h;
            for (Hotel h2 : hotels
            ) {
                if(h.getCity().equals(h2.getCity()) && h!=h2){
                    return false;
                }
            }
        }

        // constraint 4
        LinkedList<Service> hotel_service = new LinkedList<>();
        hotel_service.addAll(hotel.getServices().keySet());

        for (Hotel h : hotels
        ) {
            for (Service s:hotel_service
                 ) {
                if(hotel_service.size() != h.getServices().size()) // checking if both hotle serivce list the same size
                    return false;
               if(!h.getServices().containsKey(s)){
                   return false;
               }
            }
        }


        return true;
    }
    public static boolean checkAllIntancesConstraints(Model model){
        for (Group c :model.GroupAllInstances()
        ) {
            c.checkConstraints();

        }
        return true;
    }
}
