import {createItem, UploadImage} from "../utils/api/items";

const createDiv = `
        <div id="newItemPage">
            <div id="newItemContainer">
                <form id="newItemForm" class="ItemContainer" >
                    <h1 id="newItemText">Create an Item</h1>
                    <h1 class="newItemText">Donnamis</h1>
                    <input class="inputForm fields" type="text" id="type" placeholder="type">
                    <input class="inputForm fields" type="file" id="photoInput">
                    <input class="inputForm fields" type="text" id="description" placeholder="Description">
                    <input class="inputForm fields" type="text" id="availabilities" placeholder="Disponibilités">
                    <input class="inputForm submitButton" type="submit" id="newItemSubmitButton" value="Creer un nouvel objet">
                </form>
            </div>
        </div>
`;

function NewItem() {

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = createDiv;
  const inputFile = document.getElementById("photoInput");
  const formData = new FormData();
  const form = document.getElementById("newItemForm");
  form.addEventListener("submit", createItem);
}

export default NewItem

