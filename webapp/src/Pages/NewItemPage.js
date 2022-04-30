import {createItem} from "../utils/api/itemsApi";
import {getToken} from "../utils/utils";
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
                    <input class="inputForm submitButton" type="submit" id="newItemSubmitButton" value="Creer un nouvel objet">
                </form>
            </div>
        </div>
`;

function NewItem() {

  if (!getToken()) {
    Redirect("/");
    window.location.reload();
  }

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = createDiv;
  const form = document.getElementById("newItemForm");
  form.addEventListener("submit", createItem);
}

export default NewItem

