package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Drink;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Drink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
