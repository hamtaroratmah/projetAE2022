import "./stylesheets/main.css"; // If you prefer to style your app with vanilla CSS rather than with Bootstrap

import Navbar from "./Components/Navbar";
import Footer from "./Components/Footer";
import {Router} from "./Router";

const unloggedPage = [
  "/login",
  "/register",
  "/logout"
];

const adminPage = [
  "/admin_page"
]
Navbar();

Router(); // The router will automatically load the root page

Footer();