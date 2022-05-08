import {Redirect} from "../Router";
import {getToken} from "../utils/utils";
import {displayItems} from "../utils/displayModule/items";
import {getOfferedItems} from "../utils/api/itemsApi";

const listOffersDiv = `
        <div id="listItems">
        </div>
`;

const ListInscriptionsPage = async () => {

  if (!getToken()) {
    Redirect("/");
    window.location.reload();
  }

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = listOffersDiv;
  const idMember = window.sessionStorage.getItem("idOffer");
  // window.sessionStorage.removeItem("idOffer");
  const items = await getOfferedItems(idMember);
  await displayItems(items)

}

export default ListInscriptionsPage;