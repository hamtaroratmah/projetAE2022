package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import jakarta.inject.Inject;
import java.util.List;

public class ItemUCCImpl implements ItemUCC {

  @Inject
  ItemDao itemDao;
  @Inject
  private DalServices dalServices;

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
  public List<ItemDTO> getGivenItems() {
    List<ItemDTO> items = itemDao.getGivenItems();
    if (items.isEmpty()) {
      throw new IllegalArgumentException("Il n'y a aucun objet déjà offert.");
    }
    return items;
  }


  @Override
  public ItemDTO createItem(ItemDTO item) {
    try {
      dalServices.startTransaction();

      return itemDao.createItem(item);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    } finally {
      dalServices.commitTransaction();
    }
    return null;
  }

  @Override
  public int typeExisting(String type) {
    try {
      dalServices.startTransaction();
      return itemDao.typeExisting(type);

    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    } finally {
      dalServices.commitTransaction();
    }
    return -1;
  }

  @Override
  public int createType(String type) {
    try {
      dalServices.startTransaction();
      return itemDao.createType(type);

    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    } finally {
      dalServices.commitTransaction();
    }
    return -1;
  }


}


