package cz.surin.dusan.Restaurant;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class RestaurantManager {
    Order order = new Order();
    public int countUnfinichedOrderds() {
        int unfinishedOrders = 0;
        for (Order order : order.getOrders().values()) {
            if (!order.isPaid() && order.getFulfilmentTime() == null) {
                unfinishedOrders++;
            }
        }
        return unfinishedOrders;
    }
    public List<Order> sortOrdersByOrderTime() {
        List<Order> orderList = new ArrayList<>(order.getOrders().values());
        Collections.sort(orderList, Comparator.comparing(Order::getOrderTime));
        return orderList;
    }
    public double avarageProcessTimeForOrders() {
        int totalProcessOrders = 0;
        Duration totalProcessingTime = Duration.ZERO;
        for (Order order : order.getOrders().values()) {
            Duration processingTime = getOrderProcessingTime(order);
            if (!processingTime.isZero()) {
                totalProcessOrders++;
                totalProcessingTime = totalProcessingTime.plus(processingTime);
            }
        }
        if (totalProcessOrders == 0) {
            return 0;
        }
        double avarageTimeInSeconds = totalProcessingTime.getSeconds() / (double) totalProcessOrders;
        return avarageTimeInSeconds;
    }
    public Duration getOrderProcessingTime(Order order) {
        LocalDateTime orderTime = order.getOrderTime();
        LocalDateTime fulfilmentTime = order.getFulfilmentTime();
        if (fulfilmentTime != null) {
            return Duration.between (orderTime, fulfilmentTime);
        }else {
            return Duration.ZERO;
        }
    }

    public void printTodayOrderedDishes() {
        LocalDate today = LocalDate.now();
        System.out.println("Dnesni objednana jidla:");
        Set<Integer> todayDishes = new HashSet<>();
        for (Order order : order.getOrders().values()) {
            LocalDate orderDAte = order.getOrderTime().toLocalDate();
            if (orderDAte.equals(today)) {
                int dishId = order.getDishId();
                if(!todayDishes.contains(dishId)) {
                    System.out.println(CookBook.getDishById(dishId));
                    todayDishes.add(dishId);
                }
            }
        }
    }
    public void printOrdersForTable (int tableNumber) {
        order.setNextBillId();
        List<Order> orders = order.getOrdersForTable(tableNumber);
        if (orders.isEmpty()) {
            System.out.println("Stul " + tableNumber + " nema zadnou objednavku.");
        }else {
            System.out.println("**Objednavka pro stul c." + tableNumber + " **");
            System.out.println("*****");
            for(Order order : orders) {
                System.out.println(order.getDescription());
            }
            System.out.println("*****");
        }
    }
}
