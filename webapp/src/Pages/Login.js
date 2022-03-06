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
                    <input class="inputForm submitButton" type="submit" value="Se connecter">
                    <a class="loginText" id="goToRegister" type="button">Je n'ai pas encore de compte</a>
                </form>
            </div>
        </div>
`;

/**
 * Render the NewPage :
 * Just an example to demonstrate how to use the router to "redirect" to a new page
 */
function LoginPage() {
  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = loginDiv;
  const form = document.getElementById("loginForm");
  form.addEventListener("submit", login);
  // goToRegister.addEventListener("click", e => {
  //   e.preventDefault();
  //   Redirect("/register");
  // });
}

async function login(e) {
  e.preventDefault();
  const username = document.getElementById("usernameLogin").value;
  const password = document.getElementById("passwordLogin").value;
  const errorLogin = document.getElementById("errorLogin");
  errorLogin.innerHTML = "";

  console.log("username = " + username);
  console.log("password = " + password);

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
    const response = await fetch("/api/login/login", request);
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

    const user = await response.json();
    window.localStorage.setItem("user", JSON.stringify(user));
    Navbar();
    Redirect("/");
  } catch (e) {
    console.error("LoginPage::error ", e);
  }
}

export default LoginPage;