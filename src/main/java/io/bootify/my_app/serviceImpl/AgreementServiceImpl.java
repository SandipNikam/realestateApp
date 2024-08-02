package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.exception.AgreementNotFoundException;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFoundException;
import io.bootify.my_app.repos.AgreementRepository;
import io.bootify.my_app.repos.BrokerProfileRepository;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.AgreementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgreementServiceImpl implements AgreementService {


    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BrokerProfileRepository brokerProfileRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public AgreementDto addAgreement(AgreementDto agreementDto) {
        try {
            Agreement agreement = new Agreement(agreementDto);

           Agreement saveAgreement = agreementRepository.save(agreement);

           AgreementDto savedDto = new AgreementDto(saveAgreement);

           return savedDto;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AgreementDto updateAgreement(AgreementDto agreementDto, Integer agreementId) {

        try {
            Optional<Agreement> optionalAgreement = agreementRepository.findById(agreementId);

            if (optionalAgreement.isPresent()) {
                Agreement agreement = optionalAgreement.get();
                agreement.setDuration(agreementDto.getDuration());
                agreement.setType(agreementDto.getType());
                agreement.setRate(agreementDto.getRate());

                // Set other properties as needed
              Agreement updateOne =  agreementRepository.save(agreement);

              AgreementDto updatedDto = new AgreementDto(updateOne);

              return updatedDto;

            } else {
                throw new AgreementNotFoundException("Agreeement not found with ID: " + agreementId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<AgreementDto> getAllAgreement() {

        List<Agreement> agreements = agreementRepository.findAll();
        return agreements.stream()
                .map(AgreementDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Agreement> findAgreementWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<Agreement> agreementPage = agreementRepository.findAll(PageRequest.of(offset, pageSize).withSort((Sort.by(Sort.Direction.DESC, field))));
        return agreementPage;

    }

    @Override
    public AgreementDto getAgreementById(Integer agreementId) {

        Optional<Agreement> optionalAgreement = agreementRepository.findById(agreementId);
        if (optionalAgreement.isPresent()) {
            return new AgreementDto(optionalAgreement.get());
        } else {
            throw new AgreementNotFoundException("Broker profile not found with ID: " + agreementId);
        }
    }

    @Override
    public void deleteAgreementById(Integer agreementId) {

        Optional<Agreement> optionalAgreement = agreementRepository.findById(agreementId);
        if (optionalAgreement.isPresent()) {
            agreementRepository.deleteById(agreementId);
        } else {
            throw new AgreementNotFoundException("Broker profile not found with ID: " + agreementId);
        }
    }

    @Override
    public List<AgreementDto> getAgreementByUserId(Integer userId) throws ResourceNotFoundException {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        List<Agreement> agreements = this.agreementRepository.findByUser(user);

        List<AgreementDto> agreementDtos = agreements.stream().map((agreement)-> this.modelMapper.map(agreement , AgreementDto.class)).collect(Collectors.toList());

        return agreementDtos;
    }

    @Override
    public List<AgreementDto> getAgreementByBrokerId(Integer brokerId) throws ResourceNotFoundException {

        BrokerProfile brokerProfile = this.brokerProfileRepository.findById(brokerId)
                .orElseThrow(()-> new ResourceNotFoundException("BrokerProfile","Id",brokerId));

        Optional<Agreement> agreements = this.agreementRepository.findById(brokerProfile.getBrokerAgreement().getAgreementId());

        List<AgreementDto> agreementDtos = agreements.stream().map((agreement)-> this.modelMapper.map(agreement , AgreementDto.class)).collect(Collectors.toList());

        return agreementDtos;
    }

    @Override
    public List<AgreementDto> getAgreementByPropertyId(Integer propertyId) throws ResourceNotFoundException {

        Property property = this.propertyRepository.findById(propertyId)
                .orElseThrow(()-> new ResourceNotFoundException("Property","Id",propertyId));

        Optional<Agreement> agreements = this.agreementRepository.findById(property.getPropertyId());

        List<AgreementDto> agreementDtos = agreements.stream().map((post)-> this.modelMapper.map(post , AgreementDto.class)).collect(Collectors.toList());

        return agreementDtos;
    }
}
