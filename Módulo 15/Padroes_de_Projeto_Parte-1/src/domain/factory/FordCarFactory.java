package domain.factory;

import domain.entity.ICar;
import domain.entity.FordCar;

public class FordCarFactory implements ICarFactory {
    @Override
    public ICar createCar(String model, int year, String color) {
        return new FordCar(model, year, color);
    }
}
