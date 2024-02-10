package cinema;

public class Seat {
    private int row;
    private int column;
    private int price;

    private String token = "";
    private boolean purchased = false;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        if (row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
    }

    //getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public boolean backPurchased() {
        return purchased;
    }

    public String backToken() {return token;}

    //setters
    public void changeRow(int newRow) {
        this.row = newRow;
    }

    public void setToken(String newToken) {
        this.token = newToken;
    }
    public void changeColumn(int newColumn) {
        this.column = newColumn;
    }

    public void changePurchased(){
        this.purchased = !purchased;
    }

}