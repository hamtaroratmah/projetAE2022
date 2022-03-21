package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.dal.interfaces.ItemDao;
import jakarta.inject.Inject;
import java.util.List;

public class ItemUCCImpl implements ItemUCC {

  @Inject
  ItemDao itemDao;

  public ItemUCCImpl() {
  }

  /**
   * Get items from databased, sorted by the offer's date DESC.
   */
  @Override
  public List<ItemDTO> getLastOfferedItems() {
    return itemDao.getLastOfferedItems();
  }

  @Override
  public ItemDTO getItem(int idItem) {
    return itemDao.getItem(idItem);
  }

  @Override
  public List<ItemDTO> getGivenItems() {
    return itemDao.getGivenItems();
  }


}