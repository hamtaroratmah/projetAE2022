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
    if (idItem < 1) {
      throw new IllegalArgumentException("L'id de l'objet doit être supérieur à 0.");
    }
    ItemDTO item = itemDao.getItem(idItem);
    if (item == null) {
      throw new IllegalArgumentException("L'objet désiré n'existe pas.");
    }
    return item;
  }

  @Override
  public ItemDTO createItem(ItemDTO item) {
   return itemDao.createItem(item);

  }

  @Override
  public List<ItemDTO> getGivenItems() {
    List<ItemDTO> items = itemDao.getGivenItems();
    if (items.isEmpty()) {
      throw new IllegalArgumentException("Il n'y a aucun objet déjà offert.");
    }
    return items;
  }

