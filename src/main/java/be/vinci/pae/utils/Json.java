package be.vinci.pae.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class Json<T> {

  private static final ObjectMapper jsonMapper = new ObjectMapper();
  private final Class<T> type;

  public Json(Class<T> type) {
    this.type = type;
  }

  /**
   * Filtrer une liste de Dto.
   *
   * @param list list à filtrer
   * @return renvoie une liste filtrer
   */
  public List<T> filterPublicJsonViewAsList(List<T> list) {
    try {
      JavaType type = jsonMapper.getTypeFactory().constructCollectionType(List.class, this.type);
      // serialize using JSON Views : public view (all fields not required in the
      // views are not serialized)
      String publicItemListAsString = jsonMapper.writerWithView(Views.Public.class)
          .writeValueAsString(list);
      // deserialize using JSON Views : Public View (all fields that are not serialized
      // are set to their default values in the POJOs)
      return jsonMapper.readerWithView(Views.Public.class).forType(type)
          .readValue(publicItemListAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }

  }

  /**
   * Filtrer un objet Dto.
   *
   * @return renvoie un objet DTO filtrer
   */
  public T filterPublicJsonView(T item) {
    try {
      // serialize using JSON Views : public view (all fields not required in the
      // views are not serialized)
      String publicItemAsString = jsonMapper.writerWithView(Views.Public.class)
          .writeValueAsString(item);
      System.out.println(publicItemAsString);
      // deserialize using JSON Views : Public View (all fields that are not serialized
      // are set to their default values in the POJO)
      return jsonMapper.readerWithView(Views.Public.class).forType(type)
          .readValue(publicItemAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }

  }
}