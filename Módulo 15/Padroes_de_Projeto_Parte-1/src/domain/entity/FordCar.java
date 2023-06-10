package domain.entity;

public class FordCar implements ICar {
    private final String brand;
    private final String model;
    private final Number year;
    private final String color;

    public FordCar(String model, Number year, String color) {
        this.brand = "Ford";
        this.model = model;
        this.year = year;
        this.color = color;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public Number getYear() {
        return year;
    }

    @Override
    public String getColor() {
        return color;
    }
}
