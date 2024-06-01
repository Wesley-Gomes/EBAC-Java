package domain.base;

import domain.enumns.FuelType;

public abstract class Car {
    private final String model;
    private String color;
    private int year;
    private FuelType fuelType;

    public Car(String model, String color, int year, FuelType fuelType) {
        this.model = model;
        this.color = color;
        this.year = year;
        this.fuelType = fuelType;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void start() {
        System.out.println(this.getClass().getSimpleName() + " started!");
    }

    public void stop() {
        System.out.println(this.getClass().getSimpleName() + " stopped!");
    }
}
