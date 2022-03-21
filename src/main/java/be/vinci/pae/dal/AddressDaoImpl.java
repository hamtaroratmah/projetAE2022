package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.dal.interfaces.AddressDao;
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
    AddressDTO address = null;
    try (
      PreparedStatement query = services.getPreparedStatement(
        "SELECT id_address, street, building_number,"
          + " postcode, commune, city, unit_number"
          + " FROM pae.addresses "
          + "WHERE id_address = ?");
    ) {
      address = getAdressFromDatabase(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return address;
  }

  private AddressDTO getAdressFromDatabase(PreparedStatement query) throws SQLException {
    AddressDTO address = domainFactory.getAddress();
    ResultSet resultSetAdress = query.executeQuery();

    if (!resultSetAdress.next()) {
      throw new WebApplicationException("Adress not found");
    }
    address.setIdAddress(resultSetAdress.getInt(1));
    address.setStreet(resultSetAdress.getString(2));
    address.setBuildingNumber(resultSetAdress.getInt(3));
    address.setPostcode(resultSetAdress.getInt(4));
    address.setCommune(resultSetAdress.getString(5));
    address.setCity(resultSetAdress.getString(6));
    address.setUnitNumber(resultSetAdress.getInt(7));

    resultSetAdress.close();
    return address;
  }
}
