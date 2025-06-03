import Controller.CashierController;
import Controller.ProductController;
import Controller.ReceiptController;
import Controller.StoreController;
import Model.Store;
import View.*;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(0.2, 0.3, 0.1, 3);

        StoreController storeController = new StoreController(store);
        ProductController productController = new ProductController(store);
        CashierController cashierController = new CashierController(store);
        ReceiptController receiptController = new ReceiptController(store);

        ProductView productView = new ProductView(productController);
        CashierView cashierView = new CashierView(cashierController);
        ReceiptView receiptView = new ReceiptView(receiptController);
        FoodProductView foodProductView = new FoodProductView();
        NonFoodProductView nonFoodProductView = new NonFoodProductView();

        StoreView storeView = new StoreView(storeController, productView,
                cashierView, receiptView,
                foodProductView, nonFoodProductView);

        storeView.showMainMenu();
    }
}