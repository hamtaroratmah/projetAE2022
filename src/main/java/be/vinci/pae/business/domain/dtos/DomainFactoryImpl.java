package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;

public class DomainFactoryImpl implements DomainFactory {

  public DomainFactoryImpl() {

  }

  public MemberDTO getMember() {
    return new MemberImpl();
  }

  public MemberDTO getState() {
    return new MemberImpl();
  }

  public MemberDTO confirmInscription() {
    return new MemberImpl();
  }


  public ItemDTO getItem() {
    return new ItemImpl();
  }

  public TypeDTO getType() {
    return new TypeImpl();
  }

  public OfferDTO getOffer() {
    return new OfferImpl();
  }

  public AddressDTO getAddress() {
    return new AddressImpl();
  }
}
