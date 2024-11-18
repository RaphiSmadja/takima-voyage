package com.takima.demo.repository;

import com.takima.demo.model.Voyageur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoyageurRepository extends JpaRepository<Voyageur, Long> {
}
