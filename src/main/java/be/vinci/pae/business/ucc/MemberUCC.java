package be.vinci.pae.business.ucc;


import be.vinci.pae.business.domain.dtos.DomainFactoryImpl;
import be.vinci.pae.business.domain.interfacesBusiness.Member;
import be.vinci.pae.business.domain.interfacesDTO.DomainFactory;
import be.vinci.pae.business.domain.interfacesDTO.MemberDTO;
import be.vinci.pae.dal.MemberDao;
import jakarta.inject.Inject;

public class MemberUCC {

  @Inject
  private MemberDao memberDao = new MemberDao();
  @Inject
  private DomainFactory domainFactory = new DomainFactoryImpl();

  public MemberDTO getOne(String login) {
    return memberDao.getMember(login);
  }


  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  public Member login(String username, String password) {
    Member member = (Member) domainFactory.getMember();
    if (!member.checkPassword(password)) {
      return null;
    }
    return null; //todo
  }

}