/**
 * Render the Navbar which is styled by using Bootstrap
 * Each item in the Navbar is tightly coupled with the Router configuration :
 * - the URI associated to a page shall be given in the attribute "data-uri" of the Navbar
 * - the router will show the Page associated to this URI when the user click on a nav-link
 */

const Navbar = async () => {
  const navbarWrapper = document.querySelector("#navbarWrapper");
  navbarWrapper.innerHTML = `
    <nav id="navbar">
       <p id="navbarTitle">Donnamis</p>
       <button id="OfferNavbarButton">Offrir un objet</button>
       <button id="seeListInscriptionsButton" data-uri="/listInscriptions"> Voir les inscriptions en 
            attendes/refusées</button>
       <button id="profileNavbarButton" data-uri="/login">
       <button id="logoutButton" data-uri="/logout">deconnexion</button>
       <button id="logoutButton" data-uri="/listItem">Voir tout les objets</button>
       <button id="newItemButton" data-uri="/newItem">creer un nouvel objet</button>

       
       <p id="userIdentifier"></p>
    </nav>
  `;

  let isConnected = window.localStorage.getItem("user") !== null
      || window.sessionStorage.getItem("user") !== null;

  const profileButton = document.querySelector("#profileNavbarButton");
  if (!isConnected) {
    profileButton.innerText = "Connexion"
  } else {
    profileButton.innerText = "Connecté"
    profileButton.setAttribute("data-uri", "/");
    const username = document.querySelector("#userIdentifier");
    let token;
    if (window.localStorage.getItem("user") !== null) {
      token = window.localStorage.getItem("user");
    } else if (window.sessionStorage.getItem("user") !== null) {
      token = window.sessionStorage.getItem("user");
    }
    let name = await getName(token);

    username.innerText = `Bonjour ${name}`
  }

};

async function getName(token) {
  const request = {
    method: "POST",
    body: JSON.stringify(
        {
          token: token
        }
    ),
    headers: {
      "Content-Type": "application/json"
    }
  };
  const response = await fetch(`/api/member/getMember`, request);
  if (!response.ok) {
    const error = document.getElementById("errorText");
    error.innerText = `Error while fetching username`;
  }
  return response.text();
}

export default Navbar;
