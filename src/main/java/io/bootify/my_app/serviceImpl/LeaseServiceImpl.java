package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.*;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.repos.*;
import io.bootify.my_app.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ProprtyWonerRepository proprtyWonerRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BrokerProfileRepository brokerProfileRepository;

    @Autowired
    private RentPersonRepository rentPersonRepository;


    @Override
    public String addLease(LeaseDto leaseDto) {
        try {
            Property property = propertyRepository.findById(leaseDto.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Property not found"));
            User user = userRepo.findById(leaseDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            BrokerProfile brokerProfile = brokerProfileRepository.findById(leaseDto.getBrokerProfileId())
                    .orElseThrow(() -> new RuntimeException("Broker Not Found"));
            PropertyOwner propertyOwner = proprtyWonerRepository.findById(leaseDto.getPropertyOwnerId())
                    .orElseThrow(() -> new RuntimeException("Property owner not found"));
           /* RentPerson rentPerson = rentPersonRepository.findById(leaseDto.getRentPerson())
                    .orElseThrow(() -> new RuntimeException("Rent Person Not Found"));*/

            Lease lease = new Lease(leaseDto);
            lease.setPropertyProperty(property);
            lease.setUserUser(user);
            lease.setBrokerProfiles(brokerProfile);
            lease.setProprtyWoner(propertyOwner);
            //lease.setRentPerson(rentPerson);
            leaseRepository.save(lease);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateLease(LeaseDto leaseDto, Integer leaseId) {
        try {
            Optional<Lease> optionalLease = leaseRepository.findById(leaseId);
            if (optionalLease.isPresent()) {
                Lease lease = optionalLease.get();
                lease.setStartDate(leaseDto.getStartDate());
                lease.setEndDate(leaseDto.getEndDate());
                lease.setStatus(leaseDto.getStatus());
                lease.setAmount(leaseDto.getAmount());
                lease.setRent(leaseDto.getRent());
                lease.setDeposit(leaseDto.getDeposit());
                lease.setPropertyBookingcol(leaseDto.getPropertyBookingcol());
                lease.setOwnerId(leaseDto.getOwnerId());

                leaseRepository.save(lease);
                return "success";
            } else {
                throw new LeaseNotFoundException("Lease not found with ID: " + leaseId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<LeaseDto> getAllLeases(Pageable pageable) {
        Page<Lease> leases = leaseRepository.findAll(pageable);
        return leases.map(LeaseDto::new);
    }

    @Override
    public LeaseDto getLeaseById(Integer leaseId) throws LeaseNotFoundException {
        Optional<Lease> optionalLease = leaseRepository.findById(leaseId);
        if (optionalLease.isPresent()) {
            return new LeaseDto(optionalLease.get());
        } else {
            throw new RuntimeException("Lease not found with ID: " + leaseId);
        }
    }

    @Override
    public List<LeaseDto> getLeasesByPropertyOwnerId(Integer proprtyWonerId) {
        List<Lease> leases = leaseRepository.findByProprtyWoner_ProprtyWonerId(proprtyWonerId);
        if (leases.isEmpty()) {
            throw new LeaseNotFoundException("No leases found for property owner ID: " + proprtyWonerId);
        }
        return leases.stream().map(LeaseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<LeaseDto> getLeasesByBrokerProfileId(Integer brokerProfileId) {
        List<Lease> leases = leaseRepository.findByBrokerProfiles_BrokerProfileId(brokerProfileId);
        if (leases.isEmpty()) {
            throw new LeaseNotFoundException("No leases found for broker profile ID: " + brokerProfileId);
        }
        return leases.stream().map(LeaseDto::new).collect(Collectors.toList());
    }

//    @Override
//    public List<LeaseDto> getLeasesByUserId(Integer userId) {
//        Optional<Lease> byId = leaseRepository.findById(userId);
//        if (byId.isEmpty()) {
//            throw new RuntimeException("No Lease found for user ID: " + userId);
//        }
//        return byId.stream()
//                .map(LeaseDto::new)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<LeaseDto> getLeasesByUserId(Integer userId) {
        List<Lease> leases = leaseRepository.findByUserUser_UserId(userId);
        if (leases.isEmpty()) {
            throw new LeaseNotFoundException("No leases found for user ID: " + userId);
        }
        return leases.stream().map(LeaseDto::new).collect(Collectors.toList());
    }


    @Override
    public void deleteLeaseById(Integer leaseId) throws LeaseNotFoundException {
        Optional<Lease> optionalLease = leaseRepository.findById(leaseId);
        if (optionalLease.isPresent()) {
            this.leaseRepository.deleteById(leaseId);
        } else {
            throw new RuntimeException("Lease not found with ID: " + leaseId);
        }
    }


}
