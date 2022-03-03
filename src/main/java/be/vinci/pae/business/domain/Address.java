package be.vinci.pae.business.domain;

public interface Address {

  int getId();

  void setId(int id);

  String getStreet();

  void setStreet(String street);

  int getBuildingNumber();

  void setBuildingNumber(int buildingNumber);

  int getPostCode();

  void setPostCode(int postCode);

  String getCommune();

  void setCommune(String commune);

  String getCity();

  void setCity(String city);

  int getUnitNumber();

  void setUnitNumber(int unitNumber);
}
