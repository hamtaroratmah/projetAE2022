package be.vinci.pae.ihm.api.filters;

/*
 * @author e-Baron This filter allows anonymous requests
 */

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Singleton
@Provider
@AuthorizerMemberOrQuidam
public class AuthorizerMemberOrQuidamFilter implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .build();
  @Inject
  private MemberUCC memberUCC;

  /*
   * Vérifier le token.
   */
  @Override
  public void filter(ContainerRequestContext requestContext) {
    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      return;
    }

    DecodedJWT decodedToken;
    try {
      decodedToken = this.jwtVerifier.verify(token);
    } catch (Exception e) {
      throw new WebApplicationException(e);
    }
    try {
      MemberDTO member;
      member = memberUCC.getOne(
          decodedToken.getClaim("utilisateur").asInt());

      if (member == null) {
        requestContext.abortWith(Response.status(Status.FORBIDDEN)
            .entity("You are forbidden to access this resource").build());
      }
      requestContext.setProperty("utilisateur",
          memberUCC.getOne(decodedToken.getClaim("utilisateur").asInt()));
    } catch (FatalException e) {
      e.printStackTrace();
    }


  }

}
