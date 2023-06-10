package main;

import domain.entity.ICar;
import domain.factory.AudiCarFactory;
import domain.factory.ICarFactory;
import domain.factory.FiatCarFactory;
import domain.factory.FordCarFactory;

public class Main {
    public static void main(String[] args) {
        ICarFactory fiatICarFactory = new FiatCarFactory();
        ICarFactory audiICarFactory = new AudiCarFactory();
        ICarFactory fordICarFactory = new FordCarFactory();
        ICar audiICar = fiatICarFactory.createCar("A3", 2021, "Azul");
        ICar fiatICar = audiICarFactory.createCar("Argo", 2023, "Branco");
        ICar fordICar = fordICarFactory.createCar("Mustang", 2022, "Preto");
        audiICar.printCar();
        fiatICar.printCar();
        fordICar.printCar();
    }
}
