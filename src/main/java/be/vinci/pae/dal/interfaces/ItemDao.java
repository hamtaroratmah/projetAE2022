package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import java.util.List;

public interface ItemDao {

  List<ItemDTO> getLastOfferedItems();

  int cancelAnOffer(int itemId);

  List<ItemDTO> getGivenItems();

  ItemDTO getItem(int idItem);

  List<ItemDTO> getItemSortedBy(String sortingParam, String order);

  ItemDTO createItem(ItemDTO newItem);

  int typeExisting(String type);

  int createType(String type);

  ItemDTO modify(int idItem, String type, String photo, String description, String availabilities);

  int likeAnItem(int itemId, int memberId);

  boolean offer(int idOffer, int idItem);

}
