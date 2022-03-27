package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
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
  @Inject
  OfferDao offerDao;

  public ItemDaoImpl() {
  }

  @Override
  public List<ItemDTO> getLastOfferedItems() {
    List<ItemDTO> items = null;
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT it.id_item,it.type,it.description,it.availabilities,"
            + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.id_offer "
            + "FROM pae.items it,pae.types ty,pae.offers of "
            + "WHERE it.type = ty.id_type AND of.id_item = it.id_item "
            + "ORDER BY date_offer DESC")) {
      items = getItemFromDataBase(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return items;
  }

  @Override
  public ItemDTO getItem(int idItem) {
    List<ItemDTO> item = new ArrayList<>();
    try (PreparedStatement query = services.getPreparedStatement(
        "SELECT it.id_item,it.type,it.description,it.availabilities,"
            + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.date_offer "
            + "FROM pae.items it,pae.types ty,pae.offers of "
            + "WHERE it.type = ty.id_type AND of.id_item = it.id_item AND it.id_item = ? ")) {
      query.setInt(1, idItem);
      item = getItemFromDataBase(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (item.size() > 0) {
      return item.get(0);
    }
    return null;
  }

  @Override
  public ItemDTO createItem(ItemDTO newItem) {
    ItemDTO item = null;

    try (PreparedStatement query = services.getPreparedStatement(
        "INSERT (type,photo, description, availabilities, item_condition,id_offering_member)"
            + " INTO pae.items VALUES(?,?,?,?,?,?)")) {
      query.setInt(1, newItem.getType().getIdType());
      query.setString(2, newItem.getPhoto());
      query.setString(3, newItem.getDescription());
      query.setString(4, newItem.getAvailabilities());
      query.setString(5, newItem.getItemCondition());
      query.setInt(6, newItem.getOfferingMember().getIdMember());

      item = createItemInstance(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public int likeAnItem(int itemId, int memberId) {
    int interests = 7;
    String query = "INSERT INTO pae.interests (id_item, id_member) VALUES (?,?)"
        + " RETURNING id_interest";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {

      ps.setInt(1, itemId);
      ps.setInt(2, memberId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          interests = rs.getInt(1);
          return interests;
        }


      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;

  }

  @Override
  public int cancelAnOffer(int itemId) {
    ItemDTO item;
    String query =
        "UPDATE pae.items SET item_condition='cancelled' WHERE id_item=? RETURNING *";
    try (PreparedStatement ps = services.getPreparedStatement(query)) {
      ps.setInt(1, itemId);
      try (ResultSet rs = ps.executeQuery()) {
        return 1;


      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }


  @Override
  public List<ItemDTO> getGivenItems() {
    List<ItemDTO> items = null;
    String tempQuery = "SELECT it.id_item,it.type,it.description,it.availabilities,"
        + "it.item_condition,it.photo,it.rating,it.id_offering_member,ty.type,of.date_offer "
        + "FROM pae.items it,pae.types ty,pae.offers of "
        + "WHERE it.type = ty.id_type AND of.id_item = it.id_item "
        + "AND it.item_condition = 'given' " + "ORDER BY date_offer DESC ";
    try (PreparedStatement query = services.getPreparedStatement(tempQuery)) {
      items = getItemFromDataBase(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return items;
  }

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
      OfferDTO offer = offerDao.getOffer(resultSet.getInt(10));
      member.setPassword(null);
      item.setOfferingMember(member);
      item.setOffer(offer);
      items.add(item);
    }
    resultSet.close();
    return items;
  }


  private ItemDTO createItemInstance(PreparedStatement query) throws SQLException {
    ItemDTO item = domainFactory.getItem();
    ResultSet rs = query.executeQuery();
    item.setIdItem(rs.getInt(1));
    return null;
  }


}

