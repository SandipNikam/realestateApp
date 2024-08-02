package io.bootify.my_app.repos;


import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.domain.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrokerProfileRepository extends JpaRepository<BrokerProfile, Integer> {

    List<BrokerProfile> findByUserUserId(Integer userId);
}
