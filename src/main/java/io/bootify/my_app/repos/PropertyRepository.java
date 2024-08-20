package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findByUserUserUserId(Integer userId);

    List<Property> findByStatusIn(@Param("statuses") List<String> statuses);
    List<Property> findByProprtyWonerProprtyWonerId(Integer propertyOwnerId);
}
