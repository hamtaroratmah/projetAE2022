package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import java.util.ArrayList;

public interface MemberUCC {


  String getState(String username);

  MemberDTO getOne(String login);

  MemberDTO getOne(int id);

  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  Member login(String username, String password);

  MemberDTO confirmRegistration(String username, boolean isAdmin);


  ArrayList<MemberDTO> listPendingUsers();


  ArrayList<MemberDTO> listDeniedUsers();

  MemberDTO denyRegistration(String username);
  MemberDTO register(Member member);
}
