package io.bootify.my_app.service;

import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.AgreementNotificationDto;
import io.bootify.my_app.exception.ResourceNotFoundException;

import java.util.Optional;

public interface AgreementService {
    public void createAgreement(AgreementDto AgreementDto);

     void checkUpcomingAgreementEndings();

    AgreementDto getAllAgreements();

    void updateExpiredAgreements() throws ResourceNotFoundException;

    Optional<Agreement> getAgreementById(Integer id);

    void scheduleNotificationsForToday(Agreement agreement);
    AgreementNotificationDto fetchAndSendNotification(Integer agreementId);
}
