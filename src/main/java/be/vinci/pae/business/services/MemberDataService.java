package be.vinci.pae.business.services;


import be.vinci.pae.business.domain.businessDomain.Member;
import be.vinci.pae.business.domain.interfaces.DomainFactory;
import be.vinci.pae.business.domain.interfaces.MemberDTO;
import be.vinci.pae.business.utils.Config;
import be.vinci.pae.dal.MemberDao;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;

public class MemberDataService {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private DomainFactory domainFactory;
  @Inject
  private MemberDao memberDao = new MemberDao();

  public MemberDTO getOne(String login) {
    return memberDao.getMember(login);
  }

  public ObjectNode login(String username, String password) {
    Member member = new Member(getOne(username));
    System.out.println("password = " + password);
    if (!member.checkPassword(password)) {
      return null;
    }
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("member", member.getMember().getIdMember()).sign(this.jwtAlgorithm);
      return jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", member.getMember().getIdMember())
          .put("username", member.getMember().getUsername());

    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }

}