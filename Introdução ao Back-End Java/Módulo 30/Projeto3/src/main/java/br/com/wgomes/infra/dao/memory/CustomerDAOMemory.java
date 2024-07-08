package br.com.wgomes.infra.dao.memory;

import br.com.wgomes.domain.dao.ICustomerDAO;
import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerDAOMemory implements ICustomerDAO {
    private final List<CustomerEntity> customers = new ArrayList<>();

    @Override
    public Optional<CustomerEntity> findByCpf(String cpf) {
        return customers.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst();
    }

    @Override
    public void save(CustomerEntity entity) {
        customers.add(entity);
    }

    @Override
    public void update(CustomerEntity entity) throws Exception {
        if (!existsById(entity.getCustomerId())) throw new NotFoundException("Customer not found");
        customers.stream()
                .filter(c -> c.getCustomerId().equals(entity.getCustomerId()))
                .findFirst()
                .ifPresent(c -> c.setName(entity.getName()));
    }

    @Override
    public void delete(UUID id) throws Exception {
        if (!existsById(id)) throw new NotFoundException("Customer not found");
        customers.removeIf(c -> c.getCustomerId().equals(id));
    }

    @Override
    public boolean existsById(UUID id) {
        return customers.stream().anyMatch(c -> c.getCustomerId().equals(id));
    }

    @Override
    public Optional<CustomerEntity> findById(UUID id) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(id))
                .findFirst();
    }

    @Override
    public List<CustomerEntity> findAll() {
        return customers;
    }
}
