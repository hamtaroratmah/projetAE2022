package be.vinci.pae.api.filters;

import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Singleton
@Provider
@Authorize
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .build();


  @Override
  public void filter(ContainerRequestContext requestContext) /*throws IOException*/ {
//        String token = requestContext.getHeaderString("Authorization");
//        if (token == null) {
//            requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
//                    .entity("A token is needed to access this resource").build());
//        } else {
//            DecodedJWT decodedToken = null;
//            try {
//                decodedToken = this.jwtVerifier.verify(token);
//            } catch (Exception e) {
//                throw new TokenDecodingException(e);
//            }
//            User authenticatedUser = myUserDataService.getOne(decodedToken.getClaim("user").asInt());
//            if (authenticatedUser == null) {
//                requestContext.abortWith(Response.status(Status.FORBIDDEN)
//                        .entity("You are forbidden to access this resource").build());
//            }
//
//            requestContext.setProperty("user",
//                    myUserDataService.getOne(decodedToken.getClaim("user").asInt()));
//        }
  }

}