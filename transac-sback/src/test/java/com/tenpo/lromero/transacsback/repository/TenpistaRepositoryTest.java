package com.tenpo.lromero.transacsback.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tenpo.lromero.transacsback.entity.Tenpista;

@DataJpaTest
class TenpistaRepositoryTest {

    @Autowired
    private TenpistaRepository tenpistaRepository;

    @Test
    @DisplayName("Save a Tenpista")
    void shouldSaveTenpista() {
        Tenpista tenpista = new Tenpista();
        tenpista.setName("Luis Romero"); // ajusta al nombre real de tu atributo

        Tenpista saved = tenpistaRepository.save(tenpista);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @DisplayName("Find a Tenpista by ID")
    void shouldFindTenpistaById() {
        Tenpista tenpista = new Tenpista();
        tenpista.setName("Luis Romero");

        Tenpista saved = tenpistaRepository.save(tenpista);

        Optional<Tenpista> found = tenpistaRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Luis Romero");
    }
}
