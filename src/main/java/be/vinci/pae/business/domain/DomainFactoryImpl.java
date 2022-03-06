package be.vinci.pae.business.domain;

import be.vinci.pae.business.domain.dtos.MemberDTOImpl;
import be.vinci.pae.business.domain.interfaces.DomainFactory;
import be.vinci.pae.business.domain.interfaces.MemberDTO;

public class DomainFactoryImpl implements DomainFactory {

  public DomainFactoryImpl() {

  }

  public MemberDTO getMemberDto() {
    return new MemberDTOImpl();
  }

}
