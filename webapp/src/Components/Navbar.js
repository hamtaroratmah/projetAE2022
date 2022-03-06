/**
 * Render the Navbar which is styled by using Bootstrap
 * Each item in the Navbar is tightly coupled with the Router configuration :
 * - the URI associated to a page shall be given in the attribute "data-uri" of the Navbar
 * - the router will show the Page associated to this URI when the user click on a nav-link
 */

const Navbar = () => {
  const navbarWrapper = document.querySelector("#navbarWrapper");
  let navbar = `
    <nav id="navbar">
       <p id="navbarTitle">Donnamis</p>
       <button id="OfferNavbarButton">Offrir un objet</button>
       <button id="profileNavbarButton" data-uri="/login">
       <button id="logoutButton" data-uri="/logout">deconnexion</button>
    </nav>
  `;

  navbarWrapper.innerHTML = navbar;

  let isConnected = window.localStorage.length !== 0;

  const profileButton = document.querySelector("#profileNavbarButton");
  if (!isConnected) {
    profileButton.innerText = "Connexion"
  } else {
    profileButton.innerText = "Connecté"
    profileButton.setAttribute("data-uri", "/");
  }

};

export default Navbar;
