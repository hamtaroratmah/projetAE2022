package be.vinci.pae.business.domain.interfacesdto;

public interface AddressDTO {

  int getIdAddress();

  void setIdAddress(int idAddress);

  String getStreet();

  void setStreet(String street);

  int getBuildingNumber();

  void setBuildingNumber(int buildingNumber);

  int getPostcode();

  void setPostcode(int postcode);

  String getCommune();

  void setCommune(String commune);

  String getCity();

  void setCity(String city);

  int getUnitNumber();

  void setUnitNumber(int unitNumber);
}
