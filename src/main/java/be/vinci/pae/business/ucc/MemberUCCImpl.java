package be.vinci.pae.business.ucc;


import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDao memberDao;
  @Inject
  private DomainFactory domainFactory;

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
    Member member = (Member) memberDao.getMember(username);
    if (member == null) {
      throw new WebApplicationException("Username not found", Status.UNAUTHORIZED);
    }
    if (!member.checkPassword(password)) {
      throw new WebApplicationException("Invalid password", Status.UNAUTHORIZED);
    }
    return member;
  }

}