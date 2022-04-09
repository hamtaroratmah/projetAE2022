package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDaoImpl implements MemberDao {

  @Inject
  private DomainFactory domainFactory;
  @Inject
  private DalBackendServices services;

  public MemberDaoImpl() {

  }

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username member's username that you want get
   */
  public MemberDTO getMemberByUsername(String username) {
    MemberDTO member = null;
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT id_member, password, username,"
            + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
            + " state, count_object_not_collected, count_object_given, count_object_got"
            + " FROM pae.members " + "WHERE username = ?")) {
      query.setString(1, username);
      member = getMemberFromDataBase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
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
  public MemberDTO getMember(int id) throws SQLException {
    MemberDTO member = null;
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT id_member, password, username,"
            + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
            + " state, count_object_not_collected, count_object_given, count_object_got"
            + " FROM pae.members " + "WHERE id_member = ?")) {
      query.setInt(1, id);
      member = getMemberFromDataBase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return member;
  }


  /**
   * Insert a member in the dataBase from the informations given in the parameter and execute.
   *
   * @param member to insert
   */
  public void insertMember(MemberDTO member) {
    PreparedStatement queryMember;
    PreparedStatement queryAddress;
    int idAddress = -1;
    try {
      queryAddress = services.getPreparedStatement(
          "INSERT INTO pae.addresses"
              + "( street, building_number, postcode, city,unit_number)"
              + " VALUES (?,?,?,?,?) RETURNING id_address;"
      );
      queryAddress.setString(1, member.getAddress().getStreet());
      queryAddress.setInt(2, member.getAddress().getBuildingNumber());
      queryAddress.setInt(3, member.getAddress().getPostcode());
      queryAddress.setString(4, member.getAddress().getCity());
      queryAddress.setInt(5, member.getAddress().getUnitNumber());

      ResultSet rs = queryAddress.executeQuery();
      if (rs.next()) {
        idAddress = rs.getInt(1);
      }

    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    try {
      queryMember = services.getPreparedStatement(
          "INSERT INTO pae.members"
              + "(password, username, last_name, first_name, address, call_number, isadmin,\n"
              + " reason_for_conn_refusal, state)\n"
              + "VALUES (?,?,?,?,?,?,?,?,?);"

      );
      queryMember.setString(1, member.getPassword());
      queryMember.setString(2, member.getUsername());
      queryMember.setString(3, member.getLastName());
      queryMember.setString(4, member.getFirstName());
      queryMember.setInt(5, idAddress);
      queryMember.setString(6, member.getCallNumber());
      queryMember.setBoolean(7, member.isAdmin());
      queryMember.setString(8, member.getReasonForConnRefusal());
      queryMember.setString(9, member.getState());

      queryMember.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * lists users by a state.
   *
   * @param state state to list
   * @return return an arrayList of members
   */
  @Override
  public ArrayList<MemberDTO> listUsersByState(String state) {
    ArrayList<MemberDTO> list = new ArrayList<>();
    String query = "SELECT * FROM pae.members WHERE state=?";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setString(1, state);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          list.add(createMemberInstance(resultSet));
        }
      }


    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return list;

  }

  /**
   * confirm a registration.
   *
   * @param username user ton confirm
   * @return returns the member DTO
   */
  public MemberDTO confirmRegistration(String username, boolean isAdmin) {
    MemberDTO member;
    String query =
        "UPDATE pae.members SET state='confirmed', isAdmin =? WHERE username=? RETURNING *";
    System.out.println(query);
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setBoolean(1, isAdmin);
      ps.setString(2, username);
      try (ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
          member = createMemberInstance(rs);
          return member;
        }


      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return null;
  }

  /**
   * deny a registration.
   *
   * @param username user ton deny
   * @return returns the member DTO
   */
  public MemberDTO denyRegistration(String username) {
    MemberDTO member;
    String query = "UPDATE pae.members SET state='denied' WHERE username=? RETURNING *";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
          member = createMemberInstance(rs);
          return member;
        }


      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return null;
  }

  /**
   * create a member instance used in methods confirm and deny.
   *
   * @param resultSetMember to execute this query
   * @return returns the member DTO
   */
  public static MemberDTO createMemberInstance(ResultSet resultSetMember) throws SQLException {

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


  /**
   * Avoid duplicate code if we want to get a user from the dataBase.
   *
   * @param query query to execute
   * @return return the member got in the database
   */
  public MemberDTO getMemberFromDataBase(PreparedStatement query) throws SQLException {
    MemberDTO member = domainFactory.getMember();
    ResultSet resultSetMember = query.executeQuery();

    if (!resultSetMember.next()) {
      throw new FatalException("Username not found");
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
