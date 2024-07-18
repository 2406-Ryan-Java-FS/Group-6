package com.revature.repository;

import com.revature.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {

    Part findByPartId(int partId);

    List<Part> findAllByPartName(String partName);

    boolean existsByPartName(String partName);

    // We shouldn't need any of these as we can use findByPartId() and save()

//    @Transactional
//    @Modifying
//    @Query("update parts p set p.inventory = :amount where p.part_id = :partId")
//    void updateInventory(@Param("partId") int partId, @Param("amount") int amount);
//
//    @Query("select p.price from Parts p where p.part_id = :id")
//    BigDecimal getPrice(int id);
//
//    @Transactional
//    @Modifying
//    @Query("update parts p set p.price = :amount where p.part_id = :partId")
//    void updatePrice(@Param("partId") int partId, @Param("amount") BigDecimal amount);
//
//    @Query("select p.make_model_id from Parts p where p.part_id = :id")
//    int getMakeModel(int id);
//
//    @Transactional
//    @Modifying
//    @Query("update parts p set p.description = :changes where p.part_id = :partId")
//    void updateDescription(@Param("partId") int partId, @Param("changes") String changes);
//
//    @Query("SELECT p.description FROM Parts p WHERE p.part_id = :id")
//    String getDescription(@Param("id") int id);
}
