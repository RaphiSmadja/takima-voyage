package com.takima.demo.repository;

import com.takima.demo.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {

    List<Voyage> findByDestination(String destination);
}
