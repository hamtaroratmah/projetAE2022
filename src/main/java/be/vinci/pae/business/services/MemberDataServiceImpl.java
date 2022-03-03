package be.vinci.pae.business.services;


import be.vinci.pae.business.domain.interfaces.DomainFactory;
import be.vinci.pae.business.domain.interfaces.Member;
import be.vinci.pae.business.services.utils.Json;
import be.vinci.pae.business.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import java.util.List;

public class MemberDataServiceImpl implements MemberDataService {

  private static final String COLLECTION_NAME = "users";
  private static Json<Member> jsonDB = new Json<>(Member.class);
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private DomainFactory myDomainFactory;

  @Override
  public List<Member> getAll() {
    var items = jsonDB.parse(COLLECTION_NAME);
    return jsonDB.filterPublicJsonViewAsList(items);
  }


  @Override
  public Member getOne(int id) {
    var items = jsonDB.parse(COLLECTION_NAME);
    return items.stream().filter(item -> item.getId() == id).findAny().orElse(null);
  }

  @Override
  public Member getOne(String login) {
    var items = jsonDB.parse(COLLECTION_NAME);
    return items.stream().filter(item -> item.getLogin().equals(login)).findAny().orElse(null);
  }

  @Override
  public Member createOne(Member item) {
    var items = jsonDB.parse(COLLECTION_NAME);
    item.setId(nextItemId());
    items.add(item);
    jsonDB.serialize(items, COLLECTION_NAME);
    return item;
  }

  @Override
  public int nextItemId() {
    var items = jsonDB.parse(COLLECTION_NAME);
    if (items.size() == 0) {
      return 1;
    }
    return items.get(items.size() - 1).getId() + 1;
  }

  @Override
  public ObjectNode login(String login, String password) {
    Member member = getOne(login);
    if (member == null || !member.checkPassword(password)) {
      return null;
    }
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("member", member.getId()).sign(this.jwtAlgorithm);
      ObjectNode publicMember = jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", member.getId())
          .put("login", member.getLogin());
      return publicMember;

    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }

  @Override
  public ObjectNode register(Member member) {
    if (getOne(member.getLogin()) != null) // the member already exists !
    {
      return null;
    }

    member.setPassword(member.hashPassword(member.getPassword()));

    member = createOne(member); // add an id to the member and serialize it in db.json
    if (member == null) {
      return null;
    }
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("member", member.getId()).sign(this.jwtAlgorithm);
      ObjectNode publicUser = jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", member.getId())
          .put("login", member.getLogin());
      return publicUser;

    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }

}