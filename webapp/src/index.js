import "./stylesheets/main.css"; // If you prefer to style your app with vanilla CSS rather than with Bootstrap
import Footer from "./Components/Footer";
import {Router} from "./Router";
import Navbar from "./Components/Navbar";

const actuelRoot = window.location.pathname;
const isConnected = window.localStorage.length !== 0;

const unloggedPage = [
  "/login",
  "/register",
  "/logout"
];

const adminPage = [
  "/admin_page"
]

if (unloggedPage.findIndex(s => s === actuelRoot) < 0) {
  if (!isConnected) {
    window.location.replace(window.location.origin + "/login");
  } else {
    if (!user.is_admin && adminPage.findIndex(s => s === actuelRoot) >= 0) {
      window.location.replace(window.location.origin + "/");
    } else {
      Navbar();
    }
  }
} else if (isConnected) {
  window.location.replace(window.location.origin + "/");
}

Router(); // The router will automatically load the root page

Footer();