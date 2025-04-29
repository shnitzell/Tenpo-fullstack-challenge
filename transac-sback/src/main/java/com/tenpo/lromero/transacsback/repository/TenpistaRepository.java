package com.tenpo.lromero.transacsback.repository;

import com.tenpo.lromero.transacsback.entity.Tenpista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TenpistaRepository extends JpaRepository<Tenpista, Integer> {
    Optional<Tenpista> findByName(String name);
}