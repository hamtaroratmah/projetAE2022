package be.vinci.pae.business.domain.interfacesdto;

public interface AddressDTO {
    int getId_address();

    String getStreet();

    int getBuilding_number();

    int getPostcode();

    String getCommune();

    String getCity();

    int getUnit_number();

    void setBuilding_number(int building_number);

    void setPostcode(int postcode);

    void setCommune(String commune);

    void setCity(String city);

    void setUnit_number(int unit_number);

    void setStreet(String street);

    void setId_address(int id_address);
}
