package cz.surin.dusan.Restaurant;

import exceptions.DishException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Order {
    private int orderId;
    private int tableNumber;
    private Dish dish;
    private LocalDateTime orderTime;
    private LocalDateTime fulfilmentTime;
    private boolean isPaid;
    private int countDish = 1;
    private static int nextBillId = 1;
    private static int nextOrderId= 1;
    private static Map<Integer, Order> orders = new HashMap<>();

    public Order(int tableNumber, int dishId, int countDish) {
        this.orderId = nextOrderId++;
        this.tableNumber = tableNumber;
        this.dish = CookBook.getDishById(dishId);
        this.orderTime = LocalDateTime.now();
        this.isPaid = false;
        this.countDish = countDish;
        addOrder(this);
    }
    public Order() {}

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public void addOrder(Order order) {
        orders.put(order.getOrderId(), order);
    }
    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public int getTableNumber() {
        return tableNumber;
    }



    public Dish getDish() {
        return dish;
    }
    public int getDishId(){return dish.getIdDish();
    }


    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(String time) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
    public void markAsPaid() {
        this.isPaid = true;
    }

    public int getCountDish() {
        return countDish;
    }

    public void setCountDish(int countDish) {
        this.countDish = countDish;
    }

    public static int getNextBillId() {
        return nextBillId++;
    }

    public void setNextBillId() {
        this.nextBillId = 1;
    }

    public static int getNextOrderId() {
        return nextOrderId;
    }

    public static void setNextOrderId(int nextOrderId) {
        Order.nextOrderId = nextOrderId;
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrdersForTable(int tableNumber) {
        return orders.values()
                .stream()
                .filter(order -> order.getTableNumber() == tableNumber)
                .collect(Collectors.toList());

    }

    public String getDescription() {
        StringBuilder description = new StringBuilder();
        description.append(Order.getNextBillId()).append(".").append(" ").append(dish.getTitle())
                .append(" ").append(countDish).append(" ").append("(")
                .append(totalDishPrice()).append(" $").append(")").append(":").append("\t")
                .append(orderTime.format(DateTimeFormatter.ofPattern("HH:mm"))).append("-");
        if (fulfilmentTime != null) {
            description.append(fulfilmentTime.format(DateTimeFormatter.ofPattern("HH:mm"))).append("\t");
        }
        description.append(isPaid ? "Zaplaceno" : "");
        return description.toString();
    }

    public void printTotalDishPriceForTable(int tableNumber) {
        List<Order> orders = getOrdersForTable(tableNumber);
        double total = 0.0;
        for(Order order : orders) {
            total += order.totalDishPrice();
        }
        System.out.println("Celkova cena konzumace pro stul cislo "
        +tableNumber + " je :" +Math.round(total * 100.0) / 100.0 + " $");
    }

    public double totalDishPrice() {
        double price = countDish * dish.getPrice();
        return Math.round(price * 100.0) / 100.0;
    }

    public static String formatTableNumber(int tableNumber) {
        if (tableNumber >= 1 && tableNumber <= 9) {
            return " " + tableNumber;
        }
        else {
            return String.valueOf(tableNumber);
        }
    }

    public void setPaidForOrder(int orderId) throws DishException {
        Order order = getOrderById(orderId);
        try {
            if (order != null) {
                if ( order.getFulfilmentTime() != null) {
                    order.markAsPaid();
                } else {
                    throw new DishException("Objednavka s Id " + orderId + " nebyla vybavena, a proto nemuze byt oznacena jako zaplacena.");
                }
            } else {
                throw new DishException("Objednavka s Id " + orderId + " neexistuje.");
            }
        }catch (Exception e) {
            throw new DishException("Chyba pri placeni objednavky: " + e.getMessage());
        }
    }

    public void setFulfilmentTimeForOrder(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.settingsFulfilmentTime();
        }
    }
    public Order getOrderById(int orderId) {
        return orders.values()
                .stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }

    public void settingsFulfilmentTime() {
        if (orderTime != null && dish != null) {
            int preparationTimeMinutes = dish.getPreparationTime();
            if (preparationTimeMinutes > 0) {
                fulfilmentTime  = orderTime.plusMinutes(preparationTimeMinutes);
            }
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", tableNumber=" + tableNumber +
                ", dish=" + dish +
                ", orderTime=" + orderTime +
                ", fulfilmentTime=" + fulfilmentTime +
                ", isPaid=" + isPaid +
                ", countDish=" + countDish +
                '}';
    }
}
