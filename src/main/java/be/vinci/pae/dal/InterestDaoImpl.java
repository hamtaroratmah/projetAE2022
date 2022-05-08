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

public class InterestDaoImpl implements InterestDao {


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

        interest.setId_interest(1);
        ItemDTO item = itemDao.getItem(resultSet.getInt(2));

        MemberDTO member = memberDao.getMember(resultSet.getInt(3));
        interest.setItem(item);
        interest.setMember(member);
        interest.setRecipient(resultSet.getBoolean(4));
        interest.setDate_delivery(resultSet.getString(5));
        interest.setCame(resultSet.getBoolean(6));


      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return interest;

  }

}
