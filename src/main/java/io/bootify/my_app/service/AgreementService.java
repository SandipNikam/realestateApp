package io.bootify.my_app.service;

import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;

import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AgreementService {

    AgreementDto addAgreement(AgreementDto agreementDto);

    AgreementDto updateAgreement(AgreementDto agreementDto , Integer agreementId);

    List<AgreementDto> getAllAgreement();

    public Page<Agreement> findAgreementWithPaginationAndSorting(int offset , int pageSize , String field);


    AgreementDto getAgreementById(Integer agreementId) ;

    void deleteAgreementById(Integer agreementId) ;

    List<AgreementDto> getAgreementByUserId(Integer userId) throws ResourceNotFoundException;

    List<AgreementDto> getAgreementByBrokerId(Integer brokerId) throws ResourceNotFoundException;

    List<AgreementDto> getAgreementByPropertyId(Integer propertyId) throws ResourceNotFoundException;
}
