import {Redirect} from "../Router";

function LogoutPage() {
  window.localStorage.removeItem("user");
  Redirect("/")
}

export default LogoutPage;