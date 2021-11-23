import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Constrain1();
        Constrain2();
        Constrain3();
        Constrain4();
        Constrain5();
        Constrain6();
        Constrain7();
        Constraint8();
        Constraint9();
        Constrain10();
        Constrain11();
        Constraint12();
        Constraint13();
    }

    private static void Constrain1() {
        //test constraint 1 - no more than 2 hotels from the same group in 1 city - implemented in group
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

        System.out.println("Constraint 1: need True: " + m1.checkModelConstraints()); // should print False
        m1.create_link_group_hotel(h3, g);
        System.out.println("Constraint 1: need False: " + m1.checkModelConstraints());
    }

    private static void Constrain2() {
        Model m1;
        Hotel h1;//test constraint 2- create 5 res for client and check if he got at least 1 vip room - implemented in client
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

        System.out.println("Constraint 2: need True: " + m1.checkModelConstraints());
        m1.create_link_reservationSet_reservation(reservationSet, res5);
        m1.create_link_reservation_roomCategory(res5, roomCategory);
        System.out.println("Constraint 2: need False: " + m1.checkModelConstraints());
    }

    private static void Constrain3() {
        Model m1;
        Hotel h1;
        Client c1;
        Room r1;
        RoomCategory roomCategory;
        ReservationSet reservationSet;
        Reservation res1;
        Hotel h2;//test constraint 3 - connect booking to hotel 1 and reservation to hotel 2 - they should have the same hotel - implemented in Booking
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

        System.out.println("Constraint 3: need False: " + m1.checkModelConstraints());
    }

    private static void Constrain4() {
        Model m1;
        Hotel h1;
        Hotel h2;
        Group g;//Constraint 4 - hotels in the same group have the same services - implemented in Group
        m1 = new Model();
        h1 = new Hotel("Haifa", "Dan Panorama", 4);
        h2 = new Hotel("aa", "bb", 123);
        g = new Group(1);
        RegularService s1 = new RegularService("1");
        RegularService s2 = new RegularService("2");
        VipService s3 = new VipService("3");
        VipService s4 = new VipService("4");
        HotelService hotelService = new HotelService(5, 5);
        HotelService hotelService1 = new HotelService(5, 5);
        HotelService hotelService2 = new HotelService(5, 5);
        HotelService hotelService3 = new HotelService(5, 5);
        HotelService hotelService4 = new HotelService(5, 5);

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

        System.out.println("Constraint 4: need True: " + m1.checkModelConstraints());

        m1.create_link_hotel_service_hotelService(h1, s3, hotelService2);
        System.out.println("Constraint 4: need False: " + m1.checkModelConstraints());
    }

    private static void Constrain5() {
        Model m1;
        Hotel h1;
        Booking b1;
        Client c1;
        Reservation res1;
        ReservationSet reservationSet;//const 5 - vip service belong to vip room
        m1 = new Model();
        h1 = new Hotel("Haifa", "Dan Panorama", 4);
        c1 = new Client(1, 20, "adi", "Haifa");
        Room room1 = new Room(1);
        RoomCategory roomCategoryVIP = new RoomCategory(200, RoomCategory.RoomType.VIP);
        HotelService hotelServiceReg = new HotelService(5, 5);
        VipService vipService = new VipService("3");
        RegularService regularService = new RegularService("1");

        m1.addObjectToModel(h1);
        m1.addObjectToModel(room1);
        m1.addObjectToModel(roomCategoryVIP);
        m1.addObjectToModel(hotelServiceReg);
        m1.addObjectToModel(vipService);

        m1.create_link_hotel_room(room1, h1);
        m1.create_link_room_roomCategory(room1, roomCategoryVIP);
        m1.create_link_hotel_service_hotelService(h1, regularService, hotelServiceReg);

        b1 = new Booking(Model.getDateFromString("21-12-2019"), room1);
        m1.addObjectToModel(b1);

        res1 = new Reservation(Model.getDateFromString("21-12-2009"), Model.getDateFromString("11-12-2019"), 15);
        m1.addObjectToModel(res1);

        reservationSet = new ReservationSet();
        m1.addObjectToModel(reservationSet);
        m1.addObjectToModel(c1);

        Review review = new Review(10, "asdasd", Model.getDateFromString("21-12-2009"));
        m1.addObjectToModel(review);

        m1.create_link_room_Booking(room1, b1);
        m1.create_link_booking_review(b1, review);
        m1.create_link_reservation_roomCategory(res1, roomCategoryVIP);
        m1.create_link_reservationSet_reservation(reservationSet, res1);
        m1.create_link_reservation_booking(b1, res1);
        m1.create_link_hotelService_booking(hotelServiceReg, b1);
        m1.create_link_client_hotel_reservationSet(c1, h1, reservationSet);

        System.out.println("Constraint 5: need False: " + m1.checkModelConstraints());
    }

    private static void Constrain6() {
        Model m1;
        Hotel h1;
        Room r1;
        RoomCategory roomCategory;// Constraint 6 - creating 3 rooms with different type, check that less then 10% are vips -> implemented in hotel

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
        System.out.println("Constraint 6: need True: " + m1.checkModelConstraints());
        m1.create_link_room_roomCategory(r3, roomCategory3);
        m1.create_link_hotel_room(r3, h1);
        System.out.println("Constraint 6: need False: " + m1.checkModelConstraints());
    }

    private static void Constrain7() {
        Model m1;
        Room room1;
        Date orDate;
        Date reqDate;// Constraint 7  במלונות בעיר VEGAS" "LAS גיל האורח הרשום על ההזמנה חייב להיות 21 ומעלה.
        m1 = new Model();
        Client client1 = new Client(1, 22, " Dolev ", " Tel Aviv");
        Hotel hotel1 = new Hotel(" Las Vegas ", " Paris ", 5);
        room1 = new Room(404);
        ReservationSet reservationSet1 = new ReservationSet();
        orDate = Model.getDateFromString(" 25-12-2019");
        reqDate = Model.getDateFromString(" 01-01-2020 ");
        Reservation reservation1 = new Reservation(orDate, reqDate, 100);

        RoomCategory roomCategory1 = new RoomCategory(200, RoomCategory.RoomType.BASIC);
        m1.addObjectToModel(client1);
        m1.addObjectToModel(hotel1);
        m1.addObjectToModel(reservationSet1);
        m1.addObjectToModel(reservation1);
        m1.addObjectToModel(room1);

        m1.create_link_client_hotel_reservationSet(client1, hotel1, reservationSet1);
        m1.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m1.create_link_reservation_roomCategory(reservation1, roomCategory1);
        m1.create_link_hotel_room(room1, hotel1);
        m1.create_link_room_roomCategory(room1, roomCategory1);
        System.out.println("Constraint 7: need True: " + m1.checkModelConstraints());

        m1 = new Model();
        client1 = new Client(1, 20, " Dolev ", " Tel Aviv");
        hotel1 = new Hotel("Las Vegas", " Paris ", 5);
        room1 = new Room(404);
        reservationSet1 = new ReservationSet();
        orDate = Model.getDateFromString(" 25-12-2019");
        reqDate = Model.getDateFromString(" 01-01-2020 ");
        reservation1 = new Reservation(orDate, reqDate, 100);

        roomCategory1 = new RoomCategory(200, RoomCategory.RoomType.BASIC);
        m1.addObjectToModel(client1);
        m1.addObjectToModel(hotel1);
        m1.addObjectToModel(reservationSet1);
        m1.addObjectToModel(reservation1);
        m1.addObjectToModel(room1);

        m1.create_link_client_hotel_reservationSet(client1, hotel1, reservationSet1);
        m1.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m1.create_link_reservation_roomCategory(reservation1, roomCategory1);
        m1.create_link_hotel_room(room1, hotel1);
        m1.create_link_room_roomCategory(room1, roomCategory1);

        System.out.println("Constraint 7: need False: " + m1.checkModelConstraints());
    }

    private static void Constraint8() {
        Model m1;
        Client client1;
        Hotel hotel1;
        Room room1;
        ReservationSet reservationSet1;
        Reservation reservation1;
        Booking b1;//Constraint  8 רמת החדר שהושם בפועל עבור אורח, יכולה להיות בקטגוריה שהוזמנה או גבוהה יותר
        m1 = new Model();
        client1 = new Client(1, 26, " Dolev ", " Tel Aviv");
        hotel1 = new Hotel("Las Vegas", " Paris ", 5);
        room1 = new Room(404);
        reservationSet1 = new ReservationSet();
        Date orDate1 = Model.getDateFromString(" 25-12-2019");
        Date reqDate1 = Model.getDateFromString(" 01-01-2020 ");
        reservation1 = new Reservation(orDate1, reqDate1, 100);
        RoomCategory roomVIP = new RoomCategory(200, RoomCategory.RoomType.BASIC);
        RoomCategory roomREG = new RoomCategory(200, RoomCategory.RoomType.VIP);
        b1 = new Booking(orDate1, room1);
        m1.addObjectToModel(client1);
        m1.addObjectToModel(hotel1);
        m1.addObjectToModel(reservationSet1);
        m1.addObjectToModel(reservation1);
        m1.addObjectToModel(room1);
        m1.addObjectToModel(roomVIP);
        m1.addObjectToModel(roomREG);
        m1.addObjectToModel(b1);
        m1.create_link_client_hotel_reservationSet(client1, hotel1, reservationSet1);
        m1.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m1.create_link_reservation_booking(b1, reservation1);
        m1.create_link_hotel_room(room1, hotel1);
        m1.create_link_reservation_roomCategory(reservation1, roomREG);
        m1.create_link_room_roomCategory(room1, roomVIP);
        m1.create_link_room_Booking(room1, b1);
        System.out.println("Constraint 8: need False : " + m1.checkModelConstraints());
    }

    private static void Constraint9() {
        Model m1;
        Client client1;
        Hotel hotel1;
        ReservationSet reservationSet1;
        Reservation reservation1;
        Room r1;
        Booking b1;
        HotelService hotelService;
        RoomCategory roomCategory1;//Constraint  .9 לקוח שהזמין שירות מסוג VIP מחויב למלא ביקורת על המלון.
        m1 = new Model();
        client1 = new Client(1, 25, " Dolev ", " Tel Aviv");
        hotel1 = new Hotel("Las Vegas", " Paris ", 5);
        reservationSet1 = new ReservationSet();
        Date orDate = Model.getDateFromString(" 25-12-2019");
        Date reqDate = Model.getDateFromString(" 01-01-2020 ");
        reservation1 = new Reservation(orDate, reqDate, 100);
        r1 = new Room(12);
        b1 = new Booking(Model.getDateFromString("21-12-2019"), r1);
        hotelService = new HotelService(5, 5);
        VipService vipService1 = new VipService("3");
        roomCategory1 = new RoomCategory(200, RoomCategory.RoomType.BASIC);

        m1.addObjectToModel(client1);
        m1.addObjectToModel(hotel1);
        m1.addObjectToModel(reservationSet1);
        m1.addObjectToModel(reservation1);
        m1.addObjectToModel(r1);
        m1.addObjectToModel(b1);
        m1.addObjectToModel(hotelService);
        m1.addObjectToModel(vipService1);
        m1.addObjectToModel(roomCategory1);

        m1.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m1.create_link_reservation_roomCategory(reservation1, roomCategory1);
        m1.create_link_client_hotel_reservationSet(client1, hotel1, reservationSet1);
        m1.create_link_hotel_service_hotelService(hotel1, vipService1, hotelService);
        m1.create_link_hotel_room(r1, hotel1);
        m1.create_link_hotelService_booking(hotelService, b1);
        m1.create_link_reservation_booking(b1, reservation1);
        m1.create_link_room_Booking(r1, b1);
        m1.create_link_room_roomCategory(r1, roomCategory1);

        System.out.println("Constraint 9: need False: " + m1.checkModelConstraints());
        Review rev = new Review(10, "very good", Model.getDateFromString("21-12-2019"));
        m1.addObjectToModel(rev);
        m1.create_link_booking_review(b1, rev);
        System.out.println("Constraint 9: need True: " + m1.checkModelConstraints());
    }

    private static void Constrain10() {
        Model m1;
        Client client1;
        Hotel hotel1;
        ReservationSet reservationSet1;
        Reservation reservation1;
        Room r1;
        Booking b1;
        HotelService hotelService;
        VipService vipService1;
        RoomCategory roomCategory1;
        Review rev;//Constraint 10   ממוצע הדירוג הביקורות (rank) עבור מלונות שהדירוג (Rate) שלהם הוא 5 כוכבים גדול מ7.5-
        m1 = new Model();
        client1 = new Client(1, 25, " Dolev ", " Tel Aviv");
        hotel1 = new Hotel("Las Vegas", " Paris ", 5);
        reservationSet1 = new ReservationSet();
        Date orDate = Model.getDateFromString(" 25-12-2019");
        Date reqDate = Model.getDateFromString(" 01-01-2020 ");
        reservation1 = new Reservation(orDate, reqDate, 100);
        r1 = new Room(12);
        b1 = new Booking(Model.getDateFromString("21-12-2019"), r1);
        hotelService = new HotelService(5, 5);
        vipService1 = new VipService("3");
        roomCategory1 = new RoomCategory(200, RoomCategory.RoomType.VIP);

        m1.addObjectToModel(client1);
        m1.addObjectToModel(hotel1);
        m1.addObjectToModel(reservationSet1);
        m1.addObjectToModel(reservation1);
        m1.addObjectToModel(r1);
        m1.addObjectToModel(b1);
        m1.addObjectToModel(hotelService);
        m1.addObjectToModel(vipService1);
        m1.addObjectToModel(roomCategory1);

        m1.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m1.create_link_reservation_roomCategory(reservation1, roomCategory1);
        m1.create_link_client_hotel_reservationSet(client1, hotel1, reservationSet1);
        m1.create_link_hotel_service_hotelService(hotel1, vipService1, hotelService);
        m1.create_link_hotel_room(r1, hotel1);
        m1.create_link_hotelService_booking(hotelService, b1);
        m1.create_link_reservation_booking(b1, reservation1);
        m1.create_link_room_Booking(r1, b1);
        m1.create_link_room_roomCategory(r1, roomCategory1);
        rev = new Review(5, "very good", Model.getDateFromString("21-12-2019"));
        m1.addObjectToModel(rev);
        m1.create_link_booking_review(b1, rev);

        Room room2 = new Room(3123);
        Review rev2 = new Review(10, "very good", Model.getDateFromString("21-12-2019"));
        Booking b2 = new Booking(Model.getDateFromString("21-10-2019"), room2);
        Reservation reservation2 = new Reservation(orDate, reqDate, 156);

        m1.addObjectToModel(room2);
        m1.addObjectToModel(rev2);
        m1.addObjectToModel(b2);
        m1.addObjectToModel(reservation2);

        m1.create_link_hotel_room(room2, hotel1);
        m1.create_link_room_roomCategory(room2, roomCategory1);
        m1.create_link_room_Booking(room2, b2);
        m1.create_link_reservation_roomCategory(reservation2, roomCategory1);
        m1.create_link_reservation_booking(b2, reservation2);
        m1.create_link_reservationSet_reservation(reservationSet1, reservation2);
        m1.create_link_booking_review(b2, rev2);

        //todo : need to check with couple of review
        System.out.println("Constraint 10: need False: " + m1.checkModelConstraints());
    }

    private static void Constrain11() {
        Model m1;
        Hotel hotel1;
        HotelService hotelService;
        HotelService hotelService1;
        VipService vipService1;
        RegularService regularService;
        HotelService hotelService2;//Constraint 11 לא קיימים במלון 2 שירותים בעלי אותו השם.
        // implement constraint on hotel
        m1 = new Model();
        hotel1 = new Hotel("Las Vegas", " Paris ", 5);
        hotelService = new HotelService(5, 5);
        hotelService1 = new HotelService(5, 5);
        vipService1 = new VipService("vip1");
        regularService = new RegularService("reg");

        m1.addObjectToModel(hotel1);
        m1.addObjectToModel(hotelService);
        m1.addObjectToModel(hotelService1);
        m1.addObjectToModel(vipService1);
        m1.addObjectToModel(regularService);

        m1.create_link_hotel_service_hotelService(hotel1, vipService1, hotelService);
        m1.create_link_hotel_service_hotelService(hotel1, regularService, hotelService1);

        //System.out.println("Constraint 11: need true: " + m1.checkModelConstraints());
        hotelService2 = new HotelService(5, 5);
        m1.addObjectToModel(hotelService2);
        RegularService regularService2 = new RegularService("reg");
        m1.addObjectToModel(regularService2);
        m1.create_link_hotel_service_hotelService(hotel1, regularService2, hotelService2);
        System.out.println("Constraint 11: need False: " + m1.checkModelConstraints());
    }

    private static void Constraint12() {
        Model m1;
        Client client1;
        ReservationSet reservationSet1;
        Reservation reservation1;
        Reservation reservation2;
        Room r1;
        Booking b1;
        Booking b2;
        HotelService hotelService;
        HotelService hotelService2;
        RegularService regularService2;
        RoomCategory roomCategory1;//Constraint 12 ההכנסות של בית מלון מהשירותים השונים יהיו גבוהים בכל שנה מהשנה הקודמת לה.
        // implement in hotel
        m1 = new Model();
        client1 = new Client(1, 25, " Dolev ", " Tel Aviv");
        Hotel hotel2 = new Hotel("Las Vegas", " Paris ", 5);
        reservationSet1 = new ReservationSet();
        Date orDate = Model.getDateFromString(" 25-12-2019");
        Date reqDate = Model.getDateFromString(" 01-01-2020 ");
        reservation1 = new Reservation(orDate, reqDate, 100);
        reservation2 = new Reservation(orDate, reqDate, 101);
        r1 = new Room(12);
        b1 = new Booking(Model.getDateFromString("21-12-2019"), r1);
        b2 = new Booking(Model.getDateFromString("21-12-2020"), r1);
        hotelService = new HotelService(5, 5);
        hotelService2 = new HotelService(4, 5);
        RegularService regularService1 = new RegularService("43");
        regularService2 = new RegularService("41");
        roomCategory1 = new RoomCategory(200, RoomCategory.RoomType.BASIC);

        m1.addObjectToModel(client1);
        m1.addObjectToModel(hotel2);
        m1.addObjectToModel(reservationSet1);
        m1.addObjectToModel(reservation1);
        m1.addObjectToModel(reservation2);
        m1.addObjectToModel(r1);
        m1.addObjectToModel(b1);
        m1.addObjectToModel(hotelService);
        m1.addObjectToModel(regularService1);
        m1.addObjectToModel(regularService2);
        m1.addObjectToModel(roomCategory1);
        m1.addObjectToModel(hotelService2);
        m1.addObjectToModel(b2);

        m1.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m1.create_link_reservationSet_reservation(reservationSet1, reservation2);
        m1.create_link_client_hotel_reservationSet(client1, hotel2, reservationSet1);

        m1.create_link_hotel_service_hotelService(hotel2, regularService1, hotelService);
        m1.create_link_hotel_service_hotelService(hotel2, regularService2, hotelService2);

        m1.create_link_hotel_room(r1, hotel2);
        m1.create_link_hotelService_booking(hotelService, b1);
        m1.create_link_hotelService_booking(hotelService2, b2);
        m1.create_link_reservation_booking(b1, reservation1);
        m1.create_link_reservation_booking(b2, reservation2);
        m1.create_link_room_Booking(r1, b1);
        m1.create_link_room_Booking(r1, b2);
        m1.create_link_room_roomCategory(r1, roomCategory1);
        m1.create_link_reservation_roomCategory(reservation1, roomCategory1);
        m1.create_link_reservation_roomCategory(reservation2, roomCategory1);

        System.out.println("Constraint 12: need False: " + m1.checkModelConstraints());
    }

    private static void Constraint13() {
        Model m1;
        Client client1;
        Hotel hotel2;
        ReservationSet reservationSet1;
        Reservation reservation1;
        Reservation reservation2;
        Room r1;
        Booking b1;
        Booking b2;
        HotelService hotelService;
        HotelService hotelService2;
        RegularService regularService1;
        RegularService regularService2;
        RoomCategory roomCategory1;
        //Constraint 13 כל השירותים שניתנים במהלך אירוח שייכים למלון המארח
        // implement in hotel
        m1 = new Model();
        client1 = new Client(1, 25, " Dolev ", " Tel Aviv");
        hotel2 = new Hotel("Las Vegas", " Paris ", 5);
        reservationSet1 = new ReservationSet();
        Date orDate = Model.getDateFromString(" 25-12-2019");
        Date reqDate = Model.getDateFromString(" 01-01-2020 ");
        reservation1 = new Reservation(orDate, reqDate, 100);
        reservation2 = new Reservation(orDate, reqDate, 101);
        Hotel hotel1 = new Hotel(" Las Vegas ", " Paris ", 5);

        r1 = new Room(12);
        b1 = new Booking(Model.getDateFromString("21-12-2019"), r1);
        b2 = new Booking(Model.getDateFromString("21-12-2020"), r1);
        hotelService = new HotelService(5, 5);
        hotelService2 = new HotelService(10, 5);
        regularService1 = new RegularService("43");
        regularService2 = new RegularService("41");
        roomCategory1 = new RoomCategory(200, RoomCategory.RoomType.BASIC);

        m1.addObjectToModel(client1);
        m1.addObjectToModel(hotel2);
        m1.addObjectToModel(reservationSet1);
        m1.addObjectToModel(reservation1);
        m1.addObjectToModel(reservation2);
        m1.addObjectToModel(r1);
        m1.addObjectToModel(b1);
        m1.addObjectToModel(hotelService);
        m1.addObjectToModel(regularService1);
        m1.addObjectToModel(regularService2);
        m1.addObjectToModel(roomCategory1);
        m1.addObjectToModel(hotelService2);
        m1.addObjectToModel(b2);

        m1.create_link_reservationSet_reservation(reservationSet1, reservation1);
        m1.create_link_reservationSet_reservation(reservationSet1, reservation2);
        m1.create_link_client_hotel_reservationSet(client1, hotel2, reservationSet1);

        m1.create_link_hotel_service_hotelService(hotel2, regularService1, hotelService);
        m1.create_link_hotel_service_hotelService(hotel1, regularService2, hotelService2);

        m1.create_link_hotel_room(r1, hotel2);
        m1.create_link_hotelService_booking(hotelService, b1);
        m1.create_link_hotelService_booking(hotelService2, b2);
        m1.create_link_reservation_booking(b1, reservation1);
        m1.create_link_reservation_booking(b2, reservation2);
        m1.create_link_room_Booking(r1, b1);
        m1.create_link_room_Booking(r1, b2);
        m1.create_link_room_roomCategory(r1, roomCategory1);
        m1.create_link_reservation_roomCategory(reservation1, roomCategory1);
        m1.create_link_reservation_roomCategory(reservation2, roomCategory1);

        System.out.println("Constraint 13: need False: " + m1.checkModelConstraints());
    }
}