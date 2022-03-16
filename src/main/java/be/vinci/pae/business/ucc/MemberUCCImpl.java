package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDao memberDao;
  //  @Inject
  //  private DomainFactory domainFactory;

  @Override
  public MemberDTO getOne(String login) {
    return memberDao.getMember(login);
  }

  @Override
  public MemberDTO getOne(int id) {
    return memberDao.getMember(id);
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
    if (!member.checkPassword(password)) {
      throw new WebApplicationException("Invalid password", Status.UNAUTHORIZED);
    }
    return member;
  }


  @Override
  public String getState(String username) {
    return memberDao.getMember(username).getState();
  }

  @Override
  public MemberDTO confirmInscription(String username, boolean isAdmin) {
    return memberDao.confirmInscription(username, isAdmin);
  }

  @Override
  public ArrayList<MemberDTO> listPendingUsers() {
    return memberDao.listPendingUsers();

  }

  @Override
  public ArrayList<MemberDTO> listDeniedUsers() {
    return memberDao.listDeniedUsers();

  }

}