
package cz.surin.dusan.Restaurant;

import exceptions.DishException;

public class Dish {
    private static int nextId = 1;
    private int idDish;
    private String title;
    private double price;
    private int preparationTime;
    private String image;

    public Dish(String title, double price, int preparationTime, String image) throws DishException {
        this.idDish = nextId++;
        this.title = title;
        this.price = price;
        checkPreparationTime(preparationTime);
        this.preparationTime = preparationTime;
        this.image = image;
    }

    public Dish(int idDish,String title, double price, int preparationTime) throws DishException{
        this.idDish = idDish;
        this.title = title;
        this.price = price;
        checkPreparationTime(preparationTime);
        this.preparationTime = preparationTime;
        this.image = "blank";
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    private void checkPreparationTime(int preparationTime) throws DishException {
        if(preparationTime <= 0){
            throw new DishException("Cas na pripravu pokrmu nesmi byt zaporne cislo: " + preparationTime);
        }
    }

    @Override
    public String toString() {
        return title + ": " + price +" Kc " +
                preparationTime + " minut " +
                image;
    }

}
