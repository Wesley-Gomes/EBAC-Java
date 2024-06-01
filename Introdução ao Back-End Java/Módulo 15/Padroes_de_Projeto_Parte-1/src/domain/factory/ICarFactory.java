package domain.factory;

import domain.entity.ICar;

public interface ICarFactory {
    ICar createCar(String model, int year, String color);
}
