package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.AddressDao;
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
  @Inject
  private AddressDao addressDao;

  public MemberDaoImpl() {

  }

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username member's username that you want get
   */
  public MemberDTO getMemberByUsername(String username) {
    MemberDTO member;
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT id_member, password, username,"
            + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
            + " state, count_object_not_collected, count_object_given, count_object_got, address"
            + " FROM pae.members WHERE username = ?")) {
      query.setString(1, username);
      member = getMemberFromDataBase(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return member;
  }


  /**
   * Get a member according to the id given in parameter and execute a query given by DalServices
   * class.
   *
   * @param id member's id that you want get
   */
  @Override
  public MemberDTO getMember(int id) {
    MemberDTO member;
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT id_member, password, username,"
            + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
            + " state, count_object_not_collected, count_object_given, count_object_got, address"
            + " FROM pae.members WHERE id_member = ? ")) {
      query.setInt(1, id);
      member = getMemberFromDataBase(query);
      return member;
    } catch (SQLException e) {
      throw new FatalException("Problème en db getMember");
    }
  }

  /**
   * Update member's information.
   */
  @Override
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
      e.printStackTrace();
    }

  }

  public MemberDTO updateMember(MemberDTO oldMember, MemberDTO newMember) {
    String stringQuery = "UPDATE pae.members "
        + "SET password = ?"
        + ", username = ?"
        + ", last_name = ?"
        + ", first_name = ?"
        + ", call_number = ?"
        + " WHERE id_member = ?"
        + "RETURNING *";
    try (PreparedStatement query = services.getPreparedStatement(stringQuery)) {
      query.setString(1, newMember.getPassword());
      query.setString(2, newMember.getUsername());
      query.setString(3, newMember.getLastName());
      query.setString(4, newMember.getFirstName());
      query.setString(5, newMember.getCallNumber());
      query.setInt(6, oldMember.getIdMember());
      MemberDTO member = getMemberFromDataBase(query);
      if (member == null) {
        return null;
      }
      member.setAddress(addressDao.updateAddress(oldMember.getAddress(), newMember.getAddress()));
      return member;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Insert a member in the dataBase from information given in the parameter and execute.
   *
   * @param member to insert
   */
  public void register(MemberDTO member, AddressDTO address) {
    PreparedStatement queryMember;
    try {
      queryMember = services.getPreparedStatement(
          "INSERT INTO pae.members"
              + "(password, username, last_name, first_name, address, call_number, "
              + " reason_for_conn_refusal, state) "
              + "VALUES (?,?,?,?,?,?,?,pending);"

      );
      queryMember.setString(1, member.getPassword());
      queryMember.setString(2, member.getUsername());
      queryMember.setString(3, member.getLastName());
      queryMember.setString(4, member.getFirstName());
      queryMember.setInt(5, addressDao.insertAddress(address));
      queryMember.setString(6, member.getCallNumber());
      queryMember.setString(7, member.getReasonForConnRefusal());

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
        "UPDATE pae.members SET state='valid', isAdmin =? WHERE username=? RETURNING *";
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
  public MemberDTO createMemberInstance(ResultSet resultSetMember) throws SQLException {

    MemberDTO member = domainFactory.getMember();
    try {

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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return member;

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
      return null;
    }
    member.setIdMember(resultSetMember.getInt("id_member"));
    member.setPassword(resultSetMember.getString("password"));
    member.setUsername(resultSetMember.getString("username"));
    member.setLastName(resultSetMember.getString("last_name"));
    member.setFirstName(resultSetMember.getString("first_name"));
    member.setCallNumber(resultSetMember.getString("call_number"));
    member.setAdmin(resultSetMember.getBoolean("isadmin"));
    member.setReasonForConnRefusal(resultSetMember.getString("reason_for_conn_refusal"));
    member.setState(resultSetMember.getString("state"));
    member.setCountObjectNotCollected(resultSetMember.getInt("count_object_not_collected"));
    member.setCountObjectGiven(resultSetMember.getInt("count_object_given"));
    member.setCountObjectGot(resultSetMember.getInt("count_object_got"));
    member.setAddress(addressDao.getAddress(resultSetMember.getInt("address")));
    resultSetMember.close();
    return member;
  }


}
