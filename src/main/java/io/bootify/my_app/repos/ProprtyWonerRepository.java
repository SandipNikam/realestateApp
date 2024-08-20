package io.bootify.my_app.repos;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProprtyWonerRepository extends JpaRepository<PropertyOwner, Integer> {
    @Query("SELECT p FROM PropertyOwner p WHERE  p.status = 'activate' ORDER BY p.id DESC")
    List<PropertyOwner> getActivatePropertyOwnerOrderedByCreatedAtDesc();
}
