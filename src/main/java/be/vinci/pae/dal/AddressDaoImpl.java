package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.dal.interfaces.AddressDao;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDaoImpl implements AddressDao {

  @Inject
  private DomainFactory domainFactory;
  @Inject
  private DalServices services;

  public AddressDaoImpl() {

  }

  // get an addresse by his id
  @Override
  public AddressDTO getAddress(int id) {
    AddressDTO address;
    PreparedStatement query;
    try {
      query = services.getPreparedStatement(
          "SELECT id_address, street, building_number,"
              + " postcode, city, unit_number"
              + " FROM pae.addresses "
              + "WHERE id_address = ?");
      query.setInt(1, id);
      address = getAdressFromDatabase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return address;
  }

  @Override
  public int insertAddress(AddressDTO address) {
    PreparedStatement queryAddress;
    int idAddress = -1;
    try {
      queryAddress = services.getPreparedStatement(
          "INSERT INTO pae.addresses"
              + "( street, building_number, postcode, city,unit_number)"
              + " VALUES (?,?,?,?,?) RETURNING id_address;"
      );
      queryAddress.setString(1, address.getStreet());
      queryAddress.setInt(2, address.getBuildingNumber());
      queryAddress.setInt(3, address.getPostcode());
      queryAddress.setString(4, address.getCity());
      queryAddress.setString(5, address.getUnitNumber());
      ResultSet rs = queryAddress.executeQuery();
      if (rs.next()) {
        idAddress = rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return idAddress;
  }

  @Override
  public AddressDTO updateAddress(AddressDTO oldAddress, AddressDTO newAddress) {
    String stringQuery = " UPDATE pae.addresses "
        + " SET street = ?"
        + ", city = ?"
        + ", unit_number = ?"
        + ", building_number = ?"
        + ", postcode = ?"
        + " WHERE id_address = ? "
        + "RETURNING *";
    try (PreparedStatement query = services.getPreparedStatement(stringQuery)) {
      query.setString(1, newAddress.getStreet());
      query.setString(2, newAddress.getCity());
      query.setString(3, newAddress.getUnitNumber());
      query.setInt(4, newAddress.getBuildingNumber());
      query.setInt(5, newAddress.getPostcode());
      query.setInt(6, oldAddress.getIdAddress());
      return getAdressFromDatabase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }


  private AddressDTO getAdressFromDatabase(PreparedStatement query) throws SQLException {
    AddressDTO address = domainFactory.getAddress();
    ResultSet resultSetAdress = query.executeQuery();
    if (!resultSetAdress.next()) {
      throw new WebApplicationException("Address not found");
    }
    address.setIdAddress(resultSetAdress.getInt(1));
    address.setStreet(resultSetAdress.getString(2));
    address.setBuildingNumber(resultSetAdress.getInt(3));
    address.setPostcode(resultSetAdress.getInt(4));
    address.setCity(resultSetAdress.getString(5));
    address.setUnitNumber(resultSetAdress.getString(6));
    resultSetAdress.close();
    return address;
  }
}
