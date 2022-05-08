package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import java.util.List;

public interface ItemDao {

  int cancelAnOffer(int itemId);

  List<ItemDTO> getGivenItems();

  ItemDTO getItem(int idItem);

  List<ItemDTO> getItemSortedBy(String sortingParam, String order);

  ItemDTO createItem(ItemDTO newItem);

  int typeExisting(String type);

  int createType(String type);

  ItemDTO modify(int idItem, int type, String photo, String description, String availabilities);

  int likeAnItem(int itemId, int memberId);

  boolean offer(int idOffer, int idItem);

  void insertPhoto(String fileName, int idItem);

  boolean came(int interestId, int itemId);
}
