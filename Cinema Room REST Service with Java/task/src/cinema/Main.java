package cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)


    public static class PurchaseRequestBody {
        private int row;
        private int column;

        public int getRow() {
            return row;
        }

        public int getColumn(){
            return column;
        }

        public void setColumn(int newColumn) {
            this.column = newColumn;
        }
        public void setRow(int newRow) {
            this.row = newRow;
        }

        PurchaseRequestBody() {}

    }

    public static class ReturnRequestBody {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String newToken){
            this.token = newToken;
        }

        ReturnRequestBody() {};

    }

}
