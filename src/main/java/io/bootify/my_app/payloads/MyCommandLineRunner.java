package io.bootify.my_app.payloads;

import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.service.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private AgreementService agreementService;

    @Override
    public void run(String... args) throws Exception {

        try {
            agreementService.updateExpiredAgreements();
        } catch (ResourceNotFoundException e) {
            try {
                throw new ResourceNotFoundException(e.getMessage());
            } catch (ResourceNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        agreementService.checkUpcomingAgreementEndings();
    }
}