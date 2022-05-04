// If you prefer to style your app with vanilla CSS rather than with Bootstrap
import "./stylesheets/main.css";
import {Router} from "./Router";
import Navbar from "./Components/Navbar";
import {getToken} from "./utils/utils";
import {verifyToken} from "./utils/api/member";

if (window.location.pathname !== "/login" && window.location.pathname
    !== "/register" && getToken()) {
  verifyToken().catch((err) => console.log(err));
}
Navbar();
Router(); // The router will automatically load the root page
