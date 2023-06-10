package domain.factory;

import domain.entity.ICar;
import domain.entity.FiatCar;

public class FiatCarFactory implements ICarFactory {
    @Override
    public ICar createCar(String model, int year, String color) {
        return new FiatCar(model, year, color);
    }
}
