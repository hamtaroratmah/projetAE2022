import {Redirect} from "../Router";

function LogoutPage() {
  window.localStorage.removeItem("user");
  window.sessionStorage.removeItem("user");
  Redirect("/")
  window.location.reload();
}

export default LogoutPage;