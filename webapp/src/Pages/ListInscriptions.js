import {getListInscriptions} from "../utils/api/member";
import {displayInscriptions} from "../utils/displayModule/members";
import {getToken} from "../utils/utils";
import {Redirect} from "../Router";

const listInscriptionsDiv = `
        <div id="listInscriptionsPage">
        </div>
`;

const ListInscriptionsPage = async () => {

  if (!getToken()) {
    Redirect("/");
    window.location.reload();
  }

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = listInscriptionsDiv;

  let inscriptions = await getListInscriptions();

  // display inscriptions denied and pending
  console.log(inscriptions)
  displayInscriptions(inscriptions)
}

export default ListInscriptionsPage;