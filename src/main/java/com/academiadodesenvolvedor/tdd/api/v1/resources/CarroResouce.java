package com.academiadodesenvolvedor.tdd.api.v1.resources;

import com.academiadodesenvolvedor.tdd.api.dtos.CarroDTO;
import com.academiadodesenvolvedor.tdd.api.forms.CreateCarroForm;
import com.academiadodesenvolvedor.tdd.models.Carro;
import com.academiadodesenvolvedor.tdd.services.contratos.CarroServiceContrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroResouce {

    @Autowired
    private CarroServiceContrato service;

    @PostMapping
    public ResponseEntity<CarroDTO> cadastraCarro(@RequestBody CreateCarroForm form){
        Carro carro =  this.service.salvar(form.convert());

        return new ResponseEntity<>(carro.toDTO(), HttpStatus.CREATED);
    }
}
