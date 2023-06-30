package com.academiadodesenvolvedor.tdd.api.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCarroForm {
    private String marca;
    private String modelo;
    private int ano;
    private String combustivel;
    private String cor;
}
