package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.RatingDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.dal.interfaces.RatingDao;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RatingDaoImpl implements RatingDao {

  @Inject
  DomainFactory domainFactory;
  @Inject
  DalServices services;
  @Inject
  MemberUCC memberUCC;
  @Inject
  MemberDao memberDao;

  /**
   * Insert new rating in database.
   */
  public RatingDTO rateAnItem(int itemId, int memberId, int stars, String comment) {
    String query = "INSERT INTO pae.ratings( rating, comment,id_recipient_member ) VALUES(?,?,?) "
        + "RETURNING *;";
    RatingDTO rating = null;
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, stars);
      ps.setString(2, comment);
      ps.setInt(3, memberId);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          rating = createRatingInstance(rs);
          query = "UPDATE pae.items set rating= ? WHERE id_item=? RETURNING id_item";
          try (PreparedStatement pss = services.getPreparedStatement(query)) {
            pss.setInt(1, rs.getInt(1));
            pss.setInt(2, itemId);
            rs.close();
            pss.executeQuery();
          }
        }
      }


    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rating;


  }

  private RatingDTO createRatingInstance(ResultSet rs) throws FatalException, SQLException {
    RatingDTO rating = domainFactory.getRating();

    rating.setRatingId(rs.getInt(1));
    rating.setRating(rs.getInt(2));
    rating.setComment(rs.getString(3));
    String query = "SELECT * FROM pae.members WHERE id_member= ? ";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, rs.getInt(4));

      ps.executeQuery();
    }
    //todo
    //set le member, pas avec id_offering_member?
    rating.setMember(memberDao.getMember(rs.getInt(4)));

    return rating;
  }


}
