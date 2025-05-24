import Controller.StoreController;
import Model.Store;
import View.StoreView;

public class Main {
    public static void main(String[] args) {
        Store store = initializeStore();
        StoreController controller = new StoreController(store);
        StoreView view = new StoreView(controller);
        view.showMainMenu();
    }

    private static Store initializeStore() {
        // Initialize store with default values or get from user input
        return new Store(0.2, 0.3, 0.1, 3);
    }
}