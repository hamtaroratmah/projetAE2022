package be.vinci.pae.business.domain.interfacesdto;

public interface DomainFactory {

  MemberDTO getMember();

  MemberDTO getState();

  MemberDTO confirmInscription();

 
  ItemDTO getItem();

  TypeDTO getType();

  OfferDTO getOffer();
  
  AddressDTO getAddress();
}
