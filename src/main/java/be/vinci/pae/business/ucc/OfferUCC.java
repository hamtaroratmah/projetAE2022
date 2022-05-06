package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import java.util.ArrayList;

public interface OfferUCC {

  OfferDTO getOffer(int idOffer);

  OfferDTO createOffer(ItemDTO item);

  boolean isLiked(int idItem, int idMember);

  ArrayList<MemberDTO> interests(int idItem);

  boolean cancel(int idItem);

  ItemDTO modify(int idOffer, String type, String photo, String description, String availabilities);

  boolean offer(int idOffer, int idMember);


}
