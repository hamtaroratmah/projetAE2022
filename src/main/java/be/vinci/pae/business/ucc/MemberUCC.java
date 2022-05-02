package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import java.util.ArrayList;

public interface MemberUCC {


  String getState(String username);


  MemberDTO getOne(int id);

  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  MemberDTO login(String username, String password);

  MemberDTO confirmRegistration(String username, boolean isAdmin);

  MemberDTO updateMember(MemberDTO oldMember, MemberDTO newMember, String confirmPasswod);

  ArrayList<MemberDTO> listDeniedUsers();

  ArrayList<MemberDTO> listPendingUsers();

  MemberDTO denyRegistration(String username, String reasonForConnRefusal);

  MemberDTO register(MemberDTO member, AddressDTO address);

  Object getOneByUsername(String username);

  boolean preclude(int idMember);

  boolean unpreclude(int idMember);
}
