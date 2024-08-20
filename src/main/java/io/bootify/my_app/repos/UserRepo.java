package io.bootify.my_app.repos;


import io.bootify.my_app.domain.Lease;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);

 //   List<User> findByBrokerProfiles_BrokerProfileId(Integer brokerProfileId);
}
