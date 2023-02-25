package br.com.wgomes.classes;

public class LegalPerson extends Person {
    private String cnpj;

    public LegalPerson(String name, String cnpj) {
        super(name);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
