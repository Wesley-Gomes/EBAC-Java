package br.com.wgomes.classes;

public class PhysicalPerson extends Person {
    private String cpf;

    public PhysicalPerson(String name, String cpf) {
        super(name);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
