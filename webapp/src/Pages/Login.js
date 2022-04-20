import {Redirect} from "../Router";
import {getToken} from "../utils/utils";
import {login} from "../utils/api/auths";

const loginDiv = `
        <div id="loginPage">
            <div id="loginContainer">
                <form id="loginForm" class="loginRegisterContainer">
                    <h1 id="loginText">Connexion</h1>
                    <h1 class="loginText">Donnamis</h1>
                    <input class="inputForm fields" type="text" id="usernameLogin" placeholder="Pseudo">
                    <input class="inputForm fields" type="password" id="passwordLogin" placeholder="Mot de passe">
                    <label id="labelRememberCheckBox"><input type="checkbox" name="rememberMe" id="rememberCheckBox" class="inputForm">Se souvenir de moi</label>
                    <input class="inputForm submitButton" type="submit" id="loginSubmitButton" value="Se connecter">
                </form>
            </div>
        </div>
`;

/**
 * Render the NewPage :
 * Just an example to demonstrate how to use the router to "redirect" to a new page
 */
function LoginPage() {
  if (getToken()) {
    Redirect("/");
    return;
  }
  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = loginDiv;
  const form = document.getElementById("loginForm");
  form.addEventListener("submit", login);
}

export default LoginPage;
