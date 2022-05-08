package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.*;

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

  public OfferDTO getOffer() {
    return new OfferImpl();
  }

  public AddressDTO getAddress() {
    return new AddressImpl();
  }

  public RatingDTO getRating() {
    return new RatingImpl();

  }

  public InterestDTO getInterest() {
    return new InterestImpl();
  }
}
