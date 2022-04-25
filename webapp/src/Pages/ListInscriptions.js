import {getListInscriptions} from "../utils/api/member";
import {displayInscriptions} from "../utils/displayModule/members";

const listInscriptionsDiv = `
        <div id="listInscriptionsPage">
        </div>
`;

const ListInscriptionsPage = async () => {

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = listInscriptionsDiv;

  let inscriptions = await getListInscriptions();

  // display inscriptions denied and pending
  console.log(inscriptions)
  displayInscriptions(inscriptions)
}

export default ListInscriptionsPage;