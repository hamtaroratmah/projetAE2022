package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class AddressImpl implements AddressDTO {

  @JsonView(Views.Public.class)
  private Integer idAddress;
  @JsonView(Views.Public.class)
  private String street;
  @JsonView(Views.Public.class)
  private Integer buildingNumber;
  @JsonView(Views.Public.class)
  private Integer postcode;
  @JsonView(Views.Public.class)
  private String city;
  @JsonView(Views.Public.class)
  private String unitNumber;

  public AddressImpl() {

  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public Integer getIdAddress() {
    return idAddress;
  }

  @Override
  public void setIdAddress(Integer idAddress) {
    this.idAddress = idAddress;
  }

  @Override
  public Integer getBuildingNumber() {
    return buildingNumber;
  }

  @Override
  public void setBuildingNumber(Integer buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  @Override
  public Integer getPostcode() {
    return postcode;
  }

  @Override
  public void setPostcode(Integer postcode) {
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
  public String getUnitNumber() {
    return unitNumber;
  }

  @Override
  public void setUnitNumber(String unitNumber) {
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
