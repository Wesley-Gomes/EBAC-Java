package domain.entity;

public interface ICar {
    String getBrand();

    String getModel();

    Number getYear();

    String getColor();

    default void printCar() {
        System.out.println("brand = " + getBrand() + ", model = " + getModel() + ", year = " + getYear() + ", color = " + getColor());
    }
}
