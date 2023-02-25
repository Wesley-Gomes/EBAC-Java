package br.com.wgomes;

import br.com.wgomes.classes.LegalPerson;
import br.com.wgomes.classes.Person;
import br.com.wgomes.classes.PhysicalPerson;

public class Main {
    public static void main(String[] args) {
        PhysicalPerson physicalPPerson = new PhysicalPerson("Teste1", "123.123.123-12");
        LegalPerson legalPerson = new LegalPerson("Teste2", "12.123.123/0001-12");
        print(physicalPPerson);
        print(legalPerson);
    }

    public static void print(Person person) {
        if (person instanceof LegalPerson legalPerson) {
            System.out.println("Pessoa Jurídica");
            System.out.println("Nome: " + legalPerson.getName());
            System.out.println("CNPJ: " + legalPerson.getCnpj());
            return;
        }

        System.out.println("Pessoa Física");
        PhysicalPerson physicalPerson = (PhysicalPerson) person;
        System.out.println("Nome: " + physicalPerson.getName());
        System.out.println("CPF: " + physicalPerson.getCpf());
    }
}
