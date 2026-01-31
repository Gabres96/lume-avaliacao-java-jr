package dto;

import lombok.Data;

@Data
public class CustomerRequest {
    private String name;
    private String cpf;
    private String cep;
}