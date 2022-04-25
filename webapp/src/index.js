// If you prefer to style your app with vanilla CSS rather than with Bootstrap
import "./stylesheets/main.css";
import {Router} from "./Router";
import Navbar from "./Components/Navbar";
import {getToken} from "./utils/utils";
import {verifyToken} from "./utils/api/member";

getToken() ? verifyToken() : console.log("Aucun token à vérifier");
Navbar();
Router(); // The router will automatically load the root page
