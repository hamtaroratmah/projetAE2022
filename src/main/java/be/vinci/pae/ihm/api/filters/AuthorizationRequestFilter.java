package be.vinci.pae.ihm.api.filters;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Singleton
@Provider
@Authorize
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .acceptExpiresAt(0)
      .build();
  @Inject
  private MemberUCC memberUCC;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    System.out.println("bonsoir @Authorize");
    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      System.out.println("A token is needed to access this resource");
      return;
    }
    DecodedJWT decodedToken;
    try {
      decodedToken = this.jwtVerifier.verify(token);
    } catch (Exception e) {
      throw new TokenDecodingException("Malformed Token");
    }
    MemberDTO authenticatedUser = memberUCC.getOne(decodedToken.getClaim("id_member").asInt());
    if (authenticatedUser == null) {
      requestContext.abortWith(Response.status(Status.FORBIDDEN)
          .entity("You are forbidden to access this resource").build());
    }
    requestContext.setProperty("user",
        memberUCC.getOne(decodedToken.getClaim("id_member").asInt()));
  }
}
