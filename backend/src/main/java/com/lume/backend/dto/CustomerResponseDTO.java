package com.lume.backend.dto;

import com.lume.backend.entity.Customer;
import lombok.Data;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String cpf;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;

    public CustomerResponseDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.cpf = customer.getCpf();
        this.cep = customer.getCep();
        this.logradouro = customer.getLogradouro();
        this.bairro = customer.getBairro();
        this.cidade = customer.getCidade();
        this.estado = customer.getEstado();
    }
}