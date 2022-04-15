package be.vinci.pae.business.domain.interfacesdto;

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
