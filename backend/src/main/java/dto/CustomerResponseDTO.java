package dto;

import entity.Customer;
import lombok.Data;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String cpf;
    private String cep;
    private String logradouro;
    private String cidade;

    public CustomerResponseDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.cpf = customer.getCpf();
        this.cep = customer.getCep();
        this.logradouro = customer.getLogradouro();
        this.cidade = customer.getCidade();
    }
}