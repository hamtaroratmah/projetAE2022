package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import java.util.List;

public interface ItemUCC {

  List<ItemDTO> getLastOfferedItems();
}
