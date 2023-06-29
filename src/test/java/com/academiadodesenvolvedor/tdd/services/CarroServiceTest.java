package com.academiadodesenvolvedor.tdd.services;

import com.academiadodesenvolvedor.tdd.models.Carro;
import com.academiadodesenvolvedor.tdd.services.contratos.CarroServiceContrato;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CarroServiceTest {

    @Autowired
    private CarroServiceContrato carroService;

    @Test
    @DisplayName("Testa se a service de carro está salvando corretamente")
    public void salvarCarroTest(){
        Carro carro = new Carro("Volkswagen", "Gol", 2010,"Flex","Vermelho");

        carro = carroService.salvar(carro);

        Assertions.assertNotNull(carro.getId());
        Assertions.assertNotEquals(0L, carro.getId());
    }

    @Test
    @DisplayName("Testa se a service de carro está listando corretamente")
    public void  listarCarrosTest(){
        List<Carro> listagem = carroService.listarCarros();

        Assertions.assertTrue(listagem.size() != 0);

        Carro carro = listagem.get(0);
        Assertions.assertNotNull(carro.getId());
        Assertions.assertNotEquals(0L, carro.getId());
    }

    @Test
    @DisplayName("Testa se a service de carro está listando por modelo")
    public void listaCarroPoModeloTest(){
        List<Carro> listagem = carroService.listarCarrosPorMarca("Volkswagen");

        Assertions.assertTrue(listagem.size() > 0);
    }

    @Test
    @DisplayName("Testa se a service de carro está buscando por Id")
    public void bucarCarroPorIdTest(){
        Carro carro = carroService.bucarPorId(1L);

        Assertions.assertNotNull(carro);
        Assertions.assertNotEquals(0L, carro.getId());
    }

    @Test
    @DisplayName("Testa se a service de carro está atualizando")
    public void atualizaCarroTest(){
        Carro carro = carroService.bucarPorId(1L);

        carro.setCor("Preto");

        carro = carroService.atualizarCarro(carro);

        Assertions.assertNotEquals("Vermelho",carro.getCor());
    }

    @BeforeEach
    public void cadastraCarro(){
        Carro carro = new Carro("Volkswagen", "Gol", 2010,"Flex","Vermelho");

       carroService.salvar(carro);
    }
}
