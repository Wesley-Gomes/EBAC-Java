package main;

import creational.abstract_factory.AudiCarFactory;
import creational.abstract_factory.CarFactory;
import creational.abstract_factory.FiatCarFactory;

public class Main {
    public static void main(String[] args) {
        CarFactory fiatCarFactory = new FiatCarFactory("Argo", 2023, "Branco");
        CarFactory AudiCarFactory = new AudiCarFactory("A3", 2021, "Azul");
        generateCar(fiatCarFactory);
        generateCar(AudiCarFactory);
    }

    public static void generateCar(CarFactory carFactory) {
        Car car = carFactory.createCar();
        car.printCar();
    }
}
