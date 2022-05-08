package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.InterestDao;
import be.vinci.pae.exceptions.BadRequestException;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;

import java.util.ArrayList;

public class InterestUccImpl implements InterestUcc {

  @Inject
  private DalServices dalServices;
  @Inject
  InterestDao interestDao;

  /**
   * get an interest from database according to its idInterest.
   */
  @Override
  public InterestDTO getInterest(int idInterest) {
    InterestDTO interest = null;
    try {
      dalServices.startTransaction();
       interest = interestDao.getInterest(idInterest);
      dalServices.commitTransaction();
      if (idInterest < 1) {
        throw new BadRequestException("un id ne peut être inférieur à 0");
      }
      return interest;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
    }
    return interest;
  }

  public ArrayList<InterestDTO> listInterests(int idItem){
    try {
      dalServices.startTransaction();
      if (idItem < 1) {
        throw new FatalException("L'id de l'objet doit être supérieur à 0.");
      }
      ArrayList<InterestDTO> list;
      list = interestDao.listInterests(idItem);
      dalServices.commitTransaction();
      return list;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }
  }
  }

