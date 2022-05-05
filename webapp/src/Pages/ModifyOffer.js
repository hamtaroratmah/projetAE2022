import {modifyOfferFunction} from "../utils/api/items";
import {getToken} from "../utils/utils";
import {Redirect} from "../Router";

const createDiv = `
        <div id="modifyOfferPage">
            <div id="modifyOfferContainer">
                <form id="modifyOfferForm" class="ItemContainer">
                    <h1 id="newItemText">modify an Item</h1>
                    <h1 class="newItemText">Donnamis</h1>
                    <input class="inputForm fields" type="text" id="type" placeholder="type">
                    <input class="inputForm fields" type="text" id="photo" placeholder="URL de la photo">
                    <input class="inputForm fields" type="text" id="description" placeholder="Description">
                    <input class="inputForm fields" type="text" id="availabilities" placeholder="Disponibilités">
                    <input class="inputForm submitButton" type="submit" id="modifyOfferSubmitButton" value="Modifier un objet">
                </form>
            </div>
        </div>
`;

function ModifyOffer() {

  if (!getToken()) {
    Redirect("/");
    window.location.reload();
  }

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = createDiv;
  const form = document.getElementById("modifyOfferForm");
  form.addEventListener("submit", modifyOfferFunction);
}

export default ModifyOffer

