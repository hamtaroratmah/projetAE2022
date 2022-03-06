package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfaces.AddressDTO;

public class AddressDTOimpl implements AddressDTO {

  private int idAddress;
  private String street;
  private String buildingNumber;
  private int postcode;
  private String commune;
  private String city;
  private String unitNumber;

  /**
   * Class constructor.
   */
  public AddressDTOimpl(int idAddress, String street, String buildingNumber, int postcode,
      String commune, String city, String unitNumber) {
    this.idAddress = idAddress;
    this.street = street;
    this.buildingNumber = buildingNumber;
    this.postcode = postcode;
    this.commune = commune;
    this.city = city;
    this.unitNumber = unitNumber;
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
  public String getStreet() {
    return street;
  }

  @Override
  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public String getBuildingNumber() {
    return buildingNumber;
  }

  @Override
  public void setBuildingNumber(String buildingNumber) {
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
  public String getCommune() {
    return commune;
  }

  @Override
  public void setCommune(String commune) {
    this.commune = commune;
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
  public String getUnitNumber() {
    return unitNumber;
  }

  @Override
  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }
}
