package be.vinci.pae.business.domain.dtos;

import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
// ignore all null fields in order to avoid sending props not linked to a JSON view
public class MemberImpl implements Member {

  @JsonView(Views.Public.class)
  private Integer idMember;
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
  private Boolean isAdmin;
  @JsonView(Views.Public.class)
  private Integer countObjectNotCollected;
  @JsonView(Views.Public.class)
  private Integer countObjectGiven;
  @JsonView(Views.Public.class)
  private Integer countObjectGot = 0;
  @JsonView(Views.Public.class)
  private AddressDTO address;

  /**
   * Empty constructor.
   */
  public MemberImpl() {

  }

  @Override
  public boolean checkPassword(String password) {
    return checkpw(password, this.password);
  }

  public String hashPassword(String password) {
    return hashpw(password, gensalt());
  }

  @Override
  public Integer getIdMember() {
    return idMember;
  }

  @Override
  public void setIdMember(Integer idMember) {
    //    if (idMember <= 0) {
    //      throw new IllegalArgumentException();
    //    }
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
    if (callNumber != null && callNumber.isBlank()) {
      this.callNumber = "null";
    } else {
      this.callNumber = callNumber;
    }
  }

  @Override
  public String getReasonForConnRefusal() {
    return reasonForConnRefusal;
  }

  @Override
  public void setReasonForConnRefusal(String reasonForConnRefusal) {
    this.reasonForConnRefusal = reasonForConnRefusal;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public Boolean isAdmin() {
    return isAdmin;
  }

  @Override
  public void setAdmin(Boolean admin) {
    isAdmin = admin;
  }

  @Override
  public Integer getCountObjectNotCollected() {
    return countObjectNotCollected;
  }

  @Override
  public void setCountObjectNotCollected(Integer countObjectNotCollected) {
    this.countObjectNotCollected = countObjectNotCollected;
  }

  @Override
  public Integer getCountObjectGiven() {
    return countObjectGiven;
  }

  @Override
  public void setCountObjectGiven(Integer countObjectGiven) {
    this.countObjectGiven = countObjectGiven;
  }

  @Override
  public Integer getCountObjectGot() {
    return countObjectGot;
  }

  @Override
  public void setCountObjectGot(Integer countObjectGot) {
    this.countObjectGot = countObjectGot;
  }

  public AddressDTO getAddress() {
    return address;
  }

  public void setAddress(AddressDTO address) {
    this.address = address;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MemberImpl)) {
      return false;
    }
    MemberImpl member = (MemberImpl) o;
    return getIdMember().equals(member.getIdMember()) && getPassword().equals(member.getPassword())
        && getUsername().equals(member.getUsername()) && getLastName().equals(member.getLastName())
        && getFirstName().equals(member.getFirstName()) && getCallNumber().equals(
        member.getCallNumber()) && getReasonForConnRefusal()
        .equals(member.getReasonForConnRefusal())
        && getState().equals(member.getState()) && isAdmin.equals(member.isAdmin)
        && getCountObjectNotCollected().equals(member.getCountObjectNotCollected())
        && getCountObjectGiven().equals(member.getCountObjectGiven()) && getCountObjectGot()
        .equals(member.getCountObjectGot()) && getAddress().equals(member.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getIdMember(), getPassword(), getUsername(), getLastName(), getFirstName(),
        getCallNumber(), getReasonForConnRefusal(), getState(), isAdmin,
        getCountObjectNotCollected(),
        getCountObjectGiven(), getCountObjectGot(), getAddress());
  }
}
