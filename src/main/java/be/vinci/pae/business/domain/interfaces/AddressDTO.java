package be.vinci.pae.business.domain.interfaces;

public interface AddressDTO {

  int getIdAddress();

  void setIdAddress(int idAddress);

  String getStreet();

  void setStreet(String street);

  String getBuildingNumber();

  void setBuildingNumber(String buildingNumber);

  int getPostcode();

  void setPostcode(int postcode);

  String getCommune();

  void setCommune(String commune);

  String getCity();

  void setCity(String city);

  String getUnitNumber();

  void setUnitNumber(String unitNumber);
}
