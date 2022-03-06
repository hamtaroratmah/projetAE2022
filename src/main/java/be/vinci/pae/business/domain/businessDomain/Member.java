package be.vinci.pae.business.domain.businessDomain;

import be.vinci.pae.business.domain.interfaces.MemberDTO;
import be.vinci.pae.business.utils.BCrypt;

public class Member {

  private MemberDTO member;

  public Member(MemberDTO member) {
    this.member = member;
  }

  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, member.getPassword());
  }

  public MemberDTO getMember() {
    return member;
  }
}
