import {getToken} from "../utils/utils";
import {getMember} from "../utils/api/memberApi";

const Navbar = async () => {
  const navbarWrapper = document.querySelector("#navbarWrapper");
  navbarWrapper.innerHTML = `
    <nav id="navbar">
       <p id="navbarTitle">Donnamis</p>
       <button id="seeListInscriptionsButton" data-uri="/listInscriptions"> Voir les inscriptions en 
            attentes/refusées</button>
       <button id="registerButton" data-uri="/register">s'inscrire</button>
       <button id="profileNavbarButton" data-uri="/login">
       <button id="logoutButton" data-uri="/logout">deconnexion</button>
       <button id="logoutButton" data-uri="/listItem">Voir tout les objets</button>
       <button id="newItemButton" data-uri="/newItem">creer un nouvel objet</button>
       <p id="userIdentifier"></p>
    </nav>
  `;
  let token = getToken();
  let isConnected = token !== null;

  const profileButton = document.querySelector("#profileNavbarButton");
  if (!isConnected) {
    profileButton.innerText = "Connexion"
  } else {
    profileButton.innerText = "Connecté"
    profileButton.setAttribute("data-uri", "/");
    const username = document.querySelector("#userIdentifier");
    let member = await getMember(token);
    username.innerText = `Bonjour ${member.username}`
  }

};

export default Navbar;
