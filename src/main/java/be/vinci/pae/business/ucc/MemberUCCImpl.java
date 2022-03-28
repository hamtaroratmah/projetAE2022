package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.BizExceptioinUnauthorized;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.util.ArrayList;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDao memberDao;

  @Inject
  private DalServices dalServices;


  //test
  @Override
  public MemberDTO getOne(int id) {
    try {
      dalServices.startTransaction();
      return memberDao.getMember(id);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
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
      Member member = (Member) memberDao.getMemberByUsername(username);
      if (!member.checkPassword(password)) {
        throw new BizExceptioinUnauthorized("Invalid password");
      }
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }
  }


  @Override
  public String getState(String username) {
    return memberDao.getMemberByUsername(username).getState();
  }

  @Override
  public MemberDTO confirmRegistration(String username, boolean isAdmin) {
    try {
      dalServices.startTransaction();
      return memberDao.confirmRegistration(username, isAdmin);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public MemberDTO denyRegistration(String username) {
    try {
      dalServices.startTransaction();
      return memberDao.denyRegistration(username);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }

  }


  @Override
  public ArrayList<MemberDTO> listPendingUsers() {
    try {
      dalServices.startTransaction();
      return memberDao.listUsersByState("pending");
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }

  }

  @Override
  public ArrayList<MemberDTO> listDeniedUsers() {
    try {
      dalServices.startTransaction();
      return memberDao.listUsersByState("denied");
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }

  }


  @Override
  public MemberDTO register(Member member) {
    try {
      dalServices.startTransaction();
      String hashPass = member.hashPassword(member.getPassword());
      member.setPassword(hashPass);
      memberDao.insertMember(member);
      MemberDTO newMember = (MemberDTO) member;
      return newMember;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public Object getOneByUsername(String username) {
    try {
      dalServices.startTransaction();
      return memberDao.getMemberByUsername(username);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }
  }


}