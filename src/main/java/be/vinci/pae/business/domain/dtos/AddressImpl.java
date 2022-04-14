package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;

public class AddressImpl implements AddressDTO {

  private int idAddress;
  private String street;
  private int buildingNumber;
  private int postcode;
  private String city;
  private int unitNumber;

  public AddressImpl() {

  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public int getIdAddress() {
    return idAddress;
  }

  @Override
  public void setIdAddress(int idAddress) {
    this.idAddress = idAddress;
  }

  @Override
  public int getBuildingNumber() {
    return buildingNumber;
  }

  @Override
  public void setBuildingNumber(int buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  @Override
  public int getPostcode() {
    return postcode;
  }

  @Override
  public void setPostcode(int postcode) {
    this.postcode = postcode;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public int getUnitNumber() {
    return unitNumber;
  }

  @Override
  public void setUnitNumber(int unitNumber) {
    this.unitNumber = unitNumber;
  }

  @Override
  public String toString() {
    return "AddressesImpl{"
        + "id_address='"
        + idAddress
        + '\''
        + ", building_number='"
        + buildingNumber
        + '\''
        + ", postcode="
        + postcode
        + '\''
        + ", city='"
        + city
        + '\''
        + ", unit_number="
        + unitNumber
        + '}';
  }
}
