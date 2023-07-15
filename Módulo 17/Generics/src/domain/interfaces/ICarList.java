package domain.interfaces;

import domain.base.Car;

import java.util.List;

public interface ICarList<T extends Car> {
    List<T> getCars();

    void addCar(T car);

    void removeCar(T car);

    void updateCar(T car);

    void printCars();

    void startCar(T car);

    void stopCar(T car);
}
