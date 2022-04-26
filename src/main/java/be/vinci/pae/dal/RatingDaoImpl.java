package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.RatingDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.RatingDao;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RatingDaoImpl implements RatingDao {

  @Inject
  DomainFactory domainFactory;
  @Inject
  DalServices services;


  public RatingDTO rateAnItem(int itemId, int memberId, int stars, String comment) {
    String query = "INSERT INTO pae.ratings VALUES(?,?,?,?)";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, itemId);
      ps.setInt(2, memberId);
      ps.setInt(3, stars);
      ps.setString(4, comment);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return RatingDTO;

    //todo

  }

}
