package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findByUserUserUserId(Integer userId);

    List<Property> findByProprtyWonerProprtyWonerId(Integer propertyOwnerId);

    @Query("SELECT p FROM Property p WHERE  p.status = 'activate' ORDER BY p.id DESC")
    List<Property> getActivatePropertyOrderedByCreatedAtDesc();
}
