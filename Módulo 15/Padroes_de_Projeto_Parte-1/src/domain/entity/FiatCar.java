package domain.entity;

public class FiatCar implements ICar {
    private final String brand;
    private final String model;
    private final Number year;
    private final String color;

    public FiatCar(String model, Number year, String color) {
        this.brand = "Fiat";
        this.model = model;
        this.year = year;
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Number getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }
}
