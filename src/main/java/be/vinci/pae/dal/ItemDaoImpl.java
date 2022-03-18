package be.vinci.pae.dal;

import be.vinci.pae.business.domain.dtos.TypeDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

  @Inject
  DomainFactory domainFactory;
  @Inject
  DalServices services;
  @Inject
  MemberDao memberDao;

  public ItemDaoImpl() {
  }

  @Override
  public List<ItemDTO> getLastOfferedItems() {
    List<ItemDTO> items = null;
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT id_item, type, description, availabilities,"
            + " item_condition, photo, rating, id_offering_member "
            + "INTO pae.items")) {
      items = getItemFromDataBase(query);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return items;
  }

  private List<ItemDTO> getItemFromDataBase(PreparedStatement query) throws SQLException {
    ItemDTO item = domainFactory.getItem();
    List<ItemDTO> items = new ArrayList<>();
    ResultSet resultSetMember = query.executeQuery();

    while (resultSetMember.next()) {
      item.setIdItem(resultSetMember.getInt(1));
      item.setType(getTypeFromDataBase(resultSetMember.getInt(2)));
      item.setDescription(resultSetMember.getString(3));
      item.setAvailabilities(resultSetMember.getString(4));
      item.setItemCondition(resultSetMember.getString(5));
      item.setPhoto(resultSetMember.getString(6));
      item.setRating(resultSetMember.getInt(7));
      item.setOfferingMember(memberDao.getMember(resultSetMember.getInt(8)));
      items.add(item);
    }

    resultSetMember.close();
    return items;
  }

  private TypeDTO getTypeFromDataBase(int idType) {
    //TODO Implement method
    //select the type according to the idType
    return null;
  }

}

