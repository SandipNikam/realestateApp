package io.bootify.my_app.repos.controller;

import io.bootify.my_app.controller.AgreementController;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.service.AgreementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AgreementControllerTest {

    @InjectMocks
    private AgreementController agreementController;

    @Mock
    private AgreementService agreementService;

    @Test
    @DisplayName("Test Success scenario for saving new agreement")
    public void testSaveAgreement()
    {
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setType("Rent Property");

        AgreementDto saveAgreement = new AgreementDto();
        saveAgreement.setAgreementId(1);
        saveAgreement.setType(agreementDto.getType());

        Mockito.when(agreementService.addAgreement(agreementDto)).thenReturn(saveAgreement);

        ResponseEntity<AgreementDto> responseEntity = agreementController.createAgreement(agreementDto);

        //Assertions.assertNotNull(responseEntity.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED.value() ,  responseEntity.getStatusCodeValue());
    }


   /* @Test
    @DisplayName("Test Success scenario for Fetching all agreements")
    public void testGetAgreement()
    {
        List<AgreementDto> agreementDtos = new ArrayList<>();
        PropertyDTO propertyDTO = new PropertyDTO();

        propertyDTO.setId(1L);
        propertyDTO.setTitle("Dummy Property");

        propertyDTOList.add(propertyDTO);

        Mockito.when(propertyService.getAllProperties()).thenReturn(propertyDTOList);

        ResponseEntity<List<PropertyDTO>> responseEntity = propertyController.getAllProperties();

        Assertions.assertEquals(1, responseEntity.getBody().size());
        Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }*/

    @Test
    @DisplayName("Test Success scenario for updating only price of agreement")
    public void testUpdatePropertyPrice()
    {
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setDuration(6.5);

        Mockito.when(agreementService.updateAgreement(Mockito.any() , Mockito.anyInt())).thenReturn(agreementDto);

        ResponseEntity<AgreementDto> responseEntity = agreementController.updateAgreement(agreementDto , 1);

        Assertions.assertEquals(6.5 , responseEntity.getBody().getDuration());

       // Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }
}
