package be.vinci.pae.dal;


import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.OfferDao;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
      throw new FatalException(e.getMessage());
    }
    return offer;
  }

  @Override
  public OfferDTO createOffer(ItemDTO newItem) {
    String now = LocalDate.now().toString();
    Date date = Date.valueOf(now);
    String query = "INSERT  INTO pae.offers (date_offer,id_item) VALUES (?,?) RETURNING id_offer, date_offer, id_item ";
    int idItem= newItem.getIdItem();
    System.out.println(idItem);

    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setDate(1, date);
      ps.setInt(2, idItem);
      System.out.println(ps);
      OfferDTO offer=getOfferFromDatabase(ps);
      System.out.println("ok");
      return offer;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;

  }



  private OfferDTO getOfferFromDatabase(PreparedStatement query) throws SQLException {
    ResultSet resultSet = query.executeQuery();
    OfferDTO offer = domainFactory.getOffer();
    while (resultSet.next()) {
      offer.setIdOffer(resultSet.getInt(1));
      offer.setDateOffer(resultSet.getDate(2).toLocalDate());
      offer.setIdItem(resultSet.getInt(3));
      System.out.println(resultSet.getInt(3));
    }
    return offer;
  }

}
