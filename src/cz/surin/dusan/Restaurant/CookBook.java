package cz.surin.dusan.Restaurant;

import exceptions.DishException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CookBook {

    private static Map<Integer, Dish> dishes;
    public CookBook() {dishes = new HashMap<>();}
    public static void addDish(int idDish, Dish newDish) {dishes.put(idDish, newDish);}

    public void addDishObj(Dish newDish) {
       int id = generateNewId();
       newDish.setIdDish(id);
       dishes.put(id, newDish);
    }
    public static void addDish(Dish newDish) {
       int id = generateNewId();
       newDish.setIdDish(id);
       dishes.put(id, newDish);
    }

    public static int generateNewId() {
        int maxId = dishes.keySet().stream().max(Integer::compare).orElse(0);
        return maxId + 1;
    }

    public static Dish getDishById(int dishId) {
        for (Dish dish : dishes.values()) {
            if (dish.getIdDish() == dishId) {
                return dish;
            }
        }
        return null;
    }

    public void editDish(int dishId, Dish updateDish) {
        if(dishes.containsKey(dishId)) {
            Dish dish = dishes.get(dishId);
            dish.setTitle(updateDish.getTitle());
            dish.setPrice(updateDish.getPrice());
            dish.setPreparationTime(updateDish.getPreparationTime());
            dish.setImage(updateDish.getImage());
        }
    }

    public void removeDish(int dishId) {dishes.remove(dishId);}
    public List<Dish> getDishes() {return new ArrayList<>(dishes.values());}
    public void printDishes() {
        for (Map.Entry<Integer, Dish> entry : dishes.entrySet()) {
            int dishId = entry.getKey();
            Dish dish = entry.getValue();
            System.out.println("Id: " + dishId);
            System.out.println(" Jidlo: " + dish.getTitle());
            System.out.println(" cena: " + dish.getTitle() + " $ ");
            System.out.println(" doba pripravy: " + dish.getPreparationTime() + " minut");
            System.out.println(" ");
        }
    }

    public void saveToFile(String filename, CookBook cookBook) throws DishException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Dish dish : cookBook.getDishes()) {
                writer.println(dish.getIdDish() + Settings.fileItemDelimeter() + dish.getTitle() + Settings.fileItemDelimeter()
                + dish.getPrice() + Settings.fileItemDelimeter() + dish.getPreparationTime());
            }
            System.out.println("Jidla byla ulozena do souboru " + filename);
        }catch (IOException e) {
            throw new DishException("Chyba pri zapisu do souboru " + filename + ": " + e.getLocalizedMessage());
        }
    }

    public static void loadDishesFromFile(String filename) throws DishException {
        try {
            if (Files.size(Paths.get(filename)) == 0) {
                throw new DishException("Soubor " + filename + " je prazdny ");
            }
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Dish dish = parseDishLine(line);
                    addDish(dish);
                }
            }
        }catch (FileNotFoundException e) {
            throw new DishException("Nepodarilo se nalezt soubor " + filename + ": " + e.getLocalizedMessage());
        }catch (IOException e) {
            throw new DishException("Chyba pri nacitani jidel ze souboru " + filename + ": " +e.getLocalizedMessage());
        }
    }

    public static Dish parseDishLine(String line) throws DishException {
        String[] blocks = line.split(Settings.fileItemDelimeter());
        int numOfBlocks = blocks.length;
        if(numOfBlocks != 4) {
            System.err.println("Nespravny pocet polozek na radku: " + line+ "! Pocet polozek: " + numOfBlocks + ".");
            return new Dish("kureci", 2);
        }
        int idDish = Integer.parseInt(blocks[0].trim());
        String title = blocks[1].trim();
        double price = Double.parseDouble(blocks[2].trim());
        int preparationTime = Integer.parseInt(blocks[3].trim());
        Dish newDish = new Dish(idDish, title, price, preparationTime);
        return newDish;
    }
}
