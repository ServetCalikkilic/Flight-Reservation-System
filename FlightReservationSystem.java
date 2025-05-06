/**
 * @author : Servet Çalikkılıç
 * @date : 01.05.2025
 * @version : 1.0
 */


public class FlightReservationSystem {
    public static void main(String[] args) {
        try {
            // === Example 1: Domestic flight booking ===
            long departure = System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000); // 1 week later
            long arrival   = departure + (2L * 60 * 60 * 1000);                    // +2 hours

            DomesticFlight domesticFlight = new DomesticFlight(
                    "TK101", "Istanbul", "Ankara", departure, arrival, 1000.0, 0.18
            );
            long birth1 = System.currentTimeMillis() - (35L * 365 * 24 * 60 * 60 * 1000);
            Passenger p1 = new Passenger("Ahmet", "Yılmaz", birth1,
                    "A12345678", "Turkish",
                    "ahmet@example.com", "+905551234567");
            long birth2 = System.currentTimeMillis() - (28L * 365 * 24 * 60 * 60 * 1000);
            Passenger p2 = new Passenger("Ayşe", "Kaya", birth2,
                    "B98765432", "Turkish",
                    "ayse@example.com", "+905559876543");

            Passenger[] domesticPassengers = { p1, p2 };
            SeasonalPricingStrategy domPricing = new SeasonalPricingStrategy(0.18, 0.05);
            StandardBooking domBooking = new StandardBooking(domesticFlight, domesticPassengers, domPricing, true);

            boolean created1 = domBooking.createBooking();
            boolean seats1   = domBooking.assignSeats();

            System.out.println("=== Example 1 ===");
            System.out.println("Booking OK: " + created1 + ", Seats assigned: " + seats1);
            if (created1 && seats1) {
                CreditCardPayment payment1 = new CreditCardPayment(domBooking,
                        "1234567890123456", "Ahmet Yılmaz", "12/25", "123"
                );
                System.out.println("Payment OK: " + payment1.processPayment()
                        + ", PaymentStatus: " + payment1.getStatus());
                // İptal & iade örneği
                if (domBooking.cancel()) {
                    double refund = domBooking.calculateRefundAmount();
                    payment1.refundPayment();
                    System.out.println("Cancelled & refunded: " + refund + " TL");
                }
            }

            // === Example 2: International flight booking ===
            long intlDep = System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000); // 30 days later
            long intlArr = intlDep + (5L * 60 * 60 * 1000);                          // +5 hours

            InternationalFlight intlFlight = new InternationalFlight(
                    "TK202", "Istanbul", "Paris", intlDep, intlArr, 5000.0
            );
            long birth3 = System.currentTimeMillis() - (40L * 365 * 24 * 60 * 60 * 1000);
            Passenger p3 = new Passenger("Mehmet", "Çelik", birth3,
                    "C11223344", "Turkish",
                    "mehmet@example.com", "+905551112233");
            long birth4 = System.currentTimeMillis() - (30L * 365 * 24 * 60 * 60 * 1000);
            Passenger p4 = new Passenger("Elif", "Demir", birth4,
                    "D55667788", "Turkish",
                    "elif@example.com", "+905559998877");

            Passenger[] intlPassengers = { p3, p4 };
            SeasonalPricingStrategy intlPricing = new SeasonalPricingStrategy(0.20, 0.05);
            StandardBooking intlBooking = new StandardBooking(intlFlight, intlPassengers, intlPricing, false);

            boolean created2 = intlBooking.createBooking();
            boolean seats2   = intlBooking.assignSeats();
            System.out.println("\n=== Example 2 ===");
            System.out.println("Intl booking OK: " + created2 + ", Seats assigned: " + seats2);
            if (created2 && seats2) {
                CreditCardPayment payment2 = new CreditCardPayment(intlBooking,
                        "9876543210987654", "Elif Demir", "06/26", "456"
                );
                System.out.println("Payment OK: " + payment2.processPayment()
                        + ", Status: " + payment2.getStatus());
            }

            // === Example 3: Too many passengers (seat shortage) ===
            Passenger[] many = new Passenger[60];
            for (int i = 0; i < 60; i++) {
                many[i] = new Passenger("Test" + i, "User" + i, birth1,
                        "X" + i, "Country",
                        "test" + i + "@example.com", "+900000000000");
            }
            StandardBooking bigBooking = new StandardBooking(domesticFlight, many, domPricing, false);
            boolean created3 = bigBooking.createBooking();
            boolean seats3   = bigBooking.assignSeats();
            System.out.println("\n=== Example 3 ===");
            System.out.println("Big booking created: " + created3
                    + ", Seats assigned (should be false): " + seats3);

            // === Example 4: Change request too late ===
            long closeDep = System.currentTimeMillis() + (10L * 60 * 60 * 1000); // 10h later
            long closeArr = closeDep + (1L * 60 * 60 * 1000);                    // +1h
            DomesticFlight lateFlight = new DomesticFlight(
                    "TK103", "Istanbul", "Izmir", closeDep, closeArr, 500.0, 0.18
            );
            Passenger lateP = new Passenger("Test", "Late", birth1,
                    "L12345", "Turkish",
                    "late@example.com", "+900000000000");
            StandardBooking lateBooking = new StandardBooking(
                    lateFlight, new Passenger[]{lateP},
                    new SeasonalPricingStrategy(0.18,0.0), false
            );
            lateBooking.createBooking();
            lateBooking.assignSeats();
            ChangeRequest req = new ChangeRequest(
                    lateBooking.getBookingId(),
                    closeDep + (2L * 60 * 60 * 1000),
                    closeArr + (2L * 60 * 60 * 1000),
                    new String[]{"D1"}
            );
            System.out.println("\n=== Example 4 ===");
            System.out.println("Change allowed (should be false): "
                    + ((IChangeable) lateFlight).change(req));

            // === Example 5: Cancellation not allowed ===
            boolean cancelLate = lateBooking.cancel();
            System.out.println("\n=== Example 5 ===");
            System.out.println("Cancellation allowed (should be false): " + cancelLate);

            // === Example 6: Invalid payment details ===
            CreditCardPayment badPay = new CreditCardPayment(
                    lateBooking, "1111222233334444", "Late User", "01/20", "12"
            );
            System.out.println("\n=== Example 6 ===");
            System.out.println("Process bad payment (should be false): "
                    + badPay.processPayment());

            // === Example 7: Promo code application ===
            SeasonalPricingStrategy promoStrat = new SeasonalPricingStrategy(0.15, 0.0);
            boolean promoOK = promoStrat.applyPromoCode("SUMMER2023");
            double promoPrice = promoStrat.calculateFinalPrice(domesticFlight, 2);
            System.out.println("\n=== Example 7 ===");
            System.out.println("Promo applied: " + promoOK
                    + ", New price for 2 pax: " + promoPrice + " TL");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}