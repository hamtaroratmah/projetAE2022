import {Redirect} from "../Router";
import Navbar from "../Components/Navbar";
import {getToken} from "../utils/token";

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

async function login(e) {
  e.preventDefault();
  const username = document.getElementById("usernameLogin").value;
  const password = document.getElementById("passwordLogin").value;
  const errorLogin = document.getElementById("errorText");
  errorLogin.innerHTML = "";

  if (!username) {
    errorLogin.innerHTML = "Enter a username";
  } else if (!password) {
    errorLogin.innerHTML = "Enter a password";
  }
  const request = {
    method: "POST",
    body: JSON.stringify(
        {
          username: username,
          password: password
        }
    ),
    headers: {
      "Content-Type": "application/json"
    }
  };
  let token;

  const response = await fetch("/api/auths/login", request)
  if (!response.ok) {
    response.text().then((result) => {
      errorLogin.innerHTML = `${result}`;
    })
  } else {
    token = await response.json();
    const rememberBox = document.getElementById("rememberCheckBox");
    if (rememberBox.checked) {
      window.localStorage.setItem("user", JSON.stringify(token));
    } else {
      window.sessionStorage.setItem("user", JSON.stringify(token));
    }
    window.sessionStorage.setItem("justLogged", true.toString())
    await Navbar();
    Redirect("/");
  }
}

export default LoginPage;