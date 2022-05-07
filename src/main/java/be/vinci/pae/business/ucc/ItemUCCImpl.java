package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.exceptions.BadRequestException;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.utils.Log;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.List;

public class ItemUCCImpl implements ItemUCC {

  @Inject
  ItemDao itemDao;
  @Inject
  private DalServices dalServices;

  public ItemUCCImpl() {
  }

  @Override
  public List<ItemDTO> getItemSortedBy(String sortingParam, String order) {
    try {
      dalServices.startTransaction();
      List<ItemDTO> list = itemDao.getItemSortedBy(sortingParam, order);
      dalServices.commitTransaction();
      return list;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }

  @Override
  public ItemDTO getItem(int idItem) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new BadRequestException("L'id de l'objet doit être supérieur à 0.");
      }
      ItemDTO item = itemDao.getItem(idItem);
      if (item == null) {
        throw new BadRequestException("L'objet désiré n'existe pas.");
      }
      dalServices.commitTransaction();
      return item;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }


  @Override
  public List<ItemDTO> getGivenItems() {
    try {
      dalServices.startTransaction();
      List<ItemDTO> items = itemDao.getGivenItems();
      dalServices.commitTransaction();
      return items;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
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
      int interests = itemDao.likeAnItem(offerId, memberId);
      dalServices.commitTransaction();
      return interests;
    } catch (Exception e) {
      dalServices.rollbackTransaction();

      e.printStackTrace();
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
  public int cancelAnOffer(int itemId) throws IOException {
    try {
      dalServices.startTransaction();
      int returned = itemDao.cancelAnOffer(itemId);
      dalServices.commitTransaction();
      return returned;
    } catch (Exception e) {
      Log log = new Log("log.txt");
      log.logger.warning("id inexistant");
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public ItemDTO createItem(ItemDTO item) {
    try {
      dalServices.startTransaction();
      ItemDTO returned = itemDao.createItem(item);
      dalServices.commitTransaction();
      return returned;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int typeExisting(String type) {
    try {
      dalServices.startTransaction();
      int typeId = itemDao.typeExisting(type);
      dalServices.commitTransaction();
      return typeId;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public int createType(String type) {
    try {
      dalServices.startTransaction();
      int typeId = itemDao.createType(type);
      dalServices.commitTransaction();
      return typeId;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return -1;
  }

  public void insertPhoto(String fileName,int idItem) {
    try {
      System.out.print("Passer par là : ItemUcc");
      dalServices.startTransaction();
      itemDao.insertPhoto(fileName, idItem);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }
}


