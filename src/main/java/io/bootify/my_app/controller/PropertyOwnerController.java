package io.bootify.my_app.controller;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.dto.PropertyOwnerDto;
import io.bootify.my_app.dto.ResponseOwnerDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.repos.ProprtyWonerRepository;
import io.bootify.my_app.service.PropertyOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/propertyOwners")
public class PropertyOwnerController {


    @Autowired
    private PropertyOwnerService propertyOwnerService;


@Autowired
private ProprtyWonerRepository proprtyWonerRepository;

    @PostMapping("/")
    public ResponseEntity<?> createPropertyOwner(@RequestBody PropertyOwnerDto propertyOwnerDto) {
        try {
            PropertyOwner propertyOwner = propertyOwnerService.createPropertyOwner(propertyOwnerDto);
            return ResponseEntity.ok("Property Owner added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add property owner: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePropertyOwner(@PathVariable Integer id, @RequestBody PropertyOwnerDto propertyOwnerDetails) {
        try {
            PropertyOwner updatedPropertyOwner = propertyOwnerService.updatePropertyOwner(id, propertyOwnerDetails);
            return ResponseEntity.ok("Property Owner updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update property owner: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropertyOwner(@PathVariable Integer id) {
        try {
            propertyOwnerService.deletePropertyOwner(id);
            return ResponseEntity.ok("Property Owner deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete property owner: " + e.getMessage());
        }
    }
    @GetMapping("/getAllPropertyOwners")
    public ResponseEntity<ResponseOwnerDto> getAllPropertyOwners(@RequestParam int pageNo, @RequestParam(defaultValue = "10") int pageSize) throws ResourceNotFoundException {
        try {
            List<PropertyOwnerDto> listOfPropertyOwners = this.propertyOwnerService.getAllPropertyOwners(pageNo, pageSize);
            int totalPages = getTotalPagesForPropertyOwners(pageSize);
            ResponseOwnerDto responsePropertyOwnerDto = new ResponseOwnerDto("success", listOfPropertyOwners, totalPages);
            return ResponseEntity.status(HttpStatus.OK).body(responsePropertyOwnerDto);
        } catch (PageNotFoundException pageNotFoundException) {
            ResponseOwnerDto responsePropertyOwnerDto = new ResponseOwnerDto("unsuccess");
            responsePropertyOwnerDto.setException("Page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePropertyOwnerDto);

        } catch (UserNotFound e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
    private int getTotalPagesForPropertyOwners(int pageSize) {
        int totalUsers = proprtyWonerRepository.findAll().size();
        return (int) Math.ceil((double) totalUsers / pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyOwnerById(@PathVariable Integer id) {
        try {
            Optional<PropertyOwner> propertyOwner = propertyOwnerService.getPropertyOwnerById(id);
            return new ResponseEntity<>("Succesfull: " , HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to get property owner: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
