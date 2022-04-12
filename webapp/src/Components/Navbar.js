import {getToken} from "../utils/functions/token"
import {getMember} from "../utils/functions/member";

const Navbar = async () => {
  const navbarWrapper = document.querySelector("#navbarWrapper");
  if (window.location.pathname === "/login") {
    //todo FIXBUG navbar displayed in login page
    if (navbarWrapper.classList.contains("displayNone")) {
      console.log("bonsoir")
      window.location.reload();
    }
    navbarWrapper.classList.add("displayNone");
  } else {
    navbarWrapper.classList.remove("displayNone");
  }
  navbarWrapper.innerHTML = `
    <nav id="navbar">
       <p id="navbarTitle" data-uri="/">Donnamis</p>
       <button id="OfferNavbarButton">Offrir un objet</button>
       <button id="profileNavbarButton" data-uri="/login"></button>
       <button id="logoutButton" data-uri="/logout">deconnexion</button>
      <!--todo à ajouter dans le menu fait pour l'admin-->
<!--       <button id="seeListInscriptionsButton" data-uri="/listInscriptions"> Voir les inscriptions en -->
<!--            attendes/refusées</button>-->
<!--       <button id="registerButton" data-uri="/register">s'inscrire</button>-->
<!--       <button id="logoutButton" data-uri="/listItem">Voir tout les objets</button>-->
<!--       <button id="newItemButton" data-uri="/newItem">creer un nouvel objet</button>-->
    </nav>
  `;
  let token = getToken();
  let isConnected = token !== null;
  const profileButton = document.querySelector("#profileNavbarButton");
  if (!isConnected) {
    profileButton.innerText = "Connexion"
    const logout = document.querySelector("#logoutButton");
    logout.classList += " displayNone";
  } else {
    profileButton.innerText = "Connecté"
    profileButton.setAttribute("data-uri", "/profile");
    let member = await getMember(token);
    profileButton.innerText = member.username;
  }

};

export default Navbar;
