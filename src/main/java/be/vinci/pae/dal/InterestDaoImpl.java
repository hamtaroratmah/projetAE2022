package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.InterestDao;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InterestDaoImpl implements InterestDao {

  @Inject
  private DomainFactory domainFactory;
  @Inject
  DalServices services;
  @Inject
  ItemDao itemDao;
  @Inject
  MemberDao memberDao;




  @Override
  public InterestDTO getInterest(int idInterest) {
    InterestDTO interest = null;
    String query = "SELECT * FROM pae.interests WHERE id_interest=?";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idInterest);
      interest = getInterestFromDatabase(ps);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return interest;

  }

  private InterestDTO getInterestFromDatabase(PreparedStatement ps) throws FatalException {
    InterestDTO interest = null;
    ResultSet resultSet = null;
    try {
      resultSet = ps.executeQuery();
      while (resultSet.next()) {

        interest.setInterestId(1);
        ItemDTO item = itemDao.getItem(resultSet.getInt(2));

        MemberDTO member = memberDao.getMember(resultSet.getInt(3));
        interest.setItem(item);
        interest.setMember(member);
        interest.setRecipient(resultSet.getBoolean(4));
        interest.setDateDelivery(resultSet.getString(5));
        interest.setCame(resultSet.getBoolean(6));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return interest;
  }
  /**
   * create a member instance used in methods confirm and deny.
   *
   * @param resultSetInterest to execute this query
   * @return returns the interest DTO
   */
  public InterestDTO createInterestInstance(ResultSet resultSetInterest) {

    InterestDTO interest = domainFactory.getInterest();
    try {

      interest.setInterestId(resultSetInterest.getInt(1));
      interest.setItem(itemDao.getItem(resultSetInterest.getInt((2))));
      interest.setMember(memberDao.getMember(resultSetInterest.getInt(3)));
      interest.setRecipient(resultSetInterest.getBoolean(4));
      interest.setDateDelivery(resultSetInterest.getString(5));
      interest.setCame(resultSetInterest.getBoolean(6));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return interest;
  }

  private ArrayList<InterestDTO> addInterest(ArrayList<Integer> listId) {
    ArrayList<InterestDTO> list = new ArrayList<>();
    String query = "SELECT * FROM pae.interests WHERE id_interest =? ";
    try (PreparedStatement preparedStatement = services.getPreparedStatement(query)) {
      for (Integer id : listId
      ) {
        preparedStatement.setInt(1, id);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            list.add(createInterestInstance(resultSet));
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<InterestDTO> listInterests(int idItem){
    ArrayList<Integer> listId = new ArrayList<>();
    String query = "SELECT * FROM pae.interests WHERE id_item=? ";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, idItem);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          listId.add(resultSet.getInt(1));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return addInterest(listId);
  }
}
