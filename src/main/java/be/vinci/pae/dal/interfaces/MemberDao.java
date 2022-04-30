package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberDao {

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username username of the member that you want get
   * @return returns a member
   */
  MemberDTO getMemberByUsername(String username);

  MemberDTO confirmRegistration(String username, boolean isAdmin);

  MemberDTO getMember(int id);

  MemberDTO updateMember(MemberDTO oldMember, MemberDTO newMember);

  ArrayList<MemberDTO> listUsersByState(String state);

  MemberDTO denyRegistration(String username, String reasonForConnRefusal);

  boolean register(MemberDTO member, AddressDTO address);

  void insertMember(MemberDTO member);

  MemberDTO createMemberInstance(ResultSet resultSet) throws SQLException;
}
