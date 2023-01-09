import java.util.Date;

/**
 * @author Wesley Gomes
 * @version 1.0
 *
 */

public class Car {
    private String plate;
    private String model;
    private Number year;
    private Number mileage;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Number getYear() {
        return year;
    }

    public void setYear(Number year) {
        this.year = year;
    }

    public Number getMileage() {
        return mileage;
    }

    public void setMileage(Number mileage) {
        this.mileage = mileage;
    }

    /**
     * This method can be used to assign value to all properties of the car class
     */
    public void newCar(String plate, String model, Number year, Number mileage){
        this.plate = plate;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }
}
