import cz.surin.dusan.Restaurant.*;
import exceptions.DishException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CookBook cookBook = new CookBook();
        Order order = new Order();
        OrderManager orderManager = new OrderManager();
        RestaurantManager restaurantManager = new RestaurantManager();

        loadDataFromExistFiles(orderManager);
        pripareDateForTesting(cookBook,order);
        order.getOrderById(2).settingsFulfilmentTime();
        order.printTotalDishPriceForTable(15);
        printForRestaurantManagement(restaurantManager);
        saveDishesAndOrdersToFiles(cookBook, orderManager);



    }
    private static void pripareDateForTesting(CookBook cookBook, Order order) {
        try {
            cookBook.addDishObj(new Dish("Smazeny syr 100g", 7));
            cookBook.addDishObj(new Dish("Hranolky 100g", 2));
            cookBook.addDishObj(new Dish("Kureci rizek 150g", 8));
            cookBook.addDishObj(new Dish("Ryze 100g", 2));
            cookBook.addDishObj(new Dish("Tatarak 100g", 6.5));
            cookBook.addDishObj(new Dish("Topinka 1ks", 1.5));
            cookBook.addDishObj(new Dish("Pivo", 2));
        }catch (DishException e) {

            throw new RuntimeException();
        }
        order.addOrder(new Order(15, 8, 2));
        order.addOrder(new Order(15, 2, 2));
        order.addOrder(new Order(15, 11, 2));
        order.addOrder(new Order(2, 7, 6));
        order.addOrder(new Order(2, 3, 3));
        order.addOrder(new Order(2, 4, 3));

    }
    private static void loadDataFromExistFiles(OrderManager orderManager) {
        try{
            CookBook.loadDishesFromFile(Settings.fileDishesForLoad());
            orderManager.loadOrdersFromFile(Settings.fileOrdersForLoad());
        }catch (DishException e) {
            System.err.println("Chyba pri cteni ze souboru: "+ e.getLocalizedMessage());
        }

    }
     private static void saveDishesAndOrdersToFiles(CookBook cookBook, OrderManager orderManager) {
        try {
            cookBook.saveToFile(Settings.fileDishesForSave(), cookBook);
        }catch (DishException e) {
            throw new RuntimeException(e);
        }
        orderManager.saveOrdersToFile(Settings.fileOrdersForSave());
     }
     private static void printForRestaurantManagement(RestaurantManager restaurantManager) {
        int unfinishedOrders = restaurantManager.countUnfinichedOrderds();
         System.out.println("Pocet aktulne rozpracovanych objednavek : " + unfinishedOrders);
         List<Order> sortedOrders = restaurantManager.sortOrdersByOrderTime();
         for (Order order : sortedOrders) {
             System.out.println("Objednavka Id: " + order.getOrderId() + ", Cas: " + order.getOrderTime());
         }
        System.out.println("Prumerna doba vyrizeni objednavky: " +restaurantManager.avarageProcessTimeForOrders());
        restaurantManager.printTodayOrderedDishes();
        restaurantManager.printOrdersForTable(15);
     }

}
