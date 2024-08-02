package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Lease;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.PropertyNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.repos.LeaseRepository;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.repos.ProprtyWonerRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ProprtyWonerRepository proprtyWonerRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LeaseRepository leaseRepository;


    @Override
    public String addProperty(PropertyDto propertyDto) {
        try {
            User user = userRepo.findById(propertyDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            PropertyOwner propertyOwner = proprtyWonerRepository.findById(propertyDto.getPropertyOwnerId())
                    .orElseThrow(() -> new RuntimeException("Property owner not found"));

            Set<Lease> leases = new HashSet<>();
            if (propertyDto.getPropertyLeases() != null && !propertyDto.getPropertyLeases().isEmpty()) {
                for (Integer leaseId : propertyDto.getPropertyLeases()) {
                    Lease lease = leaseRepository.findById(leaseId)
                            .orElseThrow(() -> new RuntimeException("Lease with ID " + leaseId + " not found"));
                    leases.add(lease);
                }
            }

            Property property = new Property(propertyDto);
            property.setUserUser(user);
            property.setProprtyWoner(propertyOwner);
            property.setPropertyLeases(leases);

            propertyRepository.save(property);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String updateProperty(PropertyDto propertyDto,Integer propertyId) {
        try {
            Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
            if (optionalProperty.isPresent()) {
                Property property = optionalProperty.get();

                property.setArea(propertyDto.getArea());
                property.setName(propertyDto.getName());
                property.setType(propertyDto.getType());
                property.setAddress(propertyDto.getAddress());
                property.setStatus(propertyDto.getStatus());
                property.setCity(propertyDto.getCity());
                property.setComments(propertyDto.getComments());
                property.setFlore(propertyDto.getFlore());

                propertyRepository.save(property);

                return "success";
            } else {
                throw new PropertyNotFoundException("Property not found with ID: " + propertyDto.getPropertyId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (PropertyNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<PropertyDto> getAllProperties(Pageable pageable) {
        Page<Property> properties = propertyRepository.findAll(pageable);
        return properties.map(PropertyDto::new);
    }

    @Override
    public List<PropertyDto> getPropertiesByUserId(Integer userId) {
        List<Property> properties = propertyRepository.findByUserUserUserId(userId);
        if (properties.isEmpty()) {
            throw new RuntimeException("No properties found for user ID: " + userId);
        }
        return properties.stream()
                .map(PropertyDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyDto> getPropertiesByPropertyOwnerId(Integer propertyOwnerId) {
        List<Property> properties = propertyRepository.findByProprtyWonerProprtyWonerId(propertyOwnerId);
        if (properties.isEmpty()) {
            throw new RuntimeException("No properties found for property owner ID: " + propertyOwnerId);
        }
        return properties.stream()
                .map(PropertyDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDto getPropertyById(Integer propertyId) throws PropertyNotFoundException {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isPresent()) {
            PropertyDto propertyDto = new PropertyDto(optionalProperty.get());
            return propertyDto;
        } else {
            throw new PropertyNotFoundException("Property not found with ID: " + propertyId);
        }
    }

    @Override
    public void deletePropertyById(Integer propertyId) throws PropertyNotFoundException {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isPresent()) {
          this.propertyRepository.deleteById(propertyId);
        } else {
            throw new PropertyNotFoundException("Property not found with ID: " + propertyId);
        }
    }

    @Override
    public List<PropertyDto> getAllProperties(int pageNo, int pageSize) throws PageNotFoundException, UserNotFound {
        List<Property> properties = propertyRepository.getActivatePropertyOrderedByCreatedAtDesc();
        if (properties.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        int totalProperties = properties.size();
        int totalPages = (int) Math.ceil((double) totalProperties / pageSize);

        if (pageNo < 0 || pageNo >= totalPages) {
            throw new PageNotFoundException("Page not found");
        }

        int pageStart = (pageNo) * pageSize;
        int pageEnd = Math.min(pageStart + pageSize, totalProperties);

        List<PropertyDto> propertyDtos = new ArrayList<>();
        for (int i = pageStart; i < pageEnd; i++) {
          Property property = properties.get(i);
           PropertyDto propertyDto = new PropertyDto(property);
            propertyDto.setPropertyId(property.getPropertyId());
            propertyDtos.add(propertyDto);
        }

        return propertyDtos;
    }

}
