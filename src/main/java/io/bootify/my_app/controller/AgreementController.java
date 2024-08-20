package io.bootify.my_app.controller;

import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.service.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agreement")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @PostMapping("/add")
    public String createAgreement(@RequestBody AgreementDto AgreementDto) {
        this.agreementService.createAgreement(AgreementDto);
        agreementService.checkUpcomingAgreementEndings();
        return "success";
    }

    @GetMapping("/getAllAgreements")
    public List<AgreementDto> getAllAgreementsList() {
        AgreementDto allAgreements = this.agreementService.getAllAgreements();
        return (List<AgreementDto>) allAgreements;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAgreementById(@PathVariable Integer id) {
        try {
            Optional<Agreement> agreementDto = agreementService.getAgreementById(id);

            if (agreementDto != null) {
                return ResponseEntity.ok("Successful: " + agreementDto.toString());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get agreement: " + e.getMessage());
        }
    }

}
