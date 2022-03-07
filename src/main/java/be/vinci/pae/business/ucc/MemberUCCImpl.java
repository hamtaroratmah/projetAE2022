package be.vinci.pae.business.ucc;


import be.vinci.pae.business.domain.dtos.DomainFactoryImpl;
import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.MemberDaoImpl;
import jakarta.inject.Inject;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDaoImpl memberDao = new MemberDaoImpl();
  @Inject
  private DomainFactory domainFactory = new DomainFactoryImpl();

  @Override
  public MemberDTO getOne(String login) {
    return memberDao.getMember(login);
  }


  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  @Override
  public Member login(String username, String password) {
    Member member = (Member) domainFactory.getMember();
    if (!member.checkPassword(password)) {
      return null;
    }
    return null; //todo
  }

}