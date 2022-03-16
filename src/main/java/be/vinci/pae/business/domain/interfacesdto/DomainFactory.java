package be.vinci.pae.business.domain.interfacesdto;

public interface DomainFactory {

  MemberDTO getMember();

  MemberDTO getState();

  MemberDTO confirmInscription();

  //je dois le mettre ici ou pas ?
  //ArrayList<MemberDTO> listPendingUsers();

}
