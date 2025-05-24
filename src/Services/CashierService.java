package Services;

import Model.Cashier;
import Model.Store;

import java.util.List;

public class CashierService {
    private Store store;

    public CashierService(Store store) {
        this.store = store;
    }

    public void addCashier(Cashier cashier) {
        store.addCashier(cashier);
    }

    public List<Cashier> getCashiers() {
        return store.getCashiers();
    }
}