package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import be.vinci.pae.exceptions.BadRequestException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.LoginException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
  private Member adaptativeMember;

  @BeforeAll
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    domainFactory = locator.getService(DomainFactory.class);
    memberDao = locator.getService(MemberDao.class);
    memberUCC = locator.getService(MemberUCC.class);
    member = (Member) domainFactory.getMember();
    adaptativeMember = Mockito.mock(Member.class);
    dalServices = locator.getService(DalServices.class);
  }

  @BeforeEach
  void initEach() {
    member.setUsername("username");
    member.setPassword("$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW");
    member.setLastName("lastName");
    member.setFirstName("firstName");
    member.setCallNumber("0000");
    member.setIdMember(1);
    member.setState("confirm");
    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(member);
  }

  @DisplayName("Test Login Password and username good")
  @Test
  public void testLoginUsernamePasswordGood() {
    Mockito.when(adaptativeMember.getState()).thenReturn("confirm");
    Mockito.when(adaptativeMember.checkPassword(member.getPassword())).thenReturn(true);
    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(adaptativeMember);
    assertEquals(adaptativeMember, memberUCC.login(member.getUsername(), member.getPassword()));
  }

  @DisplayName("Test Login Password good and username wrong")
  @Test
  public void testLoginUsernameWrongPasswordGood() {
    Mockito.when(memberDao.getMemberByUsername(member.getUsername()))
        .thenReturn(null);
    assertThrows(LoginException.class,
        () -> memberUCC.login(member.getUsername(), member.getPassword()));
  }

  @DisplayName("Test Login Password wrong and username good")
  @Test
  public void testLoginUsernameGoodPasswordWrong() {
    Mockito.when(adaptativeMember.checkPassword(member.getPassword())).thenReturn(false);

    assertThrows(LoginException.class,
        () -> memberUCC.login(member.getUsername(), member.getPassword()));
  }

  @DisplayName("Test Password wrong and username wrong")
  @Test
  public void testUsernamePasswordWrong() {
    Mockito.when(memberDao.getMemberByUsername(""))
        .thenReturn(null);
    Mockito.when(adaptativeMember.checkPassword(member.getPassword())).thenReturn(false);

    assertThrows(LoginException.class,
        () -> memberUCC.login(member.getUsername(), member.getPassword()));
  }

  @DisplayName("Test Login user denied")
  @Test
  public void testLoginUserDenied() {
    member.setState("denied");
    assertThrows(LoginException.class,
        () -> memberUCC.login(member.getUsername(), member.getPassword()));
  }

  @DisplayName("Test Login user pending")
  @Test
  public void testLoginUserPending() {
    member.setState("pending");
    assertThrows(LoginException.class,
        () -> memberUCC.login(member.getUsername(), member.getPassword()));
  }

  ///////////////////////////////////////////////////////////////////////////////////

  @DisplayName("Test getOne valid id")
  @Test
  public void testGetOneValidId() {
    Mockito.when(memberDao.getMember(member.getIdMember())).thenReturn(member);
    assertEquals(member, memberUCC.getOne(member.getIdMember()));
  }

  @DisplayName("Test getOne nonexistent id")
  @Test
  public void testGetOneNonexistentId() {
    member.setIdMember(10);
    Mockito.when(memberDao.getMember(member.getIdMember())).thenReturn(null);
    assertNull(memberUCC.getOne(member.getIdMember()));
  }

  @DisplayName("Test getOne negative id")
  @Test
  public void testGetOneNegativeId() {
    member.setIdMember(-1);
    assertThrows(BadRequestException.class, () -> memberUCC.getOne(member.getIdMember()));
  }

  ///////////////////////////////////////////////////////////////////////////////////

  private Member initNewMemberUpdate() {
    Member newMember = Mockito.mock(Member.class);
    Mockito.when(newMember.getUsername()).thenReturn("newUsername");
    Mockito.when(newMember.getPassword()).thenReturn("newPassword");
    Mockito.when(newMember.getLastName()).thenReturn("newLastName");
    Mockito.when(newMember.getFirstName()).thenReturn("newFirstName");
    Mockito.when(newMember.getCallNumber()).thenReturn("+32454948595");
    Mockito.when(newMember.getIdMember()).thenReturn(1);
    Mockito.when(newMember.getState()).thenReturn("confirm");
    //    newMember.setUsername("newUsername");
    //    newMember.setPassword("newPassword");
    //    newMember.setLastName("newLastName");
    //    newMember.setFirstName("newFirstName");
    //    newMember.setCallNumber("+32454948595");
    //    newMember.setIdMember(1);
    //    newMember.setState("confirm");
    Mockito.when(memberDao.getMemberByUsername(newMember.getUsername())).thenReturn(newMember);
    return newMember;
  }

  @DisplayName("Test update existent member")
  @Test
  public void testUpdateExistentMember() {
    Member newMember = initNewMemberUpdate();
    Mockito.when(memberDao.updateMember(member, newMember)).thenReturn(newMember);
    assertAll(
        () -> assertEquals(newMember.getUsername(),
            memberUCC.updateMember(member, newMember).getUsername()),
        () -> assertEquals(newMember.getPassword(),
            memberUCC.updateMember(member, newMember).getPassword()),
        () -> assertEquals(newMember.getCallNumber(),
            memberUCC.updateMember(member, newMember).getCallNumber()),
        () -> assertEquals(newMember.getFirstName(),
            memberUCC.updateMember(member, newMember).getFirstName()),
        () -> assertEquals(newMember.getLastName(),
            memberUCC.updateMember(member, newMember).getLastName())
    );
  }

  @DisplayName("Test update member sql exception")
  @Test
  public void testUpdateMemberSqlException() {
    Member newMember = initNewMemberUpdate();
    Mockito.when(memberDao.updateMember(member, newMember)).thenThrow(FatalException.class);
    assertThrows(FatalException.class, () -> memberUCC.updateMember(member, newMember));
  }

  @DisplayName("test Update Existent Member But Only Username Changed")
  @Test
  public void testUpdateExistentMemberButOnlyUsernameChanged() {
    Member newMember = initNewMemberUpdate();
    Mockito.when(newMember.getPassword())
        .thenReturn("$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW");
    Mockito.when(newMember.getLastName()).thenReturn("lastName");
    Mockito.when(newMember.getFirstName()).thenReturn("firstName");
    Mockito.when(newMember.getCallNumber()).thenReturn("0000");
    Mockito.when(memberDao.updateMember(member, newMember)).thenReturn(newMember);
    assertAll(
        () -> assertEquals(newMember.getUsername(),
            memberUCC.updateMember(member, newMember).getUsername()),
        () -> assertEquals(member.getPassword(),
            memberUCC.updateMember(member, newMember).getPassword()),
        () -> assertEquals(member.getCallNumber(),
            memberUCC.updateMember(member, newMember).getCallNumber()),
        () -> assertEquals(member.getFirstName(),
            memberUCC.updateMember(member, newMember).getFirstName()),
        () -> assertEquals(member.getLastName(),
            memberUCC.updateMember(member, newMember).getLastName())
    );
  }

  ///////////////////////////////////////////////////////////////////////////////////

  @DisplayName("Test getState")
  @Test
  public void testGetState() {
    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(member);
    assertEquals(member.getState(), memberUCC.getState(member.getUsername()));
  }

  ///////////////////////////////////////////////////////////////////////////////////

  private Member initNewMemberConfirmRegistration(String username, boolean admin) {
    Member newMember = Mockito.mock(Member.class);
    newMember.setUsername("newUsername");
    newMember.setAdmin(true);
    Mockito.when(memberDao.getMemberByUsername(newMember.getUsername())).thenReturn(newMember);
    return newMember;
  }

  @DisplayName("Test confirmRegistration good username admin")
  @Test
  public void testConfirmRegistrationGoodUsernameAdmin() {
    Member newMember = initNewMemberConfirmRegistration(member.getUsername(), true);
    Mockito.when(memberDao.confirmRegistration(member.getUsername(), true))
        .thenReturn(newMember);
    assertEquals(newMember, memberUCC.confirmRegistration(member.getUsername(), true));
  }

  @DisplayName("Test confirmRegistration good username non admin")
  @Test
  public void testConfirmRegistrationGoodUsernameNonAdmin() {
    Member newMember = initNewMemberConfirmRegistration(member.getUsername(), false);
    Mockito.when(memberDao.confirmRegistration(member.getUsername(), false))
        .thenReturn(newMember);
    assertEquals(newMember, memberUCC.confirmRegistration(member.getUsername(), false));
  }

  ///////////////////////////////////////////////////////////////////////////////////


}
