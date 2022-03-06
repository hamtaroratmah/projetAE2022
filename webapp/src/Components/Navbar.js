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
       <button id="profileNavbarButton"></button>
    </nav>
  `;

  navbarWrapper.innerHTML = navbar;

  const profileButton = document.querySelector("#profileNavbarButton");
  if (window.localStorage.length === 0) {
    profileButton.innerText = "Connexion"
  } else {
    profileButton.innerText = "Connecté"
  }
};

export default Navbar;
