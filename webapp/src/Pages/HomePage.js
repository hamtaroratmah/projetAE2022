import {getToken} from "../utils/utils";
import {getItemUnordered, getOrderedItems} from "../utils/api/items";
import {displayItems} from "../utils/displayModule/items";

const receptionDiv = `
  <div id="sortingDiv">
    <select name="type" id="selectItemType" class="sortedList sortedButton">
      <option></option>
      <option value="ASC">Type ASC</option>
      <option value="DESC">Type DESC</option>
    </select>
    <select name="date_offer" id="selectItemDate" class="sortedList sortedButton">
      <option></option>
      <option value="ASC">Date ASC</option>
      <option value="DESC">Date DESC</option>
    </select>
    <select name="item_condition" id="selectItemCondition" class="sortedList sortedButton">
      <option value="default">Etat de l'objet</option>
      <option value="published">Publié</option>
      <option value="interestShown">Intérêt marqué</option>
      <option value="assigned">assigné</option>
      <option value="given">Donné</option>
      <option value="canceled">Annulé</option>
    </select>
  </div>

  <div id="receptionPage">
    
  </div>
`

const HomePage = async () => {

  //Refresh la page,
  // car le token n'est pas vérifié directement lors de la connexion
  if (window.sessionStorage.getItem("justLogged") === true.toString()) {
    window.location.reload();
    window.sessionStorage.removeItem("justLogged");
  }

  const pageDiv = document.querySelector("#page");
  document.getElementById("errorText");
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
  await displayItems(items, pageDiv);

  const typeSelect = document.querySelector("#selectItemType");
  const dateSelect = document.querySelector("#selectItemDate");
  const itemConditionSelect = document.querySelector("#selectItemCondition");
  console.log(typeSelect)
  console.log(dateSelect);
  console.log(itemConditionSelect);
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

export default HomePage;
