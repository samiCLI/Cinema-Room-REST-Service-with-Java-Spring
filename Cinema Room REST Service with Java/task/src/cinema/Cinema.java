package cinema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cinema {
    private int rowsNumber;
    private int columnsNumber;

    private List<Seat> seatList = new ArrayList<>();
    private List<Seat> purchasedList = new ArrayList<>();

    public Cinema(int rowsNum, int columnsNum) {
        //initial rows and columns number
        this.rowsNumber = rowsNum;
        this.columnsNumber = columnsNum;
        populateSeats();
    }

    public Map<String, Object> returnJSO() {
        Map<String, Object> toRtrnMap = new HashMap<>();
        toRtrnMap.put("rows", rowsNumber);
        toRtrnMap.put("columns", columnsNumber);
        toRtrnMap.put("seats", seatList);
        return toRtrnMap;
    }

    //getters
    public int getRowsNumber() {
        return rowsNumber;
    }

    public int getColumnsNumber() {
        return columnsNumber;
    }

    public List<Seat> getSeats() {
        return seatList;
    }

    public Seat getSeat(int id) {
        return seatList.get(id);
    }

    //setters
    public void addSeat(Seat seat) {
        seatList.add(seat);
    }


    public void deleteSeat(int column, int row) {
        int index = 0;
        for (int i = 0; i < seatList.size(); i++){
            Seat currentSeat = seatList.get(i);
            if (currentSeat.getColumn() == column && currentSeat.getRow() == row) {
                index = i;
            }
            break;
        }
        seatList.get(index).changePurchased();
    }

    public void populateSeats(){
        for (int r = 1; r <= rowsNumber; r++) {
            for (int c = 1; c <= columnsNumber; c++) {
                seatList.add(new Seat(r, c));
            }
        }
    }
}