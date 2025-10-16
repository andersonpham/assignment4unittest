package flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.time.format.ResolverStyle;



public class FlightSearch {
   private String  departureDate;
   private String  departureAirportCode;
   private boolean emergencyRowSeating;
   private String  returnDate;
   private String  destinationAirportCode; 
   private String  seatingClass;
   private int     adultPassengerCount;
   private int     childPassengerCount;
   private int     infantPassengerCount;

   

   // Strict date format: "dd/MM/yyyy"
   // The ResolverStyle.STRICT ensures dates other than dd/MM//yyyy is rejected
   
   private static final DateTimeFormatter DATE_FORMATTER =
         DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

   private static final Set<String> ALLOWED_CLASSES = Set.of("economy", "premium economy", "business", "first");
   private static final Set<String> ALLOWED_AIRPORTS = Set.of("syd", "mel", "lax", "cdg", "del", "pvg", "doh");

   

   public boolean runFlightSearch(String departureDate,    String departureAirportCode,   boolean emergencyRowSeating, 
                                  String returnDate,       String destinationAirportCode, String seatingClass, 
                                  int adultPassengerCount, int childPassengerCount,       int infantPassengerCount) {
      boolean valid = true;
      
      String depAirport = departureAirportCode.toLowerCase();
      String destAirport = destinationAirportCode.toLowerCase();
      String seatClass = seatingClass.toLowerCase();

      int totalPassengerCount = adultPassengerCount + childPassengerCount + infantPassengerCount;

      // Condition 1: range 1 to 9 inclusive
      
      if (totalPassengerCount < 1 || totalPassengerCount > 9){
         return false;
      }

       if (!ALLOWED_CLASSES.contains(seatClass)) {
         return false;
      }

      if (emergencyRowSeating && !seatClass.equals("economy")) {
         return false;
      }

      if (childPassengerCount > 0) {
         if (emergencyRowSeating) {
        	 return false;
         }
         else if (seatClass.equals("first")) {
        	 return false;
         }
      }

      if (infantPassengerCount > 0) {
         if (emergencyRowSeating) {
        	 return false;
         }
         else if (seatClass.equals("business")) {
        	 return false;
         }
      }

      if (childPassengerCount > adultPassengerCount * 2) {
         return false;
      }

      if (infantPassengerCount > adultPassengerCount) {
         return false;
      }

      if (!ALLOWED_AIRPORTS.contains(depAirport) || !ALLOWED_AIRPORTS.contains(destAirport)) {
         return false;
      }
      if (depAirport.equals(destAirport)) {
         return false;
      }
      
      LocalDate depDateObj;
      LocalDate retDateObj;
      try {
         depDateObj = LocalDate.parse(departureDate, DATE_FORMATTER);
         retDateObj = LocalDate.parse(returnDate, DATE_FORMATTER);
      } catch (DateTimeParseException ex) {
         return false;
      }

      LocalDate today = LocalDate.now();
      if (depDateObj.isBefore(today)) {
         return false;
      }

      if (retDateObj.isBefore(depDateObj)) {
         return false;
      }

      this.departureDate = departureDate;
      this.departureAirportCode = departureAirportCode;
      this.emergencyRowSeating = emergencyRowSeating;
      this.returnDate = returnDate;
      this.destinationAirportCode = destinationAirportCode;
      this.seatingClass = seatingClass;
      this.adultPassengerCount = adultPassengerCount;
      this.childPassengerCount = childPassengerCount;
      this.infantPassengerCount = infantPassengerCount;
      
      
      return valid;
   }
}

