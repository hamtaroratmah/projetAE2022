package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.business.ucc.OfferUCC;
import be.vinci.pae.dal.OfferDao;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.utils.Config;
import java.sql.SQLException;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

@TestInstance(Lifecycle.PER_CLASS)
public class MemberTest {

  private DalServices dalServices;

  private MemberUCC memberUCC;
  private Member member;
  private MemberDao memberDao;

  private ItemUCC itemUCC;
  private ItemDTO itemDTO;
  private ItemDao itemDao;
  private List<ItemDTO> items;

  private OfferUCC offerUCC;
  private OfferDTO offerDTO;
  private OfferDao offerDao;

  @BeforeAll
  void initAll() {
    Config.load("dev.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    dalServices = locator.getService(DalServices.class);

    memberUCC = locator.getService(MemberUCC.class);
    memberDao = locator.getService(MemberDao.class);
    member = locator.getService(Member.class);

    Mockito.when(member.getIdMember()).thenReturn(1);
    Mockito.when(member.getPassword()).thenReturn("password");
    Mockito.when(member.getUsername()).thenReturn("username");
    Mockito.when(member.getState()).thenReturn("valid");
    Mockito.when(member.checkPassword(member.getPassword())).thenReturn(true);
    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(member);
    try {
      Mockito.when(memberDao.getMember(member.getIdMember())).thenReturn(member);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @DisplayName("Test méthode getOne avec id de member existant")
  @Test
  public void getOne1() {
    int id = member.getIdMember();
    assertAll(
        () -> assertEquals(member, memberUCC.getOne(id)),
        () -> Mockito.verify(memberDao).getMember(id)
    );
  }

  @DisplayName("Test méthode getOne avec id de member non existant")
  @Test
  public void getOne2() {
    assertAll(
        () -> assertNull(memberUCC.getOne(5)),
        () -> Mockito.verify(memberDao).getMember(5)
    );
  }

  @DisplayName("Test méthode login member existant et valid")
  @Test
  public void login1() {
    String username = member.getUsername();
    String password = member.getPassword();
    assertAll(
        () -> assertEquals(member, memberUCC.login(username, password)),
        () -> Mockito.verify(memberDao).getMemberByUsername(username),
        () -> Mockito.verify(member).checkPassword(password)
    );
  }

  @DisplayName("Test méthode login member existant mais pending")
  @Test
  public void login2() {
    Mockito.when(member.getState()).thenReturn("pending");
    String username = member.getUsername();
    String password = member.getPassword();
    assertAll(
        () -> assertThrows(FatalException.class, () -> memberUCC.login(username, password)),
        () -> Mockito.verify(memberDao).getMemberByUsername(username),
        () -> Mockito.verify(member, Mockito.times(0)).checkPassword(password)
    );
  }

  @DisplayName("Test méthode login member existant mais denied")
  @Test
  public void login3() {
    Mockito.when(member.getState()).thenReturn("denied");
    String username = member.getUsername();
    String password = member.getPassword();
    assertAll(
        () -> assertThrows(FatalException.class, () -> memberUCC.login(username, password)),
        () -> Mockito.verify(memberDao).getMemberByUsername(username),
        () -> Mockito.verify(member, Mockito.times(0)).checkPassword(password)
    );
  }

  @DisplayName("Test méthode login member pas existant")
  @Test
  public void login4() {
    String badUsername = "badUsername";
    String badPassword = "badPassword";
    assertAll(
        () -> assertThrows(FatalException.class, () -> memberUCC.login(badUsername, badPassword)),
        () -> Mockito.verify(memberDao).getMemberByUsername(badUsername),
        () -> Mockito.verify(member, Mockito.times(0)).checkPassword(badPassword)
    );
  }

  @DisplayName("Test méthode confirmRegistration")
  @Test
  public void confirmRegistration() {

  }

  @DisplayName("Test méthode denyRegistration")
  @Test
  public void denyRegistration() {

  }

  @DisplayName("Test méthode listPendingUsers")
  @Test
  public void listPendingUsers() {

  }

  @DisplayName("Test méthode listDeniedUsers")
  @Test
  public void listDeniedUsers() {

  }

  @DisplayName("Test méthode register")
  @Test
  public void register() {
    assertAll(
        () -> assertEquals(member, memberUCC.register(member)),
        () -> Mockito.verify(member, Mockito.times(2)).hashPassword(member.getPassword()),
        () -> Mockito.verify(memberDao).insertMember(member)
    );
  }


  @DisplayName("Test méthode getOneByUsername")
  @Test
  public void getOneLogin() {
    assertAll(
        () -> assertEquals(member, memberUCC.getOneByUsername(member.getUsername())),
        () -> assertNull(memberUCC.getOneByUsername("badUsername"))
    );
  }

}