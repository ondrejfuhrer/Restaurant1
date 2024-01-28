import cz.surin.dusan.Restaurant.Dish;
import exceptions.DishException;

public class Main {
    public static void main(String[] args) {
        Dish kureci = null;
        try {
            kureci = new Dish("Kureci prsa", 150, 35, "url-kureci prsa" );
        } catch (DishException e) {
            throw new RuntimeException(e);
        }
        System.out.println(kureci);
    }
}