package domain.factory;

import domain.entity.AudiCar;
import domain.entity.ICar;

public class AudiCarFactory implements ICarFactory {
    @Override
    public ICar createCar(String model, int year, String color) {
        return new AudiCar(model, year, color);
    }
}
