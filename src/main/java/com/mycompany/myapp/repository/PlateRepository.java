package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Plate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlateRepository extends JpaRepository<Plate, Long> {

}
