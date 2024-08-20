package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    @Query("SELECT a FROM Agreement a WHERE a.duration <= :days")
    List<Agreement> findEndingWithinDays(@Param("days") int days);

    @Query("SELECT a FROM Agreement a WHERE a.endDate BETWEEN :startDate AND :endDate")
    List<Agreement> findEndingBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM Agreement a WHERE a.endDate < :today")
    List<Agreement> findExpiredAgreements(@Param("today") LocalDate today);

    @Query("SELECT a FROM Agreement a JOIN FETCH a.user JOIN FETCH a.propertyAgreement WHERE a.agreementId = :agreementId")
    Optional<Agreement> findByIdWithUserAndProperty(@Param("agreementId") Integer agreementId);
}