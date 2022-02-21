package be.vinci.pae.main;

public class AddressDTO {

  private int idAddress;
  private String street;
  private String buildingNumber;
  private int postcode;
  private String commune;
  private String city;
  private String unitNumber;

  /**
   * Constructor
   */
  public AddressDTO(int idAddress, String street, String buildingNumber, int postcode,
      String commune, String city, String unitNumber) {
    this.idAddress = idAddress;
    this.street = street;
    this.buildingNumber = buildingNumber;
    this.postcode = postcode;
    this.commune = commune;
    this.city = city;
    this.unitNumber = unitNumber;
  }

  public int getIdAddress() {
    return idAddress;
  }

  public void setIdAddress(int idAddress) {
    this.idAddress = idAddress;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getBuildingNumber() {
    return buildingNumber;
  }

  public void setBuildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  public int getPostcode() {
    return postcode;
  }

  public void setPostcode(int postcode) {
    this.postcode = postcode;
  }

  public String getCommune() {
    return commune;
  }

  public void setCommune(String commune) {
    this.commune = commune;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getUnitNumber() {
    return unitNumber;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }
}
