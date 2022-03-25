package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDao memberDao;
  //  @Inject
  //  private DomainFactory domainFactory;
  @Inject
  private DalServices dalServices;

  @Override
  public MemberDTO getOne(String login) {
    try {
      dalServices.startTransaction();
      return memberDao.getMember(login);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public MemberDTO getOne(int id) {
    try {
      dalServices.startTransaction();
      return memberDao.getMember(id);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }


  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  @Override
  public Member login(String username, String password) {
    try {
      dalServices.startTransaction();
      Member member = (Member) memberDao.getMember(username);
      if (!member.checkPassword(password)) {
        throw new WebApplicationException("Invalid password", Status.UNAUTHORIZED);
      }
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public MemberDTO register(Member member) {
    String hashPass = member.hashPassword(member.getPassword());
    member.setPassword(hashPass);
    memberDao.insertMember(member);
    MemberDTO newMember = (MemberDTO) member;
    return newMember;
  }

}