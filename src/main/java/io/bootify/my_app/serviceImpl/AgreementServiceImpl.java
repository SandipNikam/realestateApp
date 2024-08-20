package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.AgreementNotificationDto;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.repos.AgreementRepository;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.AgreementService;
import io.bootify.my_app.service.EmailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AgreementServiceImpl implements AgreementService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AgreementRepository agreementRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private UserRepo userRepository;


    @Override
    public AgreementDto getAllAgreements() {
        List<Agreement> all = this.agreementRepository.findAll();
        return (AgreementDto) all;
    }
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void updateExpiredAgreements() throws ResourceNotFoundException {
        LocalDate today = LocalDate.now();
        System.out.println("Today's date: " + today);

        List<Agreement> expiredAgreements = agreementRepository.findExpiredAgreements(today);
        System.out.println("Found " + expiredAgreements.size() + " expired agreements.");

        for (Agreement agreement : expiredAgreements) {
            try {
                Property property = agreement.getPropertyAgreement();
                property.setStatus("Available");
                propertyRepository.save(property);
                entityManager.refresh(agreement);
                agreementRepository.delete(agreement);
                System.out.println("Deleted expired agreement with ID: " + agreement.getAgreementId());
            } catch (ObjectOptimisticLockingFailureException e) {
                System.err.println("Optimistic locking exception for agreement ID " + agreement.getAgreementId() + ": " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error deleting agreement: " + e.getMessage());
            }
        }
    }
    @Override
    public Optional<Agreement> getAgreementById(Integer id) {
        try {
            return agreementRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving agreement by ID: " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional
    public void createAgreement(AgreementDto agreementDto) {
        try {
            User user = userRepository.findById(agreementDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Property property = propertyRepository.findById(agreementDto.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Property not found"));
            property.setStatus("Lease");

            Agreement agreement = new Agreement(agreementDto);
            agreement.setUser(user);
            agreement.setPropertyAgreement(property);

            LocalDate endDate = agreementDto.getEndDate();
            LocalDate notificationDate = endDate.minusDays(15);
            agreement.setEndDate(endDate);
            agreement.setNotificationDate(notificationDate);

            Agreement savedAgreement = agreementRepository.save(agreement);

            if (notificationDate != null && notificationDate.isAfter(LocalDate.now())) {
                scheduleNotification(savedAgreement);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while creating agreement", e);
        }
    }

    @Override
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void checkUpcomingAgreementEndings() {
        LocalDate today = LocalDate.now();

        List<Agreement> agreementsEndingSoon = agreementRepository.findEndingWithinDays(15);
        for (Agreement agreement : agreementsEndingSoon) {
            LocalDate endDate = agreement.getEndDate();
            LocalDate notificationThreshold = endDate.minusDays(15);

            if (notificationThreshold.isEqual(today)) {
                scheduleNotificationsForToday(agreement);
            }
        }
    }

   /* @Override
    public void scheduleNotificationsForToday(Agreement agreement) {
        LocalDate today = LocalDate.now();
        LocalDate notificationDate = agreement.getEndDate().minusDays(15);

        if (notificationDate.equals(today)) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endOfDay = now.withHour(23).withMinute(59).withSecond(59);

            Runnable notificationTask = new Runnable() {
                @Override
                public void run() {
                    fetchAndSendNotification(agreement.getAgreementId());
                    LocalDateTime nextRunTime = LocalDateTime.now().plusMinutes(1);
                    if (nextRunTime.isBefore(endOfDay)) {
                        taskScheduler.schedule(this, Date.from(nextRunTime.atZone(ZoneId.systemDefault()).toInstant()));
                    }
                }
            };

            taskScheduler.schedule(notificationTask, Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        }
    }*/
   @Override
   public void scheduleNotificationsForToday(Agreement agreement) {
       LocalDate today = LocalDate.now();
       LocalDate notificationDate = agreement.getEndDate().minusDays(15);

       if (notificationDate.equals(today)) {
           LocalDateTime now = LocalDateTime.now();

           Runnable notificationTask = new Runnable() {
               @Override
               public void run() {
                   fetchAndSendNotification(agreement.getAgreementId());
               }
           };

           taskScheduler.schedule(notificationTask, Date.from(now.plusSeconds(1).atZone(ZoneId.systemDefault()).toInstant()));
       }
   }

    @Transactional
    public AgreementNotificationDto fetchAndSendNotification(Integer agreementId) {
        Agreement agreement = (Agreement) agreementRepository.findByIdWithUserAndProperty(agreementId)
                .orElseThrow(() -> new RuntimeException("Agreement not found"));

        User user = agreement.getUser();
        Property property = agreement.getPropertyAgreement();

        AgreementNotificationDto notificationDto = new AgreementNotificationDto(
                user.getEmail(),
                property.getName()
        );

        sendUpcomingAgreementNotification(notificationDto);
        return notificationDto;
    }

    private void sendUpcomingAgreementNotification(AgreementNotificationDto notificationDto) {
        String message = "Your agreement for property " + notificationDto.getPropertyName() + " is ending in 15 days.";
        emailService.sendEmail(message, "Upcoming agreement ending", notificationDto.getUserEmail());
    }

    private void scheduleNotification(Agreement agreement) {
        LocalDate notificationDate = agreement.getNotificationDate();
        if (notificationDate != null && notificationDate.isAfter(LocalDate.now())) {
            taskScheduler.schedule(
                    () -> sendUpcomingAgreementNotification((AgreementNotificationDto) agreement),
                    Date.from(notificationDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        }
    }
}


