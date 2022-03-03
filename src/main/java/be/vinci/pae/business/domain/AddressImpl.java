package be.vinci.pae.business.domain;

public class AddressImpl implements Address {

  private int id;
  private String street;
  private int buildingNumber;
  private int postCode;
  private String commune;
  private String city;
  private int unitNumber;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
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
  public int getBuildingNumber() {
    return buildingNumber;
  }

  @Override
  public void setBuildingNumber(int buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  @Override
  public int getPostCode() {
    return postCode;
  }

  @Override
  public void setPostCode(int postCode) {
    this.postCode = postCode;
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
  public int getUnitNumber() {
    return unitNumber;
  }

  @Override
  public void setUnitNumber(int unitNumber) {
    this.unitNumber = unitNumber;
  }
  
}
