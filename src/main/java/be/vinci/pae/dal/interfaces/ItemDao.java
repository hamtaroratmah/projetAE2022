package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import java.util.List;

public interface ItemDao {

  List<ItemDTO> getLastOfferedItems();

  int cancelAnOffer(int itemId);

  List<ItemDTO> getGivenItems();

  ItemDTO getItem(int idItem);

  ItemDTO createItem(ItemDTO item);

  int likeAnItem(int itemId, int memberId);
}
