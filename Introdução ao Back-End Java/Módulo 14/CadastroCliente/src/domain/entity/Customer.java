package domain.entity;

import java.util.Objects;
import java.util.stream.IntStream;

public class Customer {
    private String name;
    private Long cpf;
    private Long phone;
    private String addr;
    private Integer addrNumber;
    private String city;
    private String state;

    public Customer(String name, Long cpf, Long phone, String addr, Integer addrNumber, String city, String state) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.addr = addr;
        this.addrNumber = addrNumber;
        this.city = city;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getAddrNumber() {
        return addrNumber;
    }

    public void setAddrNumber(Integer addrNumber) {
        this.addrNumber = addrNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(getName(), customer.getName()) && getCpf().equals(customer.getCpf()) && Objects.equals(getPhone(), customer.getPhone()) && Objects.equals(getAddr(), customer.getAddr()) && Objects.equals(getAddrNumber(), customer.getAddrNumber()) && Objects.equals(getCity(), customer.getCity()) && Objects.equals(getState(), customer.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCpf(), getPhone(), getAddr(), getAddrNumber(), getCity(), getState());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cpf=" + cpf +
                '}';
    }

    public void validateCpf() throws Exception{
        if (!isValidCpf()) throw new Exception("CPF inválido!");
    }
    
    private boolean isValidCpf() {
        String formatedCpf = this.cpf.toString();        
        if (formatedCpf.isEmpty() || formatedCpf.length() != 11) return false;        
        int[] digits = formatedCpf.chars().map(Character::getNumericValue).toArray();
        int calculatedDigit1 = calculateVerificationDigit(digits, 9);
        int calculatedDigit2 = calculateVerificationDigit(digits, 10);        
        return digits[9] == calculatedDigit1 && digits[10] == calculatedDigit2;
    }

    private int calculateVerificationDigit(int[] digits, int index) {
        int sum = IntStream.range(0, index).map(i -> digits[i] * (index + 1 - i)).sum();
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }    
}
