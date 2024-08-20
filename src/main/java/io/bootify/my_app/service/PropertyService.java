package io.bootify.my_app.service;




import io.bootify.my_app.domain.Property;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.exception.PropertyNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyService {
   public String addProperty(PropertyDto propertyDto);


   String updateProperty(PropertyDto propertyDto, Integer propertyId);

   List<Property> getAllProperties();


   List<PropertyDto> getPropertiesByUserId(Integer userId);

   List<PropertyDto> getPropertiesByPropertyOwnerId(Integer propertyOwnerId);



   PropertyDto getPropertyById(Integer propertyId) throws PropertyNotFoundException;

   void deletePropertyById(Integer propertyId) throws PropertyNotFoundException;
}
