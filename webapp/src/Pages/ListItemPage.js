import {getToken} from "../utils/utils";
import {getItemUnordered, getOrderedItems} from "../utils/api/itemsApi";
import {displayItems} from "../utils/displayModule/items";
import {Redirect} from "../Router";

const receptionDiv = `
  <button id="typeSortedButtonASC" class="sortedButton">Type ASC</button>
  <button id="typeSortedButtonDESC" class="sortedButton">Type DESC</button>
  <button id="dateSortedButtonASC" class="sortedButton">Date ASC</button>
  <button id="dateSortedButtonDESC" class="sortedButton">Date DESC</button>
  <select name="itemCondition" id="selectItemConditionList" class="sortedList sortedButton">
    <option selected="yes" value="default">Etat de l'objet</option>
    <option value="published">Publié</option>
    <option value="interestShown">Intérêt marqué</option>
    <option value="assigned">assigné</option>
    <option value="given">Donné</option>
    <option value="canceled">Annulé</option>
  </select>
  <div id="receptionPage">
    
  </div>
`
const ListItemPage = async () => {

  if (!getToken()) {
    Redirect("/");
    window.location.reload();
  }

  //Refresh la page,
  // car le token n'est pas vérifié directement lors de la connexion
  if (window.sessionStorage.getItem("justLogged") === true.toString()) {
    window.location.reload();
    window.sessionStorage.removeItem("justLogged");
  }

  const pageDiv = document.querySelector("#page");
  let token = getToken();
  pageDiv.innerHTML = receptionDiv;
  let items;

  if (token) {
    items = await getOrderedItems("date_offer", "DESC")
  } else {
    const sortedDiv = document.querySelector("#sortingDiv");
    sortedDiv.style = "display: none;"
    items = await getItemUnordered()
  }
  displayItems(items);

  const typeSelect = document.querySelector("#selectItemType");
  const dateSelect = document.querySelector("#selectItemDate");
  const itemConditionSelect = document.querySelector("#selectItemCondition");
  console.log(typeSelect);
  typeSelect.addEventListener("change", async () => {
    let items = await getOrderedItems(typeSelect.name, typeSelect.value);
    displayItems(items);
  });
  dateSelect.addEventListener("change", async () => {
    let items = await getOrderedItems(dateSelect.name, dateSelect.value);
    displayItems(items);
  });
  itemConditionSelect.addEventListener("change", async () => {
    let items = await getOrderedItems(itemConditionSelect.name,
        itemConditionSelect.value);
    displayItems(items);
  });

}

export default ListItemPage;