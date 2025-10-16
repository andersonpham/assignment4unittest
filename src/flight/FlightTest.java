package flight;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FlightTest {
	
	// Condition 1: Tests the outside the range 1 to 9 inclusive

   @Test
   void testTotalPassengersBelowZero() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("16/10/2025", "mel", false, "19/10/2025", "pvg", "economy", 0, 0, 0);  
      // Zero Passengers
      assertFalse(result);
   }
   

   @Test
   void testTotalPassengersAboveNine() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", false, "23/11/2025", "pvg", "economy", 9, 1, 1);
      // 11 Passengers, 9 adults, 1 child, 1 infant
      assertFalse(result);
   }

   // Tests Child in Emergency Row
   
   @Test
   void testChildInEmergencyRow() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", true, "23/11/2025", "pvg", "economy", 1, 1, 0);
      // 1 adult with 1 child in Emergency together
      assertFalse(result);
      
   }

   @Test
   void testChildInFirstClass() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", false, "23/11/2025", "pvg", "first", 1, 1, 0);
   // 1 adult with 1 child in first class together
      assertFalse(result);
   }

   @Test
   void testInfantInEmergencyRow() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", true, "23/11/2025", "pvg", "economy", 1, 0, 1);
      // 1 adult with 1 infant in Emergency together
      assertFalse(result);
   }

   @Test
   void testInfantInBusinessClass() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", false, "23/11/2025", "pvg", "business", 1, 0, 1);
   // 1 adult with 1 infant in business class together
      assertFalse(result);
   }

   @Test
   void testExcessChildrenPerAdults() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", false, "30/11/2025", "pvg", "economy", 1, 3, 0);
      // 1 adult, 3 children
      assertFalse(result);
   }

   @Test
   void testExcessInfantsForAdults() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", false, "23/11/2025", "pvg", "economy", 1, 0, 2);
      // 1 adult, 2 infants
      assertFalse(result);
   }

   @Test
   void testDepartureDateInPast() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("10/01/2023", "mel", false, "20/01/2023", "pvg", "economy", 1, 0, 0);
      assertFalse(result);
   }

   @Test
   void testInvalidDateFormat() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("2025/11/13", "mel", false, "2025/11/23", "pvg", "economy", 1, 0, 0);
      // yyyy/mm//dd
      assertFalse(result);
   }

   @Test
   void testReturnDateBeforeDepartureDate() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "mel", false, "10/11/2025", "pvg", "economy", 1, 0, 0);
      assertFalse(result);
   }

   @Test
   void testInvalidAirportLocation() {
      FlightSearch fs = new FlightSearch();
      boolean result = fs.runFlightSearch("13/11/2025", "nyc", false, "23/11/2025", "pvg", "economy", 1, 0, 0);
      assertFalse(result);
   }

   @Test
   void testAllInputsValid() {
      FlightSearch fs = new FlightSearch();
      boolean result1 = fs.runFlightSearch("13/11/2025", "mel", false, "23/11/2025", "pvg", "economy", 2, 2, 0);
      boolean result2 = fs.runFlightSearch("13/12/2025", "syd", false, "23/12/2025", "del", "premium economy", 1, 0, 1);
      boolean result3 = fs.runFlightSearch("05/01/2026", "lax", false, "15/01/2026", "cdg", "economy", 3, 0, 0);
      boolean result4 = fs.runFlightSearch("10/02/2026", "mel", false, "18/02/2026", "doh", "business", 1, 0, 0);

      assertTrue(result1);
      assertTrue(result2);
      assertTrue(result3);
      assertTrue(result4);
   }
   
}
