package io.bootify.my_app.controller;

import io.bootify.my_app.dto.*;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.PropertyNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.exception.UserNotFoundException;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

@Autowired
private PropertyRepository propertyRepository;
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<?> createProperty(@RequestBody PropertyDto propertyDto){
       try {
           this.propertyService.addProperty(propertyDto);
           return ResponseEntity.ok("Property Added Succesfully");
       } catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add property: " + e.getMessage());
       }
    }



    @GetMapping("/getById")
    public ResponseEntity<?> getpropertyById(@RequestParam Integer propertyId) throws PropertyNotFoundException {
        try {
            PropertyDto propertyById = this.propertyService.getPropertyById(propertyId);
            return new ResponseEntity<>(propertyById, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get property by id: " + e.getMessage());
        }
    }

 /*   @GetMapping("/getAll")
    public ResponseEntity<?> showAllProperty(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PropertyDto> allProperties = propertyService.getAllProperties(pageable);

            if (page >= allProperties.getTotalPages()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Page not found");
            }

            return new ResponseEntity<>(allProperties, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

    @GetMapping("/getAllProperty")
    public ResponseEntity<ResponsePropertyDto> getAllProperty(@RequestParam int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<PropertyDto> properties = this.propertyService.getAllProperties(pageNo, pageSize);
            int totalPages = getTotalPagesForProperties(pageSize);
            ResponsePropertyDto responseAllUserDto = new ResponsePropertyDto("success", properties, totalPages);
            return ResponseEntity.status(HttpStatus.OK).body(responseAllUserDto);
        } catch (UserNotFoundException userNotFoundException) {
            ResponsePropertyDto responseAllUserDto = new ResponsePropertyDto("unsuccess");
            responseAllUserDto.setException("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllUserDto);
        } catch (PageNotFoundException | UserNotFound pageNotFoundException) {
            ResponsePropertyDto responseAllUserDto = new ResponsePropertyDto("unsuccess");
            responseAllUserDto.setException("Page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAllUserDto);
        }
    }

    private int getTotalPagesForProperties(int pageSize) {
        int totalUsers = propertyRepository.findAll().size();
        return (int) Math.ceil((double) totalUsers / pageSize);
    }


    @PutMapping("/update")
    public ResponseEntity<String> editProperty(@RequestBody PropertyDto propertyDto, @RequestParam Integer propertyId) {
        try {
            this.propertyService.updateProperty(propertyDto, propertyId);
            return ResponseEntity.ok("Property updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update property: " + e.getMessage());
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<PropertyDto>> getPropertiesByUserId(@PathVariable Integer userId) {
        try {
            List<PropertyDto> properties = propertyService.getPropertiesByUserId(userId);
            return new ResponseEntity<>(properties, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/owner/{propertyOwnerId}")
    public ResponseEntity<List<PropertyDto>> getPropertiesByPropertyOwnerId(@PathVariable Integer propertyOwnerId) {
        try {
            List<PropertyDto> properties = propertyService.getPropertiesByPropertyOwnerId(propertyOwnerId);
            return new ResponseEntity<>(properties, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deletePropertyById(@RequestParam Integer propertyId) throws PropertyNotFoundException {
        try {
            this.propertyService.deletePropertyById(propertyId);
            return ResponseEntity.ok(" Property deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete property: " + e.getMessage());

        }
    }
}