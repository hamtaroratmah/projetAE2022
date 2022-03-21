package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;

public class AddressImpl implements AddressDTO {

    private int id_address;
    private String street;
    private int building_number;
    private int postcode;
    private String commune;
    private String city;
    private int unit_number;

    public AddressImpl() {

    }

    public String getStreet() {
        return street;
    }

    @Override
    public int getId_address() {
        return id_address;
    }

    @Override
    public int getBuilding_number() {
        return building_number;
    }

    @Override
    public int getPostcode() {
        return postcode;
    }

    @Override
    public String getCommune() {
        return commune;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public int getUnit_number() {
        return unit_number;
    }

    @Override
    public void setBuilding_number(int building_number) {
        this.building_number = building_number;
    }

    @Override
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    @Override
    public void setCommune(String commune) {
        this.commune = commune;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setUnit_number(int unit_number) {
        this.unit_number = unit_number;
    }

    @Override
    public void setId_address(int id_address) {

    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressesImpl{" +
                "id_address='" + id_address + '\'' +
                ", building_number='" + building_number + '\'' +
                ", postcode=" + postcode +
                ", commune='" + commune + '\'' +
                ", city='" + city + '\'' +
                ", unit_number=" + unit_number +
                '}';
    }
}
