package be.vinci.pae.business.domain.interfacesdto;

public interface DomainFactory {

  MemberDTO getMember();

  MemberDTO getState();

  MemberDTO confirmInscription();

 
  AddressDTO getAddress();
}
