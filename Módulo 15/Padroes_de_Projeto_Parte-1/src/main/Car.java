package main;

public class Car {
    private String model;
    private Number year;
    private String color;
    private String brand;

    public Car(String brand, String model, Number year, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Number getYear() {
        return year;
    }

    public void setYear(Number year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void printCar () {
        System.out.println("brand = " + brand + ", model = " + model + ", year = " + year + ", color = " + color);
    }
}
