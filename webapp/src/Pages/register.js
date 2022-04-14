import {Redirect} from "../Router";
import Navbar from "../Components/Navbar";

const registerDiv = `
        <div id="registerPage">
            <div id="registerContainer">
                <form id="registerForm" class="loginRegisterContainer">
                    <h1 class="registerText">Donnamis</h1>
                    <div id="errorRegister"></div>
                    <input class="inputForm fields" type="text" id="username" placeholder="Pseudo">
                    <input class="inputForm fields" type="text" id="password" placeholder="Mot de passe">  
                    <input class="inputForm fields" type="text" id="lastName" placeholder="Nom">
                    <input class="inputForm fields" type="text" id="firstName" placeholder="Prénom">
                    <input class="inputForm fields" type="text" id="street" placeholder="Rue">
                    <input class="inputForm fields" type="text" id="buildingNumber" placeholder="Numéro de batiment">
                     <input class="inputForm fields" type="text" id="unitNumber" placeholder="numéro de boîte">
                     <input class="inputForm fields" type="text" id="postcode" placeholder="code postal">
                     <input class="inputForm fields" type="text" id="city" placeholder="ville">
                    <label><input type="checkbox" name="rememberMe" id="rememberCheckBox" class="inputForm">Se souvenir de moi</label>
                    <input class="inputForm submitButton" type="submit" value="S'inscrire">
                </form>
            </div>
        </div>
`;

const pageDiv = document.querySelector("#page");
pageDiv.innerHTML = registerDiv;
const form = document.getElementById("registerForm");
form.addEventListener("submit", register);

/**
 * Render the NewPage :
 * Just an example to demonstrate how to use the router to "redirect" to a new page
 */
function RegisterPage() {
  if (window.localStorage.length !== 0 && window.localStorage.getItem(
      "user").length !== 0) {
    Redirect("/");
  }
  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = registerDiv;
  const form = document.getElementById("registerForm");
  form.addEventListener("submit", register);
}

async function register(e) {
  e.preventDefault();
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const firstName = document.getElementById("firstName").value;
  const lastName = document.getElementById("lastName").value;
  const buildingNumber = document.getElementById("buildingNumber").value;
  const unitNumber = document.getElementById("unitNumber").value;
  const postcode = document.getElementById("postcode").value;
  const street = document.getElementById("street").value;
  const city = document.getElementById("city").value;
  const errorLogin = document.getElementById("errorText");
  errorLogin.innerHTML = "";

  //Verify the user entered all informations to log in and show an error message if not
  try {
    if (!username) {
      errorLogin.innerHTML = "Enter a username";
      throw new Error("No username");
    } else if (!password) {
      errorLogin.innerHTML = "Enter a password";
      throw new Error("No password");
    } else if (!firstName) {
      errorLogin.innerHTML = "Enter a first name";
      throw new Error("No first name");
    } else if (!lastName) {
      errorLogin.innerHTML = "Enter a last name";
      throw new Error("No last name");
    } else if (!buildingNumber) {
      errorLogin.innerHTML = "Enter a building number";
      throw new Error("No building number");
    } else if (!postcode) {
      errorLogin.innerHTML = "Enter a postcode";
      throw new Error("No postcode");
    } else if (!street) {
      errorLogin.innerHTML = "Enter a street";
      throw new Error("No street");
    } else if (!city) {
      errorLogin.innerHTML = "Enter a city";
      throw new Error("No city");
    }

    const request = {
      method: "POST",
      body: JSON.stringify(
          {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            unitNumber: unitNumber,
            buildingNumber: buildingNumber,
            postcode: postcode,
            street: street,
            city: city
          }
      ),
      headers: {
        "Content-Type": "application/json"
      }
    };
    console.log(request);
    const response = await fetch("/api/auths/register", request);

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
    console.error("RegisterPage::error ", e);
  }
}

export default RegisterPage;
