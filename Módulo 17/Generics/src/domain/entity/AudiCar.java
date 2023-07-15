package domain.entity;

import domain.base.Car;
import domain.enumns.FuelType;

public class AudiCar extends Car {
    public AudiCar(String model, String color, int year, FuelType fuelType) {
        super(model, color, year, fuelType);
    }
}
