package be.vinci.pae.business.domain.dtos;

import static org.mindrot.jbcrypt.BCrypt.checkpw;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(Include.NON_DEFAULT)
// ignore all null fields in order to avoid sending props not linked to a JSON view
public class MemberImpl implements MemberDTO, Member {

  @JsonView(Views.Public.class)
  private int idMember;
  @JsonView(Views.Internal.class)
  private String password;
  @JsonView(Views.Public.class)
  private String username;
  @JsonView(Views.Public.class)
  private String lastName;
  @JsonView(Views.Public.class)
  private String firstName;
  @JsonView(Views.Public.class)
  private String callNumber;
  @JsonView(Views.Public.class)
  private String reasonForConnRefusal;
  @JsonView(Views.Public.class)
  private String state;
  @JsonView(Views.Public.class)
  private boolean isAdmin;
  @JsonView(Views.Public.class)
  private int countObjectNotCollected;
  @JsonView(Views.Public.class)
  private int countObjectGiven;
  @JsonView(Views.Public.class)
  private int countObjectGot = 0;

  /**
   * Empty constructor.
   */
  public MemberImpl() {

  }

  @Override
  public boolean checkPassword(String password) {
    return checkpw(password, this.password);
  }

  @Override
  public int getIdMember() {
    return idMember;
  }

  @Override
  public void setIdMember(int idMember) {
    if (idMember <= 0) {
      throw new IllegalArgumentException();
    }
    this.idMember = idMember;
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
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(String username) {
    if (username == null) {
      throw new IllegalArgumentException();
    }
    this.username = username;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    if (lastName == null) {
      throw new IllegalArgumentException();
    }
    this.lastName = lastName;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    if (firstName == null) {
      throw new IllegalArgumentException();
    }
    this.firstName = firstName;
  }

  @Override
  public String getCallNumber() {
    return callNumber;
  }

  @Override
  public void setCallNumber(String callNumber) {
    this.callNumber = callNumber;
  }

  @Override
  public String getReasonForConnRefusal() {
    return reasonForConnRefusal;
  }

  @Override
  public void setReasonForConnRefusal(String reasonForConnRefusal) {
    //    if (reasonForConnRefusal == null) {
    //      throw new IllegalArgumentException();
    //    }
    this.reasonForConnRefusal = reasonForConnRefusal;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    //    if (state == null) {
    //      throw new IllegalArgumentException();
    //    }
    this.state = state;
  }

  @Override
  public boolean isAdmin() {
    return isAdmin;
  }

  @Override
  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  @Override
  public int getCountObjectNotCollected() {
    return countObjectNotCollected;
  }

  @Override
  public void setCountObjectNotCollected(int countObjectNotCollected) {
    this.countObjectNotCollected = countObjectNotCollected;
  }

  @Override
  public int getCountObjectGiven() {
    return countObjectGiven;
  }

  @Override
  public void setCountObjectGiven(int countObjectGiven) {
    this.countObjectGiven = countObjectGiven;
  }

  @Override
  public int getCountObjectGot() {
    return countObjectGot;
  }

  @Override
  public void setCountObjectGot(int countObjectGot) {
    this.countObjectGot = countObjectGot;
  }

  @Override
  public String toString() {
    return "Member{"
        + "id_member="
        + idMember
        + ", username='"
        + username
        + '\''
        + ", last_name='"
        + lastName
        + '\''
        + ", first_name='"
        + firstName
        + '\''
        + '}';
  }

}
