package com.group6.revature.repository;

import com.group6.revature.model.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface partsRepo  extends JpaRepository<Parts, Integer>{
    List<Parts> findByPartName(String partName);

    @Query("select inventory from parts where part_id = :id")
    int findInventory(int id);
}
