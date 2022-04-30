package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import java.util.ArrayList;

public interface OfferDao {

  OfferDTO getOffer(int idOffer);

  OfferDTO createOffer(ItemDTO newItem);

  boolean isLiked(int idItem, int idMember);

  ArrayList<MemberDTO> interests(int idItem, int idMember);

  int getIdItem(int idOffer);

  boolean cancel(int idItem);


}
