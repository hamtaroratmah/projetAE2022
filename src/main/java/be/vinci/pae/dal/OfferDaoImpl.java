package be.vinci.pae.dal;


import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.dal.interfaces.OfferDao;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OfferDaoImpl implements OfferDao {

  @Inject
  DomainFactory domainFactory;
  @Inject
  DalServices services;
  @Inject
  MemberDao memberDao;

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
    String query = "INSERT  INTO pae.offers (date_offer,id_item)"
        + " VALUES (?,?) RETURNING id_offer, date_offer, id_item ";
    int idItem = newItem.getIdItem();

    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setDate(1, date);
      ps.setInt(2, idItem);
      OfferDTO offer = getOfferFromDatabase(ps);
      return offer;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;

  }

  @Override
  public boolean isLiked(int idItem, int idMember) {
    boolean isLiked = false;
    String query = "SELECT * FROM pae.interests WHERE id_item= ? AND id_member = ?";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idItem);
      ps.setInt(2, idMember);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {

          isLiked = true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return isLiked;
  }

  @Override
  public ArrayList<MemberDTO> interests(int idItem) {
    ArrayList<Integer> listId = new ArrayList<>();
    String query = "SELECT * FROM pae.interests WHERE id_item=? ";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idItem);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          listId.add(resultSet.getInt(3));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return addMember(listId);
  }

  private ArrayList<MemberDTO> addMember(ArrayList<Integer> listId) {
    ArrayList<MemberDTO> list = new ArrayList<>();
    String query = "SELECT * FROM pae.members WHERE id_member =? ";
    try (PreparedStatement preparedStatement = services.getPreparedStatement(query)) {
      for (Integer id : listId
      ) {
        preparedStatement.setInt(1, id);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            list.add(memberDao.createMemberInstance(resultSet));
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  @Override
  public int getIdItem(int idOffer) {
    String query = "SELECT id_item FROM pae.offers WHERE id_offer=?";
    int id = 0;
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idOffer);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        id = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
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

  @Override
  public boolean cancel(int idItem) {

    boolean cancelled = false;
    String query = "UPDATE pae.items SET item_condition= 'cancelled' WHERE id_item=? "
        + "RETURNING id_item ";

    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idItem);
      cancelled = true;
      ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return cancelled;
  }

}
