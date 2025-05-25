package View;

import Model.FoodProduct;
import java.time.LocalDate;
import java.util.Scanner;

public class FoodProductView {
    private Scanner scanner;

    public FoodProductView() {
        this.scanner = new Scanner(System.in);
    }

    public LocalDate getExpirationDateInput() {
        System.out.print("Enter expiration date (YYYY-MM-DD): ");
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }
}