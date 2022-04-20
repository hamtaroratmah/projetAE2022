import Navbar from "../../Components/Navbar";
import {Redirect} from "../../Router";

async function login(e) {
  e.preventDefault();
  const username = document.getElementById("usernameLogin").value;
  const password = document.getElementById("passwordLogin").value;
  const errorLogin = document.getElementById("errorText");
  errorLogin.innerHTML = "";
  console.log("ici")
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

export {login}