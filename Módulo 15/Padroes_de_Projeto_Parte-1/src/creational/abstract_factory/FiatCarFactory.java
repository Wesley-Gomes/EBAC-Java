package creational.abstract_factory;

import main.Car;

public class FiatCarFactory implements CarFactory{
    private final String model;
    private final int year;
    private final String color;

    public FiatCarFactory(String model, int year, String color) {
        this.model = model;
        this.year = year;
        this.color = color;
    }
    @Override
    public Car createCar() {
        return new Car("Fiat",this.model, this.year, this.color);
    }
}
