package com.group6.revature.repository;

import com.group6.revature.model.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface partsRepo  extends JpaRepository<Parts, Integer>{
    List<Parts> findByPartName(String partName);

    @Query("select inventory from parts where part_id = :id")
    int findInventory(int id);

    @Transactional
    @Modifying
    @Query("update parts set inventory = :amount where part_id = :id")
    void updateInventory(int id, int amount);

    @Query("select price from parts where part_id = :id")
    int getPrice(int id);


    //ask about what data type amount should be
    @Transactional
    @Modifying
    @Query("update parts set price = :amount where part_id = :id")
    void updatePrice(int id, double amount);

    //double check if i am doing the get model for part right, check service
    @Query("select make_model_id from parts where part_id = :id")
    int getMakeModel(int id);
}
