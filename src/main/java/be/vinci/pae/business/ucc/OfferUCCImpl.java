package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.OfferDao;
import be.vinci.pae.exceptions.BadRequestException;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.util.ArrayList;

public class OfferUCCImpl implements OfferUCC {

  @Inject
  private OfferDao offerDao;
  @Inject
  private ItemDao itemDao;


  @Inject
  private DalServices dalServices;

  public OfferUCCImpl() {

  }

  /**
   * get an offer from database according to its idOffer.
   */
  @Override
  public OfferDTO getOffer(int idOffer) {
    try {
      dalServices.startTransaction();
      OfferDTO offer = offerDao.getOffer(idOffer);
      dalServices.commitTransaction();
      if (idOffer < 1) {
        throw new BadRequestException("un id ne peut être inférieur à 0");
      }
      return offer;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
    }
    return null;
  }

  @Override
  public OfferDTO createOffer(ItemDTO item) {
    try {
      dalServices.startTransaction();
      ItemDTO newItem = itemDao.createItem(item);
      System.out.println("id : " + newItem.getIdItem());

      OfferDTO offerDTO = offerDao.createOffer(newItem);
      dalServices.commitTransaction();
      return offerDTO;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean isLiked(int idItem, int idMember) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new FatalException("L'id de l'objet doit être supérieur à 0.");
      }
      boolean isLiked = offerDao.isLiked(idItem, idMember);
      dalServices.commitTransaction();
      return isLiked;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }

  }

  @Override
  public ArrayList<MemberDTO> interests(int idItem, int idMember) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new FatalException("L'id de l'objet doit être supérieur à 0.");
      }
      ArrayList<MemberDTO> list;
      list = offerDao.interests(idItem, idMember);
      dalServices.commitTransaction();
      return list;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }

  }

  @Override
  public boolean cancel(int idItem) {
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new FatalException("L'id de l'objet doit être supérieur à 0.");
      }
      dalServices.commitTransaction();
      return offerDao.cancel(idItem);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * modify an offer.
   *
   * @param idOffer,type,photo,description,availabilities
   * @return the new item modified
   */
  public ItemDTO modify(int idOffer, String type, String photo, String description,
      String availabilities) {

    try {
      dalServices.startTransaction();
      System.out.println("ok2");

      if (idOffer < 1) {
        throw new FatalException("L'id de l'objet doit être supérieur à 0.");
      }
      int idItem = offerDao.getIdItem(idOffer);

      ItemDTO item = itemDao.modify(idItem, type, photo, description, availabilities);
      dalServices.commitTransaction();

      return item;
    } catch (Exception e) {
      System.out.println("ko1");

      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }

  public boolean offer(int idOffer, int idMember) {
    boolean given;
    try {
      System.out.println("ok2");

      dalServices.startTransaction();
      int idItem = offerDao.getIdItem(idOffer);

      if (idItem < 1) {
        throw new FatalException("L'id de l'objet doit être supérieur à 0.");
      }
      given = itemDao.offer(idItem, idMember);
      dalServices.commitTransaction();

    } catch (Exception e) {
      System.out.println("ko1");

      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
    return given;
  }

}
