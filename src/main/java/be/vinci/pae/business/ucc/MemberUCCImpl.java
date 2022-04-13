package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.BadRequestException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.LoginException;
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
      if (id < 1) {
        throw new BadRequestException("Un id ne peut être négatif");
      }
      MemberDTO member = memberDao.getMember(id);
      dalServices.commitTransaction();
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }


  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  @Override
  public MemberDTO login(String username, String password) {
    try {
      dalServices.startTransaction();
      Member member = (Member) memberDao.getMemberByUsername(username);
      switch (member.getState()) {
        case "pending":
          throw new LoginException("L'utilisateur est en attente de confirmation.");
        case "denied":
          throw new LoginException("L'utilisateur est refusé pour la raison suivante : "
              + member.getReasonForConnRefusal());
        default:
          break;
      }
      if (!member.checkPassword(password)) {
        throw new LoginException("Invalid password");
      }
      dalServices.commitTransaction();
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new LoginException(e.getMessage());
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
      MemberDTO member = memberDao.confirmRegistration(username, isAdmin);
      dalServices.commitTransaction();
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }

  @Override
  public MemberDTO denyRegistration(String username) {
    try {
      dalServices.startTransaction();
      MemberDTO member = memberDao.denyRegistration(username);
      dalServices.commitTransaction();
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }

  }


  @Override
  public ArrayList<MemberDTO> listUsersByState(String state) {
    try {
      dalServices.startTransaction();
      ArrayList<MemberDTO> list = memberDao.listUsersByState(state);
      dalServices.commitTransaction();
      return list;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }

  @Override
  public ArrayList<MemberDTO> listDeniedUsers() {
    try {
      dalServices.startTransaction();
      ArrayList<MemberDTO> list = memberDao.listUsersByState("denied");
      dalServices.commitTransaction();
      return list;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }


  @Override
  public MemberDTO register(MemberDTO member) {
    try {
      dalServices.startTransaction();
      Member memberBiz = (Member) member;
      String hashPass = memberBiz.hashPassword(member.getPassword());
      member.setPassword(hashPass);
      memberDao.insertMember(member);
      return (MemberDTO) member;
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
      MemberDTO member = memberDao.getMemberByUsername(username);
      dalServices.commitTransaction();
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }
}