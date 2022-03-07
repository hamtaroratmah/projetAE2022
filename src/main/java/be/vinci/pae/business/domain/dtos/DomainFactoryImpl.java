package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesDTO.DomainFactory;
import be.vinci.pae.business.domain.interfacesDTO.MemberDTO;

public class DomainFactoryImpl implements DomainFactory {

  public DomainFactoryImpl() {

  }

  public MemberDTO getMember() {
    return new MemberImpl();
  }

}
