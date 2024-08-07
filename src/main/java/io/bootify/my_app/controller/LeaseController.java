package io.bootify.my_app.controller;

import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.dto.ResponseLeaseDto;
import io.bootify.my_app.dto.ResponsePropertyDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.exception.UserNotFoundException;
import io.bootify.my_app.repos.LeaseRepository;
import io.bootify.my_app.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/lease")
public class LeaseController {

    @Autowired
    private LeaseRepository leaseRepository;
    @Autowired
    private LeaseService leaseService;

    @PostMapping("/add")
    public ResponseEntity<?> createLease(@RequestBody LeaseDto leaseDto){
        try {
            this.leaseService.addLease(leaseDto);
            return ResponseEntity.ok("Lease added successfully.");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed create Lease: " + e.getMessage());
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getLeaseById(@RequestParam Integer leaseId) throws LeaseNotFoundException {
        try {
            LeaseDto leaseById = this.leaseService.getLeaseById(leaseId);
            return new ResponseEntity<>(leaseById, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed get Lease by id: " + e.getMessage());
        }
    }

    @GetMapping("/getAllLeases")
    public ResponseEntity<ResponseLeaseDto> getAllLeases(@RequestParam int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<LeaseDto> leases = this.leaseService.getAllLeases(pageNo, pageSize);
            int totalPages = getTotalPagesForLeases(pageSize);
            ResponseLeaseDto responseLeaseDto = new ResponseLeaseDto("success", leases, totalPages);
            return ResponseEntity.status(HttpStatus.OK).body(responseLeaseDto);
        } catch (LeaseNotFoundException leaseNotFoundException) {
            ResponseLeaseDto responseLeaseDto = new ResponseLeaseDto("unsuccess");
            responseLeaseDto.setException("Lease not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseLeaseDto);
        } catch (PageNotFoundException | UserNotFound pageNotFoundException) {
            ResponseLeaseDto responseLeaseDto = new ResponseLeaseDto("unsuccess");
            responseLeaseDto.setException("Page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseLeaseDto);
        }
    }

    private int getTotalPagesForLeases(int pageSize) {
        int totalLeases = leaseRepository.findAll().size(); // Assuming leaseRepository is your repository for leases
        return (int) Math.ceil((double) totalLeases / pageSize);
    }








    @GetMapping("/getByUserId")
    public ResponseEntity<?> getLeasesByUserId(@RequestParam Integer userId) {
        try {
            List<LeaseDto> leases = leaseService.getLeasesByUserId(userId);
            return new ResponseEntity<>(leases, HttpStatus.OK);
        } catch (LeaseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leases found for User ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get leases by user ID: " + e.getMessage());
        }
    }

    @GetMapping("/getByPropertyOwnerId")
    public ResponseEntity<?> getLeasesByPropertyOwnerId(@RequestParam Integer proprtyWonerId) {
        try {
            List<LeaseDto> leases = leaseService.getLeasesByPropertyOwnerId(proprtyWonerId);
            return new ResponseEntity<>(leases, HttpStatus.OK);
        } catch (LeaseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leases found for Property Owner ID: " + proprtyWonerId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get leases by Property Owner ID: " + e.getMessage());
        }
    }

    @GetMapping("/getByBrokerProfileId")
    public ResponseEntity<?> getLeasesByBrokerProfileId(@RequestParam Integer brokerProfileId) {
        try {
            List<LeaseDto> leases = leaseService.getLeasesByBrokerProfileId(brokerProfileId);
            return new ResponseEntity<>(leases, HttpStatus.OK);
        } catch (LeaseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leases found for Broker Profile ID: " + brokerProfileId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get leases by Broker Profile ID: " + e.getMessage());
        }
    }



    @PutMapping("/update")
    public ResponseEntity<String> editLease(@RequestBody LeaseDto leaseDto, @RequestParam Integer leaseId) {
        try {
            this.leaseService.updateLease(leaseDto, leaseId);
            return ResponseEntity.ok("Lease updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update lease: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteLeaseById(@RequestParam Integer leaseId) throws LeaseNotFoundException {
        try {
            this.leaseService.deleteLeaseById(leaseId);
            return ResponseEntity.ok("Lease deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed get Lease by id: " + e.getMessage());
        }
    }
}
