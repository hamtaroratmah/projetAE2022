import "./stylesheets/main.css"; // If you prefer to style your app with vanilla CSS rather than with Bootstrap
import {Router} from "./Router";
import Navbar from "./Components/Navbar";

Navbar();

Router(); // The router will automatically load the root page
