package be.vinci.pae.business.ucc;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import java.io.IOException;
import java.util.List;

public interface ItemUCC {

  List<ItemDTO> getGivenItems();

  List<ItemDTO> getItemSortedBy(String sortingParam, String order);

  ItemDTO getItem(int idItem);

  int typeExisting(String type);


  int createType(String type);


  int likeAnItem(int offerId, int memberId);

  int cancelAnOffer(int itemId) throws IOException;

  ItemDTO createItem(ItemDTO item);

  void insertPhoto(String fileName, int idItem);

  boolean came(int interestId, int itemId);
}
