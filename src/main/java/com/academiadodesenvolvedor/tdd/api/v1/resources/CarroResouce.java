package com.academiadodesenvolvedor.tdd.api.v1.resources;

import com.academiadodesenvolvedor.tdd.api.dtos.CarroDTO;
import com.academiadodesenvolvedor.tdd.api.forms.CreateCarroForm;
import com.academiadodesenvolvedor.tdd.api.forms.UpdateCarroForm;
import com.academiadodesenvolvedor.tdd.models.Carro;
import com.academiadodesenvolvedor.tdd.services.contratos.CarroServiceContrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<Page<CarroDTO>> listaCarros(Pageable page){
        Page<Carro> carroPage = this.service.listarCarros(page);
        Page<CarroDTO> carroDTOS = carroPage.map(Carro::toDTO);

       return new ResponseEntity<>(carroDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> updateCarro(@PathVariable Long id, @RequestBody UpdateCarroForm form){
        Carro carro = this.service.buscarPorId(id);
        carro = form.update(carro);
        carro = this.service.atualizarCarro(carro);

        return  new ResponseEntity<>(carro.toDTO(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> listaCarroPorId(@PathVariable Long id)
    {
        Carro carro = this.service.buscarPorId(id);

        return new ResponseEntity<>(carro.toDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagaCarro(@PathVariable Long id){
        this.service.apagarCarro(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
