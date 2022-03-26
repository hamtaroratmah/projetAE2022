package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.utils.Config;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

@TestInstance(Lifecycle.PER_CLASS)
public class DemoTest {

  private Member member;
  private MemberUCC memberUCC;
  private MemberDao memberDao;
  private DalServices dalServices;

  @BeforeAll
  void initAll() {
    Config.load("dev.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    memberUCC = locator.getService(MemberUCC.class);
    memberDao = locator.getService(MemberDao.class);
    dalServices = locator.getService(DalServices.class);
    member = locator.getService(Member.class);
    Mockito.when(member.getIdMember()).thenReturn(1);
    Mockito.when(member.getPassword()).thenReturn("password");
    Mockito.when(member.getUsername()).thenReturn("username");
    Mockito.when(member.getState()).thenReturn("valid");
    Mockito.when(member.checkPassword(member.getPassword())).thenReturn(true);

    Mockito.when(memberDao.getMember(member.getUsername())).thenReturn(member);
    Mockito.when(memberDao.getMember(member.getIdMember())).thenReturn(member);
  }

  @Test
  public void getOneLogin() {
    assertAll(
        () -> assertEquals(member, memberUCC.getOne(member.getUsername())),
        () -> Mockito.verify(dalServices).startTransaction(),
        () -> Mockito.verify(dalServices).commitTransaction(),
        () -> Mockito.verify(dalServices, Mockito.times(0)).rollbackTransaction()
    );
  }

  @Test
  public void getOneId() {
    assertAll(
        () -> assertEquals(member, memberUCC.getOne(member.getIdMember())),
        () -> Mockito.verify(dalServices).startTransaction(),
        () -> Mockito.verify(dalServices).commitTransaction(),
        () -> Mockito.verify(dalServices, Mockito.times(0)).rollbackTransaction()
    );
  }

  @Test
  public void login1() {
    assertAll(
        () -> assertEquals(member, memberUCC.login(member.getUsername(), member.getPassword())),
        () -> Mockito.verify(memberDao).getMember(member.getUsername()),
        () -> Mockito.verify(member, Mockito.times(2)).checkPassword(member.getPassword())
    );
  }

  @Test
  public void login2() {
    Mockito.when(member.getState()).thenReturn("pending");
    assertAll(
        () -> assertEquals(member, memberUCC.login(member.getUsername(), member.getPassword())),
        () -> Mockito.verify(memberDao).getMember(member.getUsername()),
        () -> Mockito.verify(member, Mockito.times(2)).checkPassword(member.getPassword())
    );
  }

  @Test
  public void login3() {
    Mockito.when(member.getState()).thenReturn("denied");
    assertAll(
        () -> assertEquals(member, memberUCC.login(member.getUsername(), member.getPassword())),
        () -> Mockito.verify(memberDao).getMember(member.getUsername()),
        () -> Mockito.verify(member, Mockito.times(2)).checkPassword(member.getPassword())
    );
  }
}
