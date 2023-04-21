package infra.repository.memory;

import domain.entity.Customer;
import domain.repository.ICustomerRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerRepositoryMemoryDAO implements ICustomerRepository {

    private final Map<Long, Customer> customers;

    public CustomerRepositoryMemoryDAO() {
        this.customers = new HashMap<>();
    }

    @Override
    public void save(Customer customer) throws Exception {
        customer.validateCpf();
        validateInsert(customer.getCpf());
        this.customers.put(customer.getCpf(), customer);
    }

    @Override
    public void delete(Long cpf) throws Exception{                
        validateDelete(cpf);
        this.customers.remove(cpf);
    }

    @Override
    public void update(Customer customer) throws Exception {
        Long cpf = customer.getCpf();
        validateUpdate(cpf);
        Customer customerRegistred = this.customers.get(cpf);
        customerRegistred.setName(customer.getName());
        customerRegistred.setPhone(customer.getPhone());
        customerRegistred.setAddr(customer.getAddr());
        customerRegistred.setAddrNumber(customer.getAddrNumber());
        customerRegistred.setCity(customer.getCity());
        customerRegistred.setState(customer.getState());
    }

    @Override
    public Boolean checkExists(Long cpf) {
        return this.customers.containsKey(cpf);
    }

    @Override
    public Collection<Customer> getAll() {        
        return this.customers.values();
    }

    @Override
    public Customer getByCpf(Long cpf) {
        return this.customers.get(cpf);
    }

    private void validateInsert(Long cpf) throws Exception {
        if (checkExists(cpf)) throw new Exception("Cliente Já Cadastrado!");
    }
    
    private void validateDelete(Long cpf) throws Exception {
        if (!checkExists(cpf)) throw new Exception("O Cliente não foi encontrado!");
    }
    
    private void validateUpdate(Long cpf) throws Exception {
        if (!checkExists(cpf)) throw new Exception("O Cliente não foi encontrado!");
    }
}
