import {Redirect} from "../Router";
import Navbar from "../Components/Navbar";

const loginDiv = `
        <div id="loginPage">
            <div id="loginContainer">
                <form id="loginForm" class="loginRegisterContainer">
                    <h1 class="loginText">Donnamis</h1>
                    <div id="errorLogin"></div>
                    <input class="inputForm fields" type="text" id="usernameLogin" placeholder="Pseudo">
                    <input class="inputForm fields" type="password" id="passwordLogin" placeholder="Mot de passe">
                    <label><input type="checkbox" name="rememberMe" id="rememberCheckBox" class="inputForm">Se souvenir de moi</label>
                    <input class="inputForm submitButton" type="submit" value="Se connecter">
                </form>
            </div>
        </div>
`;

/**
 * Render the NewPage :
 * Just an example to demonstrate how to use the router to "redirect" to a new page
 */
function LoginPage() {
  if (window.localStorage.length !== 0 && window.localStorage.getItem(
      "user").length !== 0) {
    Redirect("/");
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
  const errorLogin = document.getElementById("errorLogin");
  errorLogin.innerHTML = "";

  //Verify the user entered all informations to log in and show an error message if not
  try {
    if (!username) {
      errorLogin.innerHTML = "Enter a username";
      throw new Error("No username");
    } else if (!password) {
      errorLogin.innerHTML = "Enter a password";
      throw new Error("No password");
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
    const response = await fetch("/api/auths/login", request);
    if (!response.ok) {
      if (response.status === 403) {
        errorLogin.innerHTML = "Wrong password";
      } else if (response.status === 404) {
        errorLogin.innerHTML = "Wrong username";
      } else {
        errorLogin.innerHTML = "Connection issue";
      }
      throw new Error(
          "fetch error : " + response.status + " : " + response.statusText);
    } else {
      errorLogin.innerHTML = "";
    }

    const token = await response.json();

    const rememberBox = document.getElementById("rememberCheckBox");
    if (rememberBox.checked) {
      window.localStorage.setItem("user", JSON.stringify(token));
    } else {
      window.sessionStorage.setItem("user", JSON.stringify(token));
    }

    Navbar();
    Redirect("/");
  } catch (e) {
    console.error("LoginPage::error ", e);
  }
}

export default LoginPage;