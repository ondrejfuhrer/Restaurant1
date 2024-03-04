package cz.surin.dusan.Restaurant;

import exceptions.RestaurantException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderManager {
    Order order = new Order();
    public  void saveOrdersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for(Order order : order.getOrders().values()) {
                writer.write(order.getOrderId() + "\t" + order.getTableNumber() + "\t"
                + order.getDishId() + "\t" + order.getCountDish() + "\t" + order.getOrderTime() + "\t"
                + fulfilmentTimeString(order.getFulfilmentTime()) + "\t" + order.isPaid());
                writer.newLine();
            }
            System.out.println("Objednavka byla ulozena do souboru : "+ filename);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String fulfilmentTimeString(LocalDateTime localTime) {
        String fulfilmentTimeStr = localTime != null ?
                localTime.format(DateTimeFormatter.ofPattern("HH:mm")) : "null";
        return fulfilmentTimeStr;
    }

    public void loadOrdersFromFile(String filename) {
        Path file = Path.of(filename);
        try {
            if (Files.size(file) == 0) {
                System.out.println("Soubor " + filename + " je prazdny");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Order order = loadOrderFromLine(line);
                    if (order != null) {
                        order.addOrder(order);
                    } else {
                        throw new RestaurantException("Neplatny format radku v souboru " + filename);
                    }
                }
                System.out.println("Objednavka byla nactena ze souboru " + filename);
            } catch (IOException e) {
                System.err.println("Chyba pri cteni objednavky " + e.getLocalizedMessage());
            }
        } catch (RestaurantException | IOException e) {
            System.err.println("Chyba pri zjistovani velikosti souboru: " + e.getMessage());
        }
    }
    private Order loadOrderFromLine(String line) {
        String[] parts = line.split("\t");
        if (parts.length != 7) {
            return null;
        }
        int orderId = Integer.parseInt(parts[0].trim());
        int tableNumber = Integer.parseInt(parts[1].trim());
        int disId = Integer.parseInt(parts[2].trim());
        int countDish = Integer.parseInt(parts[3].trim());
        boolean isPaid = Boolean.parseBoolean(parts[6].trim());
        Order order = new Order(tableNumber, disId, countDish);
        order.setOrderId(orderId);
        order.setPaid(isPaid);
        return order;

    }
}
