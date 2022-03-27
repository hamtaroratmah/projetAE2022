package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.exceptions.BizExceptionForbidden;
import be.vinci.pae.exceptions.FatalException;
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
    try {
      dalServices.startTransaction();
      return itemDao.getLastOfferedItems();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public ItemDTO getItem(int idItem) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new BizExceptionForbidden("L'id de l'objet doit être supérieur à 0.");
      }
      ItemDTO item = itemDao.getItem(idItem);
      if (item == null) {
        throw new BizExceptionForbidden("L'objet désiré n'existe pas.");
      }
      return item;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public ItemDTO createItem(ItemDTO item) {
    return itemDao.createItem(item);

  }

  @Override
  public List<ItemDTO> getGivenItems() {
    try {
      dalServices.startTransaction();
      return itemDao.getGivenItems();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    } finally {
      dalServices.commitTransaction();
    }
  }

}

