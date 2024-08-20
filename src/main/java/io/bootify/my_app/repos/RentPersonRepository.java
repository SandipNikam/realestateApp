package io.bootify.my_app.repos;

import io.bootify.my_app.domain.RentPerson;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentPersonRepository extends JpaRepository<RentPerson, Integer> {
    @Query("SELECT r FROM RentPerson r WHERE  r.status = 'activate' ORDER BY r.id DESC")
    List<RentPerson> getActivateRentPersonOrderedByCreatedAtDesc();
}
