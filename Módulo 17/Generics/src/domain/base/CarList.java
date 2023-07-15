package domain.base;

import domain.interfaces.ICarList;

import java.util.ArrayList;
import java.util.List;

public class CarList implements ICarList<Car> {
    List<Car> cars = new ArrayList<>();

    public CarList() {
    }

    @Override
    public List<Car> getCars() {
        return cars;
    }

    @Override
    public void addCar(Car car) {
        cars.add(car);
    }

    @Override
    public void removeCar(Car car) {
        cars.remove(car);
    }

    @Override
    public void updateCar(Car car) {
        cars.stream().filter(c -> c.getModel().equals(car.getModel())).forEach(c -> {
            c.setColor(car.getColor());
            c.setYear(car.getYear());
            c.setFuelType(car.getFuelType());
            c.setYear(car.getYear());
        });
    }

    @Override
    public void printCars() {
        cars.forEach(
                car -> System.out.printf("Model: %s, Color: %s, Year: %d, FuelType: %s%n",
                        car.getModel(),
                        car.getColor(),
                        car.getYear(),
                        car.getFuelType()
                )
        );
    }

    @Override
    public void startCar(Car car) {
        cars.stream().filter(c -> c.getModel().equals(car.getModel())).forEach(Car::start);
    }

    @Override
    public void stopCar(Car car) {
        cars.stream().filter(c -> c.getModel().equals(car.getModel())).forEach(Car::stop);
    }
}
