package be.vinci.pae.business.domain.businessdomain;

import static org.mindrot.jbcrypt.BCrypt.checkpw;

import be.vinci.pae.business.domain.interfaces.MemberDTO;

public class Member {

  private MemberDTO member;

  public Member(MemberDTO member) {
    this.member = member;
  }

  public boolean checkPassword(String password) {
    return checkpw(password, member.getPassword());
  }

  public MemberDTO getMember() {
    return member;
  }
}
