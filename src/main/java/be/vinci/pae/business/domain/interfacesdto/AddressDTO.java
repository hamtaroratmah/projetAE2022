package be.vinci.pae.business.domain.interfacesdto;

public interface AddressDTO {
    int getIdAddress();

    String getStreet();

    int getBuildingNumber();

    int getPostcode();

    String getCommune();

    String getCity();

    int getUnitNumber();

    void setBuildingNumber(int buildingNumber);

    void setPostcode(int postcode);

    void setCommune(String commune);

    void setCity(String city);

    void setUnitNumber(int unitNumber);

    void setStreet(String street);

    void setIdAddress(int idAddress);
}
