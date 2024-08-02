package io.bootify.my_app.repos;


import io.bootify.my_app.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Integer> {


    EmailVerification findByEmail(String email);
}
