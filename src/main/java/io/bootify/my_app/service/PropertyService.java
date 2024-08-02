package io.bootify.my_app.service;




import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.PropertyNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyService {
   public String addProperty(PropertyDto propertyDto);


   String updateProperty(PropertyDto propertyDto, Integer propertyId);

   Page<PropertyDto> getAllProperties(Pageable pageable);


   List<PropertyDto> getPropertiesByUserId(Integer userId);

   List<PropertyDto> getPropertiesByPropertyOwnerId(Integer propertyOwnerId);



   PropertyDto getPropertyById(Integer propertyId) throws PropertyNotFoundException;

   void deletePropertyById(Integer propertyId) throws PropertyNotFoundException;

    List<PropertyDto> getAllProperties(int pageNo, int pageSize) throws UserNotFound;
}
