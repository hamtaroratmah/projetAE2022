import {getToken} from "../utils/token"

const Navbar = async () => {
  const navbarWrapper = document.querySelector("#navbarWrapper");
  navbarWrapper.innerHTML = `
    <nav id="navbar">
       <p id="navbarTitle">Donnamis</p>
       <button id="OfferNavbarButton">Offrir un objet</button>
       <button id="seeListInscriptionsButton" data-uri="/listInscriptions"> Voir les inscriptions en 
            attendes/refusées</button>
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
    let member = await getName(token);

    username.innerText = `Bonjour ${member.username}`
  }

};

async function getName(token) {
  const request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": token
    }
  };
  const response = await fetch(`/api/members/`, request);
  if (!response.ok) {
    const error = document.getElementById("errorText");
    error.innerText = `Error while fetching username`;
  }
  let member = await response.json();
  return member;
}

export default Navbar;
