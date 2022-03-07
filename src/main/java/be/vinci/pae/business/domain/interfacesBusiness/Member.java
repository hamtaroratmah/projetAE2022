package be.vinci.pae.business.domain.interfacesBusiness;

import be.vinci.pae.business.domain.interfacesDTO.MemberDTO;

public interface Member extends MemberDTO {

  public boolean checkPassword(String password);

}
