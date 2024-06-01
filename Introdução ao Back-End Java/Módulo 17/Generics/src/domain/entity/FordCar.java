package domain.entity;

import domain.base.Car;
import domain.enumns.FuelType;

public class FordCar extends Car {
    public FordCar(String model, String color, int year, FuelType fuelType) {
        super(model, color, year, fuelType);
    }
}
