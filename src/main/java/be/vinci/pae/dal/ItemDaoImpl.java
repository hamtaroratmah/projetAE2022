package be.vinci.pae.dal;

import be.vinci.pae.business.domain.dtos.TypeDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
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
  public List<ItemDTO> getLastOfferedItems(String typeOrder) {
    List<ItemDTO> items = null;
    String tempQuery = "SELECT it.id_item,it.type,it.description,it.availabilities,"
        + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.date_offer "
        + "FROM pae.items it,pae.types ty,pae.offers of "
        + "WHERE it.type = ty.id_type AND of.id_item = it.id_item "
        + "ORDER BY date_offer DESC, "
        + "it.type " + typeOrder;
    try (PreparedStatement query = services.getPreparedStatement(tempQuery)) {
      System.out.println(query);
      items = getItemFromDataBase(query);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return items;
  }

  /*
  tri selon le type */

  private List<ItemDTO> getItemFromDataBase(PreparedStatement query) throws SQLException {

    List<ItemDTO> items = new ArrayList<>();
    ResultSet resultSet = query.executeQuery();

    while (resultSet.next()) {
      ItemDTO item = domainFactory.getItem();
      TypeDTO type = domainFactory.getType();
      type.setIdType(resultSet.getInt(2));
      type.setType(resultSet.getString(9));
      item.setIdItem(resultSet.getInt(1));
      item.setType(type);
      item.setDescription(resultSet.getString(3));
      item.setAvailabilities(resultSet.getString(4));
      item.setItemCondition(resultSet.getString(5));
      item.setPhoto(resultSet.getString(6));
      item.setRating(resultSet.getInt(7));
      MemberDTO member = memberDao.getMember(resultSet.getInt(8));
      member.setPassword(null);
      item.setOfferingMember(member);
      items.add(item);
    }

    resultSet.close();
    return items;
  }

}

