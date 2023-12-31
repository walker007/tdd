package com.academiadodesenvolvedor.tdd.services;

import com.academiadodesenvolvedor.tdd.exceptions.NotFoundException;
import com.academiadodesenvolvedor.tdd.models.Carro;
import com.academiadodesenvolvedor.tdd.repositories.CarroRepository;
import com.academiadodesenvolvedor.tdd.services.contratos.CarroServiceContrato;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CarroServiceTest {

    @SpyBean
    private CarroServiceContrato carroService;

    @MockBean
    private CarroRepository repository;

    @Test
    @DisplayName("Testa se a service de carro está salvando corretamente")
    public void salvarCarroTest(){

        Carro carro = this.criaCarro();
        Carro carroSaved = this.criaCarro();
        carroSaved.setId(1L);
        Mockito.when(this.repository.save(carro)).thenReturn(carroSaved);
        carro = carroService.salvar(carro);

        Assertions.assertNotNull(carro.getId());
        Assertions.assertNotEquals(0L, carro.getId());
    }

    @Test
    @DisplayName("Testa se a service de carro está listando corretamente")
    public void  listarCarrosTest(){
        Carro carroSaved = this.criaCarro();
        carroSaved.setId(1L);
        Mockito.when(this.repository.findAll()).thenReturn(Arrays.asList(carroSaved));
        List<Carro> listagem = carroService.listarCarros();

        Assertions.assertTrue(listagem.size() != 0);

        Carro carro = listagem.get(0);
        Assertions.assertNotNull(carro.getId());
        Assertions.assertNotEquals(0L, carro.getId());
    }

    @Test
    @DisplayName("Testa se a service de carro está listando por modelo")
    public void listaCarroPoModeloTest(){
        Mockito.when(this.repository.findAllByMarca("Volkswagen"))
                .thenReturn(Arrays.asList(this.criaCarro()));
        List<Carro> listagem = carroService.listarCarrosPorMarca("Volkswagen");

        Assertions.assertTrue(listagem.size() > 0);
    }

    @Test
    @DisplayName("Testa se a service de carro está buscando por Id")
    public void bucarCarroPorIdTest(){
        Optional<Carro> carroOptional = Optional.of(this.criaCarro());
        Mockito.when(this.repository.findById(1L)).thenReturn(carroOptional);

        Carro carro = carroService.buscarPorId(1L);

        Assertions.assertNotNull(carro);
        Assertions.assertNotEquals(0L, carro.getId());
    }

    @Test
    @DisplayName("Testa se a service de carro está atualizando")
    public void atualizaCarroTest(){
        Carro carroSaved = this.criaCarro();
        carroSaved.setId(1L);
        Mockito.when(this.repository.findById(1L))
                .thenReturn(Optional.of(carroSaved));

        Carro carro = carroService.buscarPorId(1L);
        carro.setCor("Preto");
        Mockito.when(this.repository.save(carro)).thenReturn(carro);
        carro = carroService.atualizarCarro(carro);

        Mockito.verify(this.repository, Mockito.times(1)).save(carro);
        Assertions.assertNotEquals("Vermelho",carro.getCor());
    }

    @Test
    @DisplayName("Testa se a service está apagando corretamente")
    public void apagaCarroTest(){
        Carro carroSaved = this.criaCarro();
        carroSaved.setId(1L);
        Mockito.when(this.repository.findById(1L))
                .thenReturn(Optional.of(carroSaved));

        carroService.apagarCarro(1L);

        Mockito.when(this.repository.findById(1L)).thenReturn( Optional.empty()  );

      Assertions.assertThrows(NotFoundException.class,() ->{
          carroService.buscarPorId(1L);
      });

    }

    @Test
    @DisplayName("Testa se a sobrecarga do Listar Carro Está funcionando corretamente")
    public void listarCarrosPaginadoTest(){

        Pageable page = PageRequest.of(0,15);
        Mockito.when(this.repository.findAll(page))
                .thenReturn(new PageImpl<>(Arrays.asList(this.criaCarro())));

        Page<Carro> carrosPage = carroService.listarCarros(page);

        Assertions.assertTrue(carrosPage.hasContent());
    }
    @Test
    @DisplayName("Testa se a busca por id lança exceção")
    public void deveLancarExecao(){

        Assertions.assertThrows(NotFoundException.class, () ->{
            this.carroService.buscarPorId(0L);
        });
    }
    @BeforeEach
    public void cadastraCarro(){
        Carro carro = this.criaCarro();

       carroService.salvar(carro);
    }

    private Carro criaCarro(){
        return new Carro("Volkswagen", "Gol", 2010,"Flex","Vermelho");
    }
}
