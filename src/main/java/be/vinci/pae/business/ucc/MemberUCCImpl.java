package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.LoginException;
import jakarta.inject.Inject;
import java.util.ArrayList;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDao memberDao;
  @Inject
  private DomainFactory domainFactory;

  @Inject
  private DalServices dalServices;


  //test
  @Override
  public MemberDTO getOne(int id) {
    try {
      dalServices.startTransaction();
      MemberDTO member = memberDao.getMember(id);
      dalServices.commitTransaction();
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * update profile.
   */
  public MemberDTO updateMember(MemberDTO oldMember, MemberDTO newMember) {
    try {
      dalServices.startTransaction();
      if (newMember.getPassword().length() < 60) {
        Member memberBiz = (Member) domainFactory.getMember();
        newMember.setPassword(memberBiz.hashPassword(newMember.getPassword()));
      }
      MemberDTO member = memberDao.updateMember(oldMember, newMember);
      dalServices.commitTransaction();
      return member;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
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
      MemberDTO memberDTO = memberDao.getMemberByUsername(username);
      if (memberDTO == null) {
        throw new LoginException("Username not found");
      }
      Member member = (Member) memberDTO;
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
      throw e;
    }
  }


  @Override
  public String getState(String username) {
    try {
      dalServices.startTransaction();
      String state = memberDao.getMemberByUsername(username).getState();
      dalServices.commitTransaction();
      return state;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
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
      throw e;
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
      throw e;
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
      throw e;
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
      throw e;
    }
  }

  @Override
  public ArrayList<MemberDTO> listPendingUsers() {
    try {
      dalServices.startTransaction();
      ArrayList<MemberDTO> list = memberDao.listUsersByState("pending");
      dalServices.commitTransaction();
      return list;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }


  @Override
  public void register(MemberDTO member, AddressDTO address) {
    try {
      dalServices.startTransaction();
      if (getOneByUsername(member.getUsername()) != null) {
        throw new IllegalArgumentException("L'utilisateur existe déjà !");
      }
      Member memberBiz = (Member) member;
      String hashPass = memberBiz.hashPassword(member.getPassword());
      member.setPassword(hashPass);
      memberDao.register(member, address);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
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
      throw e;
    }
  }

  @Override
  public ArrayList<MemberDTO> searchMember(String lastName) {
    try {
      dalServices.startTransaction();
      ArrayList<MemberDTO> members = memberDao.searchMember(lastName);
      dalServices.commitTransaction();
      return members;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}