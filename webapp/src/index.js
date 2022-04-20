// If you prefer to style your app with vanilla CSS rather than with Bootstrap
import "./stylesheets/main.css";
import {verifyToken} from "./utils/api/member";
import {getToken} from "./utils/utils";
import Navbar from "./Components/Navbar";
import {Router} from "./Router";

getToken() ? verifyToken() : console.log("Aucun token à vérifier");
Navbar().then(r => {
});
Router(); // The router will automatically load the root page
