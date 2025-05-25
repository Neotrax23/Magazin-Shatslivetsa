package View;

import Controller.CashierController;
import Model.Cashier;
import java.util.List;
import java.util.Scanner;

public class CashierView {
    private Scanner scanner;
    private CashierController controller;

    public CashierView(CashierController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void viewAllCashiers() {
        List<Cashier> cashiers = controller.getCashiers();
        if (cashiers.isEmpty()) {
            System.out.println("No cashiers available.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s%n", "ID", "Name", "Monthly Salary");
        for (Cashier c : cashiers) {
            System.out.printf("%-5d %-20s %-15.2f%n",
                    c.getId(), c.getName(), c.getMonthlySalary());
        }
    }

    public int getCashierIdInput() {
        System.out.print("Enter cashier ID: ");
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}