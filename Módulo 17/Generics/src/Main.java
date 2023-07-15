import domain.entity.AudiCar;
import domain.entity.TeslaCar;
import domain.base.Car;
import domain.base.CarList;
import domain.entity.FordCar;
import domain.enumns.FuelType;
import domain.interfaces.ICarList;

public class Main {
    public static void main(String[] args) {
        ICarList<Car> carList = new CarList();
        AudiCar audiCar = new AudiCar("A4", "Branco", 2020, FuelType.GASOLINE);
        FordCar fordCar = new FordCar("Fiesta", "Preto", 2019, FuelType.GASOLINE);
        TeslaCar teslaCar = new TeslaCar("Model 3", "Vermelho", 2023, FuelType.ELECTRIC);

        carList.addCar(audiCar);
        carList.addCar(fordCar);
        carList.addCar(teslaCar);

        carList.startCar(audiCar);
        carList.stopCar(audiCar);

        carList.startCar(fordCar);
        carList.stopCar(fordCar);

        carList.startCar(teslaCar);
        carList.stopCar(teslaCar);

        audiCar.setColor("Azul");
        audiCar.setYear(2021);

        carList.updateCar(audiCar);
        carList.removeCar(fordCar);

        carList.printCars();
    }
}