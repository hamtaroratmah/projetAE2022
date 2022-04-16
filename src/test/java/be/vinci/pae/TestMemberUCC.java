package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
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
  private MemberDTO member;
  private Member memberBiz;

  @BeforeAll
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    domainFactory = locator.getService(DomainFactory.class);
    memberDao = locator.getService(MemberDao.class);

    member = Mockito.mock(MemberDTO.class);
    member.setIdMember(1);
    member.setUsername("username");
    member.setPassword(memberBiz.hashPassword("password"));
    member.setState("confirm");
    Mockito.when(memberDao.getMemberByUsername(member.getUsername()))
        .thenReturn(member);
  }

  @DisplayName("Test")
  @Test
  public void testFunction() {
    assertEquals(member, memberUCC.getOneByUsername(member.getUsername()));
  }
}
