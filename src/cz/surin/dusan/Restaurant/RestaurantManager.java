package cz.surin.dusan.Restaurant;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class RestaurantManager {
    Order order = new Order();
    public void countUnfinichedOrderds() {
        int unfinishedOrders = 0;
        for (Order order : order.getOrders().values()) {
            if (!order.isPaid() && order.getFulfilmentTime() == null) {
                unfinishedOrders++;
            }
        }
        System.out.println("Pocet aktualne rozpracovanych objednavek : " + unfinishedOrders);
    }
    public void sortOrdersByOrderTime() {
        List<Order> orderList = new ArrayList<>(order.getOrders().values());
        Collections.sort(orderList, Comparator.comparing(Order::getOrderTime));
        for (Order order : orderList) {
            System.out.println("Objednavka Id: " + order.getOrderId() + ", Cas: " + order.getOrderTime());
        }
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
        LocalTime orderTime = order.getOrderTime();
        LocalTime fulfilmentTime = order.getFulfilmentTime();
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
            LocalDate orderDAte = LocalDate.now();
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
