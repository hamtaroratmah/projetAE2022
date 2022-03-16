import "./stylesheets/main.css"; // If you prefer to style your app with vanilla CSS rather than with Bootstrap
import {Router} from "./Router";
import Navbar from "./Components/Navbar";

let isConnected = window.localStorage.getItem("user") !== null
    || window.sessionStorage.getItem("user") !== null;
if (isConnected) {
  Navbar();
}

Router(); // The router will automatically load the root page
