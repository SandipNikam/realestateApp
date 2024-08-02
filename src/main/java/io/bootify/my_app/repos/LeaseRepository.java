package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Lease;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Integer> {

    List<Lease> findByUserUser_UserId(Integer userId);

    List<Lease> findByProprtyWoner_ProprtyWonerId(Integer proprtyWonerId);

    List<Lease> findByBrokerProfiles_BrokerProfileId(Integer brokerProfileId);

    @Query("SELECT l FROM Lease l WHERE  l.status = 'activate' ORDER BY l.id DESC")
    List<Lease> getActivateLeaseOrderedByCreatedAtDesc();

}
