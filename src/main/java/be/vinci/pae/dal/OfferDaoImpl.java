package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferDaoImpl implements OfferDao {

  @Inject
  DomainFactory domainFactory;
  @Inject
  DalServices services;

  public OfferDaoImpl() {

  }

  @Override
  public OfferDTO getOffer(int idOffer) {
    OfferDTO offer = domainFactory.getOffer();
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT id_offer, date_offer, id_item FROM pae.offers WHERE id_offer = ?")) {
      query.setInt(1, idOffer);
      offer = getOfferFromDatabase(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return offer;
  }

  private OfferDTO getOfferFromDatabase(PreparedStatement query) throws SQLException {
    ResultSet resultSet = query.executeQuery();

    OfferDTO offer = domainFactory.getOffer();
    while (resultSet.next()) {
      offer.setIdOffer(resultSet.getInt(1));
      offer.setDateOffer(resultSet.getDate(2).toLocalDate());
      offer.setIdItem(resultSet.getInt(3));
    }
    return offer;
  }

}
