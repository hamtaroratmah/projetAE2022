package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.LoginException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

@TestInstance(Lifecycle.PER_CLASS)
public class TestMemberUCC {

  private DalServices dalServices;
  private MemberDao memberDao;
  private DomainFactory domainFactory;
  private MemberUCC memberUCC;
  private Member member;

  @BeforeAll
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    domainFactory = locator.getService(DomainFactory.class);
    memberDao = locator.getService(MemberDao.class);
    memberUCC = locator.getService(MemberUCC.class);

    member = Mockito.mock(Member.class);
    Mockito.when(member.getUsername()).thenReturn("username");
    Mockito.when(member.getPassword()).thenReturn("password");
    Mockito.when(member.getIdMember()).thenReturn(1);
    Mockito.when(member.getState()).thenReturn("confirm");
    Mockito.when(member.checkPassword("password")).thenReturn(true);
    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(member);
  }

  @DisplayName("Test login and username good")
  @Test
  public void testUsernameLoginGood() {
    assertEquals(member, memberUCC.login(member.getUsername(), member.getPassword()));
  }

  @DisplayName("Test login good and username wrong")
  @Test
  public void testUsernameWrongLoginGood() {
    Mockito.when(memberDao.getMemberByUsername(member.getUsername()))
        .thenThrow(FatalException.class);
    assertThrows(LoginException.class,
        () -> memberUCC.login(member.getUsername(), member.getPassword()));
  }
}
