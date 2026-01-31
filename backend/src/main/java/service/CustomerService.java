package service;

import dto.CustomerRequest;
import dto.CustomerResponseDTO;
import entity.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import repository.CustomerRepository;


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

        String url = "https://viacep.com.br/ws/" + request.getCep() + "/json/";
        Map<String, Object> address = restTemplate.getForObject(url, Map.class);

        if (address == null || address.containsKey("erro")) {
            throw new RuntimeException("CEP não encontrado");
        }

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

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    private boolean isValidCpf(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;
        return true;
    }
}