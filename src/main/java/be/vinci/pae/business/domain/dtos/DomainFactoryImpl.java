package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;

public class DomainFactoryImpl implements DomainFactory {

  public DomainFactoryImpl() {

  }

  public MemberDTO getMember() {
    return new MemberImpl();
  }

  public ItemDTO getItem() {
    return new ItemImpl();
  }

  public TypeDTO getType() {
    return new TypeImpl();
  }

}
