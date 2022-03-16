package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import java.util.ArrayList;

public interface MemberDao {

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username username of the member that you want get
   */
  MemberDTO getMember(String username);

//    MemberDTO getState(String login);

  ArrayList<MemberDTO> listDeniedUsers();

  MemberDTO confirmInscription(String username, boolean isAdmin);

  MemberDTO getMember(int id);

  ArrayList<MemberDTO> listPendingUsers();
}
