package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import jakarta.inject.Inject;
import java.util.List;

public class ItemUCCImpl implements ItemUCC {

  @Inject
  private ItemDao itemDao;

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
      e.printStackTrace();
    } finally {
      dalServices.commitTransaction();
    }
    return null;
  }

  @Override
  public ItemDTO getItem(int idItem) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new IllegalArgumentException("L'id de l'objet doit être supérieur à 0.");
      }
      ItemDTO item = itemDao.getItem(idItem);
      if (item == null) {
        throw new IllegalArgumentException("L'objet désiré n'existe pas.");
      }
      return item;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
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
      List<ItemDTO> items = itemDao.getGivenItems();
      if (items.isEmpty()) {
        throw new IllegalArgumentException("Il n'y a aucun objet déjà offert.");
      }
      return items;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  /**
   * like an offer by its id.
   *
   * @return number of interests on this offer.
   * @params offerId and memberId
   */
  @Override
  public int likeAnItem(int offerId, int memberId) {
    try {
      dalServices.startTransaction();

      return itemDao.likeAnItem(offerId, memberId);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    } finally {
      dalServices.commitTransaction();
    }
    return -1;
  }


  /**
   * Cancel an offer.
   *
   * @return 1 if ok.
   * @params itemId
   */
  @Override
  public int cancelAnOffer(int itemId) {
    try {
      dalServices.startTransaction();
      return itemDao.cancelAnOffer(itemId);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    } finally {
      dalServices.commitTransaction();
    }
    return -1;
  }


}

