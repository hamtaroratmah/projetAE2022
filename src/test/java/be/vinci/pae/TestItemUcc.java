package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.MemberDao;
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
public class TestItemUcc {

  private DalServices dalServices;
  private ItemDao itemDao;
  private DomainFactory domainFactory;
  private ItemUCC itemUcc;
  private ItemDTO item;
  private ItemDTO adaptiveItem;

  private MemberDao memberDao;
  private MemberUCC memberUCC;
  private Member member;
  private Member adaptativeMember;

  private TypeDTO type;


  @BeforeAll
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    domainFactory = locator.getService(DomainFactory.class);
    itemDao = locator.getService(ItemDao.class);
    itemUcc = locator.getService(ItemUCC.class);
    item = domainFactory.getItem();
    adaptiveItem = Mockito.mock(ItemDTO.class);
    dalServices = locator.getService(DalServices.class);

    memberDao = locator.getService(MemberDao.class);
    memberUCC = locator.getService(MemberUCC.class);
    member = (Member) domainFactory.getMember();
    adaptativeMember = Mockito.mock(Member.class);

    type = domainFactory.getType();
  }

  @BeforeEach
  void initEach() {

    member.setUsername("username");
    member.setPassword("password");
    member.setLastName("lastName");
    member.setFirstName("firstName");
    member.setCallNumber("0000");
    member.setIdMember(1);
    member.setState("confirm");
    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(member);
    type.setType("Jouets");
    type.setIdType(4);

    item.setType(type);
    item.setOfferingMember(member);
    item.setAvailabilities("availabilities");
    item.setDescription("description");
    item.setPhoto("photo");
    item.setIdItem(1);
    item.setItemCondition("published");

  }

  @DisplayName("Test creation item champs ok")
  @Test
  public void testChampsOK() {

    assertEquals(adaptiveItem, itemUcc.createItem(item));
  }

  @DisplayName("test getItem valid id")
  @Test
  public void testGetItemValidId() {
    Mockito.when(itemDao.getItem(item.getIdItem())).thenReturn(item);
    assertEquals(item, itemUcc.getItem(item.getIdItem()));

  }

  @DisplayName("Test getItem nonexistent id")
  @Test
  public void testGetItemNonexistentId() {
    item.setIdItem(30);
    Mockito.when(itemDao.getItem(item.getIdItem())).thenReturn(null);
    assertNull(itemUcc.getItem(item.getIdItem()));

  }

  @DisplayName("Test liken an item existant ids")
  @Test
  public void testLikeItemExistantIds() {
    item.setItemCondition("published");
    Mockito.when(itemDao.getItem(item.getIdItem())).thenReturn(item);
    //Mockito.when(itemDao.cancelAnOffer(item.getIdItem())).thenReturn(1);
    assertEquals(1, itemUcc.cancelAnOffer(member.getIdMember()));
  }


}
