public class Main {
    public static void main(String[] args) {
        //test constraint 1
        Model m1 = new Model();
        Group g = new Group(1);
        Hotel h1 = new Hotel("Haifa", "Dan Panorama", 4);
        Hotel h2 = new Hotel("Caifa", "Dan Carmel", 4);
        Hotel h3 = new Hotel("Haifa", "Dan Carmel", 4);
        m1.addObjectToModel(g);
        m1.addObjectToModel(h1);
        m1.addObjectToModel(h2);
        m1.addObjectToModel(h3);
        m1.create_link_group_hotel(h1, g);
        m1.create_link_group_hotel(h2, g);

        System.out.println("const 1 : need True:" + m1.checkModelConstraints()); // should print false
        m1.create_link_group_hotel(h3, g);
        System.out.println("const 1 : need false:" + m1.checkModelConstraints());

        //test constraint 2
        // create 5 res for client and check if he got at least 1 vip room - implemented in client
        m1 = new Model();
        h1 = new Hotel("Haifa", "Dan Panorama", 4);
        Client c1 = new Client(1, 20, "adi", "Haifa");
        Room r1 = new Room(1);
        RoomCategory roomCategory = new RoomCategory(200, RoomCategory.RoomType.BASIC);
        ReservationSet reservationSet = new ReservationSet();
        Reservation res1 = new Reservation(Model.getDateFromString("26-12-2019"), Model.getDateFromString("25-12-2019"), 100);
        Reservation res2 = new Reservation(Model.getDateFromString("27-12-2019"), Model.getDateFromString("25-12-2019"), 101);
        Reservation res3 = new Reservation(Model.getDateFromString("29-12-2019"), Model.getDateFromString("25-12-2019"), 102);
        Reservation res4 = new Reservation(Model.getDateFromString("25-11-2019"), Model.getDateFromString("25-12-2019"), 103);
        Reservation res5 = new Reservation(Model.getDateFromString("21-12-2019"), Model.getDateFromString("25-12-2019"), 104);

        m1.addObjectToModel(h1);
        m1.addObjectToModel(c1);
        m1.addObjectToModel(r1);
        m1.addObjectToModel(roomCategory);
        m1.addObjectToModel(reservationSet);
        m1.addObjectToModel(res1);
        m1.addObjectToModel(res2);
        m1.addObjectToModel(res3);
        m1.addObjectToModel(res4);
        m1.addObjectToModel(res5);

        m1.create_link_client_hotel_reservationSet(c1, h1, reservationSet);

        m1.create_link_reservationSet_reservation(reservationSet, res1);
        m1.create_link_reservation_roomCategory(res1, roomCategory);
        m1.create_link_reservationSet_reservation(reservationSet, res2);
        m1.create_link_reservation_roomCategory(res2, roomCategory);
        m1.create_link_reservationSet_reservation(reservationSet, res3);
        m1.create_link_reservation_roomCategory(res3, roomCategory);
        m1.create_link_reservationSet_reservation(reservationSet, res4);
        m1.create_link_reservation_roomCategory(res4, roomCategory);


        m1.create_link_hotel_room(r1, h1);
        m1.create_link_room_roomCategory(r1, roomCategory);

        System.out.println("const 2 : need True:" + m1.checkModelConstraints());
        m1.create_link_reservationSet_reservation(reservationSet, res5);
        m1.create_link_reservation_roomCategory(res5, roomCategory);
        System.out.println("const 2 : need False:" + m1.checkModelConstraints());


        //test constraint 3
        // connect booking to hotel 1 and reservation to hotel 2 - they should have the same hotel
        m1 = new Model();
        h1 = new Hotel("Haifa", "Dan Panorama", 4);
        c1 = new Client(1, 20, "adi", "Haifa");
        r1 = new Room(1);
        roomCategory = new RoomCategory(200, RoomCategory.RoomType.BASIC);
        reservationSet = new ReservationSet();
        res1 = new Reservation(Model.getDateFromString("26-12-2019"), Model.getDateFromString("25-12-2019"), 100);
        Booking b1 = new Booking(Model.getDateFromString("21-12-2019"), r1);

        h2 = new Hotel("aa", "bb", 123);

        m1.addObjectToModel(h1);
        m1.addObjectToModel(h2);
        m1.addObjectToModel(c1);
        m1.addObjectToModel(r1);
        m1.addObjectToModel(b1);
        m1.addObjectToModel(roomCategory);
        m1.addObjectToModel(reservationSet);
        m1.addObjectToModel(res1);

        m1.create_link_client_hotel_reservationSet(c1, h2, reservationSet);

        m1.create_link_reservationSet_reservation(reservationSet, res1);
        m1.create_link_reservation_roomCategory(res1, roomCategory);

        m1.create_link_reservation_booking(b1, res1);
        m1.create_link_room_Booking(r1, b1);

        m1.create_link_hotel_room(r1, h1);
        m1.create_link_room_roomCategory(r1, roomCategory);

        System.out.println("const 3 : need false:" + m1.checkModelConstraints());


        //const 4
        m1 = new Model();
        h1 = new Hotel("Haifa", "Dan Panorama", 4);
        h2 = new Hotel("aa", "bb", 123);
        g = new Group(1);

        HotelService hotelService = new HotelService(5, 5);
        HotelService hotelService1 = new HotelService(5, 5);
        HotelService hotelService2 = new HotelService(5, 5);
        HotelService hotelService3 = new HotelService(5, 5);
        HotelService hotelService4 = new HotelService(5, 5);

        RegularService s1 = new RegularService("1");
        RegularService s2 = new RegularService("1");
        VipService s3 = new VipService("3");
        VipService s4 = new VipService("3");

        m1.addObjectToModel(h1);
        m1.addObjectToModel(h2);
        m1.addObjectToModel(g);
        m1.addObjectToModel(hotelService1);
        m1.addObjectToModel(hotelService2);
        m1.addObjectToModel(hotelService3);
        m1.addObjectToModel(hotelService4);
        m1.addObjectToModel(hotelService);

        m1.addObjectToModel(s1);
        m1.addObjectToModel(s2);
        m1.addObjectToModel(s3);
        m1.addObjectToModel(s4);

        m1.create_link_group_hotel(h1, g);
        m1.create_link_group_hotel(h2, g);
        m1.create_link_hotel_service_hotelService(h1, s1, hotelService1);
        m1.create_link_hotel_service_hotelService(h1, s2, hotelService2);
        m1.create_link_hotel_service_hotelService(h2, s1, hotelService3);
        m1.create_link_hotel_service_hotelService(h2, s2, hotelService4);

        System.out.println("const 4 : need True:" + m1.checkModelConstraints());

        m1.create_link_hotel_service_hotelService(h1, s3, hotelService2);
        System.out.println("const 4 : need false:" + m1.checkModelConstraints());

        // const 6 creating 3 rooms with diffrent type, check that less thean 10% are vips -> implmented in hotel

        m1 = new Model();
        h1 = new Hotel("Haifa", "Dan Panorama", 4);
        r1 = new Room(1);
        Room r2 = new Room(1);
        Room r3 = new Room(1);
        roomCategory = new RoomCategory(200, RoomCategory.RoomType.BASIC);
        RoomCategory roomCategory2 = new RoomCategory(200, RoomCategory.RoomType.BASIC);
        RoomCategory roomCategory3 = new RoomCategory(200, RoomCategory.RoomType.VIP);

        m1.addObjectToModel(h1);
        m1.addObjectToModel(r1);
        m1.addObjectToModel(r2);
        m1.addObjectToModel(roomCategory);
        m1.addObjectToModel(roomCategory2);
        m1.addObjectToModel(roomCategory3);

        m1.create_link_hotel_room(r1, h1);
        m1.create_link_hotel_room(r2, h1);
        m1.create_link_room_roomCategory(r1, roomCategory);
        m1.create_link_room_roomCategory(r2, roomCategory2);
        System.out.println("const 6 : need True: " + m1.checkModelConstraints());
        m1.create_link_room_roomCategory(r3, roomCategory3);
        m1.create_link_hotel_room(r3, h1);
        System.out.println("const 6 : need False: " + m1.checkModelConstraints());


    }
}