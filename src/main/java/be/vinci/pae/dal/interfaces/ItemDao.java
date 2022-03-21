package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import java.util.List;

public interface ItemDao {

  List<ItemDTO> getLastOfferedItems(String typeOrder);

  ItemDTO getItem(int idItem);
}
