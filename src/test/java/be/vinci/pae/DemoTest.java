package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import be.vinci.pae.utils.Config;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class DemoTest {

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
    Mockito.when(member.checkPassword(member.getPassword())).thenReturn(true);
    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(member);
    try {
      Mockito.when(memberDao.getMember(member.getIdMember())).thenReturn(member);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    itemUCC = locator.getService(ItemUCC.class);
    itemDao = locator.getService(ItemDao.class);
    itemDTO = locator.getService(ItemDTO.class);
    Mockito.when(itemDTO.getIdItem()).thenReturn(1);
    Mockito.when(itemDao.getItem(itemDTO.getIdItem())).thenReturn(itemDTO);
    items = new ArrayList<>();
    Mockito.when(itemDao.getGivenItems()).thenReturn(items);
    Mockito.when(itemDao.getLastOfferedItems()).thenReturn(items);

    offerUCC = locator.getService(OfferUCC.class);
    offerDao = locator.getService(OfferDao.class);
    offerDTO = locator.getService(OfferDTO.class);
    Mockito.when(offerDTO.getIdOffer()).thenReturn(1);
    Mockito.when(offerDao.getOffer(offerDTO.getIdOffer())).thenReturn(offerDTO);
  }

  @DisplayName("Tests MemberUCC")
  @Test
  public void getOneLogin() {
    assertAll(
        () -> assertEquals(member, memberUCC.getOneByUsername(member.getUsername())),
        () -> assertNull(memberUCC.getOneByUsername("badUsername"))
    );
  }

  @Test
  public void getOneId() {
    assertAll(
        () -> assertEquals(member, memberUCC.getOne(member.getIdMember())),
        () -> assertNull(memberUCC.getOne(5))
    );
  }

  @Test
  public void login() {
    assertAll(
        () -> assertEquals(member, memberUCC.login(member.getUsername(), member.getPassword())),
        () -> Mockito.verify(memberDao, Mockito.times(2)).getMemberByUsername(member.getUsername()),
        () -> Mockito.verify(member, Mockito.times(3)).checkPassword(member.getPassword())
    );
  }

  @Test
  public void register() {
    assertAll(
        () -> assertEquals(member, memberUCC.register(member)),
        () -> Mockito.verify(member, Mockito.times(2)).hashPassword(member.getPassword()),
        () -> Mockito.verify(memberDao).insertMember(member)
    );
  }

  @DisplayName("Tests ItemUCC")
  @Test
  public void getLastOfferedItems() {
    assertEquals(items, itemUCC.getLastOfferedItems());
  }

  @Test
  public void getItem() {
    assertAll(
        () -> assertEquals(itemDTO, itemUCC.getItem(itemDTO.getIdItem()))
    );
  }

  @Test
  public void getGivenItems() {
    items.add(itemDTO);
    assertEquals(items, itemUCC.getGivenItems());
  }

  @DisplayName("Tests OfferUCC")
  @Test
  public void getOffer() {
    assertAll(
        () -> assertEquals(offerDTO, offerUCC.getOffer(offerDTO.getIdOffer()))
    );
  }

}