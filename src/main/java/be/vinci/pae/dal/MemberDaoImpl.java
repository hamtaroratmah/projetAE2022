package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDaoImpl implements MemberDao {

  @Inject
  private DomainFactory domainFactory;
  @Inject
  private DalServices services;

  public MemberDaoImpl() {

  }

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username member's username that you want get
   */
  public MemberDTO getMember(String username) {
    MemberDTO member = null;
    PreparedStatement query = null;
    try {
      query = services.getPreparedStatement(
          "SELECT id_member, password, username,"
              + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
              + " state, count_object_not_collected, count_object_given, count_object_got"
              + " FROM pae.members "
              + "WHERE username = ?");
      query.setString(1, username);
      member = getMemberFromDataBase(query);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        assert query != null;
        query.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
    return member;
  }

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param id member's id that you want get
   */
  @Override
  public MemberDTO getMember(int id) {
    MemberDTO member = null;
    PreparedStatement query = null;
    try {
      query = services.getPreparedStatement(
          "SELECT id_member, password, username,"
              + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
              + " state, count_object_not_collected, count_object_given, count_object_got"
              + "address" + " FROM pae.members "
              + "WHERE id_member = ?");
      query.setInt(1, id);
      member = getMemberFromDataBase(query);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        assert query != null;
        query.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
    return member;
  }

  public void insertMember(Member member){
    PreparedStatement queryMember = null;
    PreparedStatement queryAddress = null;
    try {
      queryAddress = services.getPreparedStatement(
              "INSERT INTO pae.addresses" +
                      "( street, building_number, postcode, commune, city,unit_number)" +
                      " VALUES (?,?,?,?,?,?);"
      );
      queryAddress.setString(1, member.getAddress().getStreet());
      queryAddress.setInt(2,member.getAddress().getBuilding_number());
      queryAddress.setInt(3,member.getAddress().getPostcode());
      queryAddress.setString(4,member.getAddress().getCommune());
      queryAddress.setString(5,member.getAddress().getCity());
      queryAddress.setInt(6,member.getAddress().getUnit_number());

      queryAddress.executeQuery();
    }catch(SQLException e){
      e.printStackTrace();
    }
    try {
      queryMember = services.getPreparedStatement(
              "INSERT INTO pae.members" +
                      "(password, username, last_name, first_name, address, call_number, isadmin,\n" +
                      " reason_for_conn_refusal, state)\n" +
                      "VALUES (?,?,?,?,?,?,?,?,?);"

      );
      queryMember.setString(1, member.getPassword());
      queryMember.setString(2,member.getUsername());
      queryMember.setString(3,member.getLastName());
      queryMember.setString(4,member.getFirstName());
      queryMember.setObject(5,member.getAddress());
      queryMember.setString(6,member.getCallNumber());
      queryMember.setBoolean(7,member.isAdmin());
      queryMember.setString(8,member.getReasonForConnRefusal());
      queryMember.setString(9,member.getState());

      queryMember.executeQuery();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }

  /**
   * Avoid duplicate code if we want to get a user from the dataBase.
   *
   * @param query query to execute
   * @return return the member got in the database
   */
  private MemberDTO getMemberFromDataBase(PreparedStatement query) throws SQLException {
    MemberDTO member = domainFactory.getMember();
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
    resultSetMember.close();
    return member;
  }
}
