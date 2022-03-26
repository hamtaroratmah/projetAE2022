package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import java.util.ArrayList;

public interface MemberDao {

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username username of the member that you want get
   * @return returns a member
   */
  MemberDTO getMember(String username);

  MemberDTO confirmRegistration(String username, boolean isAdmin);

  MemberDTO getMember(int id);

  ArrayList<MemberDTO> listUsersByState(String state);

  MemberDTO denyRegistration(String username);


  void insertMember(Member member);
}
