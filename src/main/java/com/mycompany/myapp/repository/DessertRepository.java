package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Dessert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dessert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DessertRepository extends JpaRepository<Dessert, Long> {

}
