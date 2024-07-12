package com.group6.revature.repository;

import com.group6.revature.model.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface partsRepo  extends JpaRepository<Parts, Integer>{
//    List<Parts> findByPartName(String part_name);

    @Query("select p.inventory from Parts p where p.part_id = :id")
    int findInventory(int id);

    @Transactional
    @Modifying
    @Query("update Parts p set p.inventory = :amount where p.part_id = :id")
    void updateInventory(@Param("id") int id, @Param("amount") int amount);

    @Query("select p.price from Parts p where p.part_id = :id")
    BigDecimal getPrice(int id);


    //ask about what data type amount should be
    @Transactional
    @Modifying
    @Query("update Parts p set p.price = :amount where p.part_id = :id")
    void updatePrice(@Param("id") int id, @Param("amount") BigDecimal amount);

    //double check if i am doing the get model for part right, check service
    @Query("select p.make_model_id from Parts p where p.part_id = :id")
    int getMakeModel(int id);

    @Transactional
    @Modifying
    @Query("update Parts p set p.description = :changes where p.part_id = :id")
    void updateDescription(@Param("id") int id, @Param("changes") String changes);

    @Query("SELECT p.description FROM Parts p WHERE p.part_id = :id")
    String getDescription(@Param("id") int id);
}
