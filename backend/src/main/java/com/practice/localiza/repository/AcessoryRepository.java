package com.practice.localiza.repository;
import java.util.List;
import com.practice.localiza.entity.Acessory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcessoryRepository extends JpaRepository<Acessory, Long> {

    public List<Acessory> findByNameContainingIgnoreCase(String name);

}

