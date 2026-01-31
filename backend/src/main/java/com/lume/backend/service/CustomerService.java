package com.lume.backend.service;

import com.lume.backend.dto.CustomerRequest;
import com.lume.backend.dto.CustomerResponseDTO;
import com.lume.backend.entity.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lume.backend.repository.CustomerRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public CustomerService(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    public CustomerResponseDTO createCustomer(CustomerRequest request) {
        if (!isValidCpf(request.getCpf())) {
            throw new RuntimeException("CPF Inválido");
        }

        Map<String, Object> address = fetchAddressByCep(request.getCep());

        Customer customer = Customer.builder()
                .name(request.getName())
                .cpf(request.getCpf())
                .cep(request.getCep())
                .logradouro((String) address.get("logradouro"))
                .cidade((String) address.get("localidade"))
                .build();

        return new CustomerResponseDTO(customerRepository.save(customer));
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CustomerResponseDTO findById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public CustomerResponseDTO update(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

        if (!isValidCpf(request.getCpf())) {
            throw new RuntimeException("CPF Inválido");
        }

        if (!customer.getCep().equals(request.getCep())) {
            Map<String, Object> address = fetchAddressByCep(request.getCep());
            customer.setCep(request.getCep());
            customer.setLogradouro((String) address.get("logradouro"));
            customer.setCidade((String) address.get("localidade"));
        }

        customer.setName(request.getName());
        customer.setCpf(request.getCpf());

        return new CustomerResponseDTO(customerRepository.save(customer));
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    private Map<String, Object> fetchAddressByCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        Map<String, Object> address = restTemplate.getForObject(url, Map.class);

        if (address == null || address.containsKey("erro")) {
            throw new RuntimeException("CEP não encontrado");
        }
        return address;
    }

    private boolean isValidCpf(String cpf) {
        return com.lume.backend.util.CpfValidator.isValid(cpf);
    }
}