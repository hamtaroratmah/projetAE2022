package be.vinci.pae.dal.interfaces;

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


  int likeAnItem(int itemId, int memberId);

}
