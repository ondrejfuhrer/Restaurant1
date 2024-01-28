
package cz.surin.dusan.order;

public class Dish {
    private static int nextId = 1;
    private int idDish;
    private String title;
    private double price;
    private int preparationTime;
    private String image;

    public Dish(String title, double price, int preparationTime, String image) {
        this.idDish = nextId++;
        this.title = title;
        this.price = price;
        this.preparationTime = preparationTime;
        this.image = image;
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

    @Override
    public String toString() {
        return title + ": " + price +" Kc " +
                preparationTime + " minut " +
                image;
    }

}
