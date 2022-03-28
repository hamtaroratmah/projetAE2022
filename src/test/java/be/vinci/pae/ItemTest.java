package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.utils.Config;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ItemTest {

  private DalServices dalServices;

  private ItemUCC itemUCC;
  private ItemDTO itemDTO;
  private ItemDao itemDao;
  private List<ItemDTO> items;

  @BeforeAll
  void initAll() {
    Config.load("dev.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    dalServices = locator.getService(DalServices.class);

    itemUCC = locator.getService(ItemUCC.class);
    itemDao = locator.getService(ItemDao.class);
    itemDTO = locator.getService(ItemDTO.class);
    Mockito.when(itemDTO.getIdItem()).thenReturn(1);
    Mockito.when(itemDao.getItem(itemDTO.getIdItem())).thenReturn(itemDTO);
    items = new ArrayList<>();
    Mockito.when(itemDao.getGivenItems()).thenReturn(items);
    Mockito.when(itemDao.getLastOfferedItems()).thenReturn(items);
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
//        () -> assertThrows(IllegalArgumentException.class, () -> itemUCC.getItem(0)),
//        () -> assertThrows(IllegalArgumentException.class, () -> itemUCC.getItem(10))
    );
  }

  @Test
  public void getGivenItems() {
//    assertThrows(FatalException.class, () -> itemUCC.getGivenItems());
    items.add(itemDTO);
    assertEquals(items, itemUCC.getGivenItems());
  }

}
