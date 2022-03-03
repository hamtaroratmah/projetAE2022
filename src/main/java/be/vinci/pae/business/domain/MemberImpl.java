package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT) // ignore all default fields in order to avoid sending props not linked to a JSON view
class MemberImpl implements Member {

  private int id;
  private String login;
  private String password;
  private String lastName;
  private String firstName;
  private Address address;
  private String callNumber;
  private boolean isAdmin;
  private String state;
  

  @Override
  public String getLogin() {
    return login;
  }

  @Override
  public void setLogin(String login) {
    this.login = login;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public String toString() {
    return "{id:" + id + ", login:" + login + ", password:" + password + "}";
  }

}