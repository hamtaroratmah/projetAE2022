import Navbar from "../Components/Navbar";
import {Redirect} from "../Router";


const createDiv = `
        <div id="newItemPage">
            <div id="newItemContainer">
                <form id="newItemForm" class="ItemContainer">
                    <h1 id="newItemText">Create an Item</h1>
                    <h1 class="newItemText">Donnamis</h1>
                    <input class="inputForm fields" type="text" id="type" placeholder="type">
                    <input class="inputForm fields" type="text" id="photo" placeholder="URL de la photo">
                    <input class="inputForm fields" type="text" id="description" placeholder="Description">
                    <input class="inputForm fields" type="text" id="availabilities" placeholder="Disponibilités">

                    <input class="inputForm fields" type="text" id="itemCondition" placeholder="Condition de l'objet">
                    <input class="inputForm submitButton" type="submit" id="newItemSubmitButton" value="Creer un nouvel objet">
                </form>
            </div>
        </div>
`;

function NewItem(){

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = createDiv;
  const form = document.getElementById("newItemForm");
  form.addEventListener("submit", createItem);
}


async function createItem(e) {
  e.preventDefault();
  const type = document.getElementById("type").value;
  const photo = document.getElementById("photo").value;
  const description = document.getElementById("description").value;
  const availabilities = document.getElementById("availabilities").value;
  const itemCondition = document.getElementById("itemCondition").value;
  const errorLogin = document.getElementById("errorText");
  // errorCreate.innerHTML = "";
  const idOfferingMember= window.localStorage;
  let idMember=4;
  // if(window.localStorage.getItem("user"))
  // idMember = window.localStorage.getItem("user") !== null
  //     || window.sessionStorage.getItem("user") !== null;
  //Verify the user entered all informations toto create an item
  // and show an error message if not
  try {
    if (!type) {
      errorCreate.innerHTML = "Choose a type";
    } else if (!description) {
      errorCreate.innerHTML = "Enter a description";

    } else if (!availabilities) {
      errorCreate.innerHTML = "Enter your availabilities";
    } else if (!itemCondition) {
      errorCreate.innerHTML = "Enter an item condition";
    }
    const request = {
      method: "POST",
      body: JSON.stringify(
          {
            type: type,
            photo: photo,
            description: description,
            availabilities: availabilities,
            itemCondition: itemCondition,
            idOfferingMember: idMember,
          }
      ),
      headers: {
        "Content-Type": "application/json"
      }
    };
    const response = await fetch("/api/item/createItem", request);
    if (!response.ok) {
      if (response.status === 403) {
        errorLogin.innerHTML = "Impossible to create a new item";
      }
    } else {
      errorLogin.innerHTML = "";
    }
    await Navbar();
    Redirect("/");
  } catch (e) {
    console.error("CreatePage::error ", e);
  }


}
export default NewItem

