package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDao memberDao;

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

  //test
  @Override
  public MemberDTO getOne(int id) {
    try {
      dalServices.startTransaction();
      return memberDao.getMember(id);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    } finally {
      dalServices.commitTransaction();
    }
    return null;
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
  public String getState(String username) {
    return memberDao.getMember(username).getState();
  }

  @Override
  public MemberDTO confirmRegistration(String username, boolean isAdmin) {
    return memberDao.confirmRegistration(username, isAdmin);
  }

  @Override
  public MemberDTO denyRegistration(String username) {
    return memberDao.denyRegistration(username);
  }

  @Override
  public ArrayList<MemberDTO> listPendingUsers() {
    return memberDao.listUsersByState("pending");

  }

  @Override
  public ArrayList<MemberDTO> listDeniedUsers() {
    return memberDao.listUsersByState("denied");
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