package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.RentPerson;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.RentPersonDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.repos.LeaseRepository;
import io.bootify.my_app.repos.RentPersonRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.RentPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentPersonServiceImpl implements RentPersonService {

    @Autowired
    private RentPersonRepository rentPersonRepository;

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private LeaseRepository leaseRepository;



    @Override
    public List<RentPersonDto> getAllRentPersons(int pageNo, int pageSize) throws ResourceNotFoundException, PageNotFoundException, ResourceNotFoundException {
        List<RentPerson> rentPersons = rentPersonRepository.getActivateRentPersonOrderedByCreatedAtDesc();

        if (rentPersons.isEmpty()) {
            throw new ResourceNotFoundException("Rent person not found");
        }

        int totalRentPersons = rentPersons.size();
        int totalPages = (int) Math.ceil((double) totalRentPersons / pageSize);

        if (pageNo < 0 || pageNo >= totalPages) {
            throw new PageNotFoundException("Page not found");
        }

        int pageStart = pageNo * pageSize;
        int pageEnd = Math.min(pageStart + pageSize, totalRentPersons);

        List<RentPersonDto> listOfRentPersonDto = new ArrayList<>();
        for (int i = pageStart; i < pageEnd; i++) {
            RentPerson rentPerson = rentPersons.get(i);
            RentPersonDto rentPersonDto = new RentPersonDto(rentPerson);

            rentPersonDto.setRentPersonId(rentPerson.getRentPersonId());

            listOfRentPersonDto.add(rentPersonDto);
        }

        return listOfRentPersonDto;
    }

    @Override
    public Optional<RentPersonDto> getRentPersonById(Integer id) {
        try {
            return rentPersonRepository.findById(id)
                    .map(RentPersonDto::new);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving rent person by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public RentPersonDto createRentPerson(RentPersonDto rentPersonDto) {
        try {
            User user = userRepository.findById(rentPersonDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            RentPerson rentPerson = new RentPerson(rentPersonDto);
            rentPerson.setUserUser(user);

            RentPerson savedRentPerson = rentPersonRepository.save(rentPerson);
            return new RentPersonDto(savedRentPerson);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while creating rent person: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating rent person: " + e.getMessage(), e);
        }
    }

    @Override
    public RentPersonDto updateRentPerson(Integer id, RentPersonDto rentPersonDTO) {
        try {
            Optional<RentPerson> optionalRentPerson = rentPersonRepository.findById(id);
            if (optionalRentPerson.isPresent()) {
                User user = userRepository.findById(rentPersonDTO.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                RentPerson rentPerson = optionalRentPerson.get();
                rentPerson.setName(rentPersonDTO.getName());
                rentPerson.setAddress(rentPersonDTO.getAddress());
                rentPerson.setMoNumber(rentPersonDTO.getMoNumber());
                rentPerson.setRentPersoncol(rentPersonDTO.getRentPersoncol());
                rentPerson.setUserUser(user);

                RentPerson updatedRentPerson = rentPersonRepository.save(rentPerson);
                return new RentPersonDto(updatedRentPerson);
            } else {
                throw new RuntimeException("Rent person not found with ID: " + id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while updating rent person: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while updating rent person: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteRentPerson(Integer id) {
        try {
            if (rentPersonRepository.existsById(id)) {
                rentPersonRepository.deleteById(id);
            } else {
                throw new RuntimeException("Rent person not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting rent person: " + e.getMessage(), e);
        }
    }
}
