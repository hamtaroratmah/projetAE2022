package be.vinci.pae.ihm.api;

import jakarta.ws.rs.Path;

@Path("/member")
public class MemberResource {

}

// vérifier si le token est toujours valide ou pas

/* TODO
* éviter le json coté business
* renomé le code :
*   dataservice-> UCC ou service
*   deux interface pour une classe DTO
* remember me
* close preparedStatement, resultSet
* Injection de dépendance
* */