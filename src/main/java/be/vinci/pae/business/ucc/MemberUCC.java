package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.exceptions.FatalException;
import java.util.ArrayList;

public interface MemberUCC {


  String getState(String username);


  MemberDTO getOne(int id) throws FatalException;

  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  MemberDTO login(String username, String password);

  MemberDTO confirmRegistration(String username, boolean isAdmin);


  ArrayList<MemberDTO> listUsersByState(String state);


  ArrayList<MemberDTO> listDeniedUsers();

  ArrayList<MemberDTO> listPendingUsers();

  MemberDTO denyRegistration(String username);

  MemberDTO register(MemberDTO member);

  Object getOneByUsername(String username);
}
