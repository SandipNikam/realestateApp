package io.bootify.my_app.controller;


import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.dto.RentPersonDto;
import io.bootify.my_app.dto.ResponseRentPersonDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.repos.RentPersonRepository;
import io.bootify.my_app.service.RentPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentPersons")
public class RentPersonController {

    @Autowired
    private RentPersonService rentPersonService;

    @Autowired
    private RentPersonRepository rentPersonRepository;
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentPersonById(@PathVariable Integer id) {
        try {
            Optional<RentPersonDto> rentPersonById = rentPersonService.getRentPersonById(id);
            return new ResponseEntity<>(rentPersonById, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to get rent person: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> createRentPerson(@RequestBody RentPersonDto rentPersonDTO) {
        try {
            RentPersonDto createdRentPerson = rentPersonService.createRentPerson(rentPersonDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdRentPerson);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create rent person: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRentPerson(@PathVariable Integer id, @RequestBody RentPersonDto rentPersonDTO) {
        try {
            RentPersonDto updatedRentPersonDTO = rentPersonService.updateRentPerson(id, rentPersonDTO);
            if (updatedRentPersonDTO != null) {
                return ResponseEntity.ok(updatedRentPersonDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Rent Person not found with id: " + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update rent person: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRentPerson(@PathVariable Integer id) {
        try {
            rentPersonService.deleteRentPerson(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete rent person: " + e.getMessage());
        }
    }

    @GetMapping("/getAllRentPersons")
    public ResponseEntity<ResponseRentPersonDto> getAllRentPersons(@RequestParam int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<RentPersonDto> listOfRentPersons = this.rentPersonService.getAllRentPersons(pageNo, pageSize);
            int totalPages = getTotalPagesForRentPersons(pageSize);
            ResponseRentPersonDto responseRentPersonDto = new ResponseRentPersonDto("success", listOfRentPersons, totalPages);
            return ResponseEntity.status(HttpStatus.OK).body(responseRentPersonDto);
        } catch (ResourceNotFoundException | UserNotFound rentPersonNotFoundException) {
            ResponseRentPersonDto responseRentPersonDto = new ResponseRentPersonDto("unsuccess");
            responseRentPersonDto.setException("Rent person not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseRentPersonDto);
        } catch (PageNotFoundException pageNotFoundException) {
            ResponseRentPersonDto responseRentPersonDto = new ResponseRentPersonDto("unsuccess");
            responseRentPersonDto.setException("Page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseRentPersonDto);
        }
    }

    private int getTotalPagesForRentPersons(int pageSize) {
        int totalRentPersons = rentPersonRepository.findAll().size();
        return (int) Math.ceil((double) totalRentPersons / pageSize);
    }


}