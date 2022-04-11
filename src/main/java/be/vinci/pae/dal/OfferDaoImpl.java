package be.vinci.pae.dal;


import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
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
import java.util.ArrayList;

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
    String query = "INSERT  INTO pae.offers (date_offer,id_item)"
        + " VALUES (?,?) RETURNING id_offer, date_offer, id_item ";
    int idItem = newItem.getIdItem();
    System.out.println(idItem);

    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setDate(1, date);
      ps.setInt(2, idItem);
      System.out.println(ps);
      OfferDTO offer = getOfferFromDatabase(ps);
      System.out.println("ok");
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
  public ArrayList<MemberDTO> interests(int idItem, int idMember) {
    ArrayList<MemberDTO> list = new ArrayList<>();
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
            list.add(createMemberInstance(resultSet));
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;


  }

  /**
   * create a member instance used in methods confirm and deny.
   *
   * @param resultSetMember to execute this query
   * @return returns the member DTO
   */
  public MemberDTO createMemberInstance(ResultSet resultSetMember) throws SQLException {

    MemberDTO member = domainFactory.getMember();

    member.setIdMember(resultSetMember.getInt(1));
    member.setPassword(resultSetMember.getString(2));
    member.setUsername(resultSetMember.getString(3));
    member.setLastName(resultSetMember.getString(4));
    member.setFirstName(resultSetMember.getString(5));
    member.setCallNumber(resultSetMember.getString(7));
    member.setAdmin(resultSetMember.getBoolean(8));
    member.setReasonForConnRefusal(resultSetMember.getString(9));
    member.setState(resultSetMember.getString(10));
    member.setCountObjectNotCollected(resultSetMember.getInt(11));
    member.setCountObjectGiven(resultSetMember.getInt(12));
    member.setCountObjectGot(resultSetMember.getInt(13));
    return member;
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
