package cz.surin.dusan.Restaurant;

public class Settings {
    private static final String fileItemDelimeterValue = "\t";
    private static final String fileForDishes = "listOfDishes.txt";
    private static final String fileForOrders = "orders.txt";
    public static String fileItemDelimeter() {return fileItemDelimeterValue;
    }
    public static String fileDishesForLoad() {return fileForDishes;}
    public static String fileDishesForSave(){return fileForDishes;}
    public static String fileOrdersForLoad() {return fileForOrders;}
    public static String fileOrdersForSave(){return fileForOrders;}
}
