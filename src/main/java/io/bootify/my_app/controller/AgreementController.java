package io.bootify.my_app.controller;

import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.exception.AgreementNotFoundException;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.service.AgreementService;
import io.bootify.my_app.service.BrokerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agreement")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @PostMapping("/add")
    public ResponseEntity<AgreementDto> createAgreement(@RequestBody AgreementDto agreementDto) {

           agreementDto = agreementService.addAgreement(agreementDto);

            ResponseEntity<AgreementDto> responseEntity = new ResponseEntity<>(agreementDto , HttpStatus.CREATED);

            return responseEntity;

           // return ResponseEntity.ok("Agreement created successfully.");

    }

    @PutMapping("/update")
    public ResponseEntity<AgreementDto> updateAgreement(@RequestBody AgreementDto agreementDto, @RequestParam Integer agreementId) {
        try {
         AgreementDto  updatedAgreement = agreementService.updateAgreement(agreementDto, agreementId);

            ResponseEntity<AgreementDto> responseEntity = new ResponseEntity<>(updatedAgreement , HttpStatus.CREATED);

            return responseEntity;
           // return ResponseEntity.ok("Agreement updated successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to update agreement" + e.getMessage()) ;
        }
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<List<BrokerProfileDto>> showAllBrokerProfiles() {
//        List<BrokerProfileDto> allBrokerProfiles = this.brokerProfileService.getAllBrokerProfiles();
//        return new ResponseEntity<>(allBrokerProfiles, HttpStatus.OK);
//    }

    @GetMapping("/getAll/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<Agreement>> showAllAgreement(@PathVariable int offset , @PathVariable int pageSize , @PathVariable String field) {
        Page<Agreement> allAgreements = this.agreementService.findAgreementWithPaginationAndSorting(offset, pageSize, field);
        return new ResponseEntity<>(allAgreements, HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<?> showAgreementById(@RequestParam Integer agreementId)  {
        try {
            AgreementDto agreementDto = this.agreementService.getAgreementById(agreementId);
            return new ResponseEntity<>(agreementDto, HttpStatus.OK);
        } catch (AgreementNotFoundException e) {
            throw new AgreementNotFoundException("Agreeement not found with ID: " + agreementId);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteAgreementById(@RequestParam Integer agreementId)  {
        try {
            this.agreementService.deleteAgreementById(agreementId);
            return ResponseEntity.ok("Agreement deleted successfully.");
        } catch (AgreementNotFoundException e) {
            throw new AgreementNotFoundException("Agreement not found with ID: " + agreementId);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AgreementDto>> getAgreementByUser(@PathVariable Integer userId) throws ResourceNotFoundException {

        List<AgreementDto> agreementDtos = this.agreementService.getAgreementByUserId(userId);

        return new ResponseEntity<List<AgreementDto>>(agreementDtos,HttpStatus.OK);
    }

    @GetMapping("/broker/{brokerId}")
    public ResponseEntity<List<AgreementDto>> getAgreementByBroker(@PathVariable Integer brokerId) throws ResourceNotFoundException {

        List<AgreementDto> agreementDtos = this.agreementService.getAgreementByBrokerId(brokerId);

        return new ResponseEntity<List<AgreementDto>>(agreementDtos,HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<AgreementDto>> getAgreementByProperty(@PathVariable Integer propertyId) throws ResourceNotFoundException {

        List<AgreementDto> agreementDtos = this.agreementService.getAgreementByPropertyId(propertyId);

        return new ResponseEntity<List<AgreementDto>>(agreementDtos,HttpStatus.OK);
    }

}
