package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.UUID.randomUUID;


@RestController
public class CinemaController {


    Cinema cinema = new Cinema(9, 9);

    @GetMapping("/seats")
    public Map<String, Object> returnSeats() {
        return cinema.returnJSO();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> returnStats(@RequestParam Optional<String> password) {
        boolean passwordCorrect = false;
        if (password.isPresent()){
            if (password.get().equals("super_secret")) {
                passwordCorrect = true;
            }
        }

        if (passwordCorrect){
            List<Seat> seatList = cinema.getSeats();
            int available = (int) seatList.stream().filter((seat) -> seat.backPurchased() == false).count();
            int purchased = (int) seatList.stream().filter((seat) -> seat.backPurchased() == true).count();
            int income = seatList.stream().filter((seat) -> seat.backPurchased() == true).mapToInt(seat -> seat.getPrice()).sum();

            Map<String, Object> toRtrnMap = new LinkedHashMap<>();
            toRtrnMap.put("income", income);
            toRtrnMap.put("available", available);
            toRtrnMap.put("purchased", purchased);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(toRtrnMap);
        } else {
            Map<String, Object> toRtrnMap = new LinkedHashMap<>();
            toRtrnMap.put("error", "The password is wrong!");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(toRtrnMap);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> returnTicket(@RequestBody Main.ReturnRequestBody requestBody) {
        String sentToken = requestBody.getToken();
        List<Seat> seatList = cinema.getSeats();
        int index = 0;
        boolean found = false;

        for (int i = 1; i < seatList.size(); i++) {
            if (seatList.get(i).backToken().equals(sentToken)) {
                found = true;
                index = i;
                break;
            }
        }
        if (found == true) {
            Seat currentSeat = seatList.get(index);
            currentSeat.setToken("");
            currentSeat.changePurchased();
            Map<String, Object> ticketMap = new LinkedHashMap<>();
            ticketMap.put("row", currentSeat.getRow());
            ticketMap.put("column", currentSeat.getColumn());
            ticketMap.put("price", currentSeat.getPrice());
            Map<String, Object> toRtrnMap = new LinkedHashMap<>();
            toRtrnMap.put ("ticket",ticketMap);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(toRtrnMap);
        } else {
            Map<String, Object> toRtrnMap = new LinkedHashMap<>();
            toRtrnMap.put("error", "Wrong token!");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(toRtrnMap);
        }
    }

    @PostMapping("/purchase")
    public ResponseEntity<Map<String, Object>> purchaseTicket(@RequestBody Main.PurchaseRequestBody requestBody) {
        List<Seat> seatListRet = cinema.getSeats();
        boolean found = false;
        int indexOfSeat = 0;
        int column = requestBody.getColumn();
        int row = requestBody.getRow();

        for (int i = 0; i < seatListRet.size(); i++) {
            Seat currentSeat = seatListRet.get(i);
            if (currentSeat.getRow() == row && currentSeat.getColumn() == column) {
                found = true;
                indexOfSeat = i;
                break; // no need to continue searching if the seat is found
            }
        }

        if (!found) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", "The number of a row or a column is out of bounds!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMap);
        }

        Seat currentSeatX = seatListRet.get(indexOfSeat);

        if (currentSeatX.backPurchased()) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", "The ticket has been already purchased!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMap);
        } else {
            try {
                String token = String.valueOf(randomUUID());
                currentSeatX.setToken(token);
                currentSeatX.changePurchased();

                Map<String, Object> ticketMap = new LinkedHashMap<>();
                ticketMap.put("row", currentSeatX.getRow());
                ticketMap.put("column", currentSeatX.getColumn());
                ticketMap.put("price", currentSeatX.getPrice());

                Map<String, Object> toRtrnMap = new LinkedHashMap<>();
                toRtrnMap.put("token", token);
                toRtrnMap.put("ticket", ticketMap);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(toRtrnMap);
            } catch (Exception e) {
                // Log exception details
                e.printStackTrace();
                throw e;
            }
        }
    }


}
