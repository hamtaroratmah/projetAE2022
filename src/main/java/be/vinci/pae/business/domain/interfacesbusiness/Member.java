package be.vinci.pae.business.domain.interfacesbusiness;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;

public interface Member extends MemberDTO {

  boolean checkPassword(String password);
  String hashPassword(String password);
}
