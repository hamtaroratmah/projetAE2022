package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.AddressImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = AddressImpl.class)
public interface AddressDTO {

  Integer getIdAddress();

  void setIdAddress(Integer idAddress);

  String getStreet();

  void setStreet(String street);

  Integer getBuildingNumber();

  void setBuildingNumber(Integer buildingNumber);

  Integer getPostcode();

  void setPostcode(Integer postcode);

  String getCity();

  void setCity(String city);

  String getUnitNumber();

  void setUnitNumber(String unitNumber);
}
