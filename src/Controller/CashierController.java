package Controller;

import Model.Cashier;
import Model.Store;
import java.util.List;

public class CashierController {
    private Store store;

    public CashierController(Store store) {
        this.store = store;
    }

    public void addCashier(Cashier cashier) {
        store.addCashier(cashier);
    }

    public List<Cashier> getCashiers() {
        return store.getCashiers();
    }

    public Cashier getCashierById(int id) {
        return store.getCashiers().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}