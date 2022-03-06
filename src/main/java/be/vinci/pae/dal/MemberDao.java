package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.business.domain.interfaces.DomainFactory;
import be.vinci.pae.business.domain.interfaces.MemberDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {

  @Inject
  private final DalFactory dalfactory = new DalFactory();
  @Inject
  private final DomainFactory domainFactory = new DomainFactoryImpl();
  private final DalServices services = dalfactory.getDalServices();

  public MemberDao() {

  }

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username username of the member that you want get
   */
  public MemberDTO getMember(String username) {
    MemberDTO member = domainFactory.getMemberDto();
    try {
      PreparedStatement query = services.getUser();
      query.setString(1, username);
      ResultSet resultSetMember = query.executeQuery();
      if (!resultSetMember.next()) {
        throw new WebApplicationException("Username not found");
      }
      member.setIdMember(resultSetMember.getInt(1));
      member.setPassword(resultSetMember.getString(2));
      member.setUsername(resultSetMember.getString(3));
      member.setLastName(resultSetMember.getString(4));
      member.setFirstName(resultSetMember.getString(5));
      member.setCallNumber(resultSetMember.getString(6));
      member.setAdmin(resultSetMember.getBoolean(7));
      member.setReasonForConnRefusal(resultSetMember.getString(8));
      member.setState(resultSetMember.getString(9));
      member.setCountObjectNotCollected(resultSetMember.getInt(10));
      member.setCountObjectGiven(resultSetMember.getInt(11));
      member.setCountObjectGot(resultSetMember.getInt(12));


    } catch (SQLException e) {
      e.printStackTrace();
    }
    return member;
  }

}
