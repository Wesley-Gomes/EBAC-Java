package br.com.wgomes.services;

import br.com.wgomes.domain.dao.ICustomerDAO;
import br.com.wgomes.domain.entity.CustomerEntity;
import br.com.wgomes.domain.mapper.ICustomerMapper;
import br.com.wgomes.domain.model.Customer;
import br.com.wgomes.exceptions.AlreadyExistException;
import br.com.wgomes.exceptions.CannotChangedException;
import br.com.wgomes.exceptions.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private final ICustomerDAO customerDAO;
    private final ICustomerMapper customerMapper;

    @Override
    public Customer findByCpf(String cpf) throws Exception {
        CustomerEntity customerEntity = customerDAO.findByCpf(cpf).orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerMapper.mapToModel(customerEntity);
    }

    @Override
    public Customer save(Customer entity) throws Exception {
        Optional<CustomerEntity> optionalCustomerEntity = customerDAO.findByCpf(entity.getCpf().value());
        if (optionalCustomerEntity.isPresent()) throw new AlreadyExistException("Customer already exists");
        CustomerEntity customerEntity = customerMapper.mapToEntity(entity);
        UUID customerId = UUID.randomUUID();
        customerEntity.setCustomerId(customerId);
        customerDAO.save(customerEntity);
        return customerMapper.mapToModel(customerEntity);
    }

    @Override
    public Customer update(Customer entity) throws Exception {
        Optional<CustomerEntity> optionalCustomerEntity = customerDAO.findById(entity.getCustomerId());
        if (optionalCustomerEntity.isEmpty()) throw new NotFoundException("Customer not found");
        if (!optionalCustomerEntity.get().getCpf().equals(entity.getCpf().value()))
            throw new CannotChangedException("CPF cannot be changed");
        CustomerEntity customerEntity = customerMapper.mapToEntity(entity);
        customerDAO.update(customerEntity);
        return customerMapper.mapToModel(customerEntity);
    }

    @Override
    public void delete(UUID id) throws Exception {
        if (!customerDAO.existsById(id)) throw new NotFoundException("Customer not found");
        customerDAO.delete(id);
    }

    @Override
    public Optional<Customer> findById(UUID id) throws Exception {
        return customerDAO.findById(id).map(customerMapper::mapToModel);
    }

    @Override
    public List<Customer> findAll() throws Exception {
        return customerMapper.mapToModelList(customerDAO.findAll());
    }
}
