package domain.entity;

import domain.base.Car;
import domain.enumns.FuelType;

public class TeslaCar extends Car {
    public TeslaCar(String model, String color, int year, FuelType fuelType) {
        super(model, color, year, fuelType);
    }
}
