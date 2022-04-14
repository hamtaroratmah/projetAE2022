import {
  getListInscriptions,
  displayInscriptions
} from "../utils/functions/member";

const listInscriptionsDiv = `
        <div id="listInscriptionsPage">
            
        </div>
`;

const ListInscriptionsPage = async () => {

  //Refresh la page,
  // car le token n'est pas vérifié directement lors de la connexion
  if (window.sessionStorage.getItem("justLogged") === true.toString()) {
    window.location.reload();
    window.sessionStorage.removeItem("justLogged");
  }

  const pageDiv = document.querySelector("#page");
  const error = document.getElementById("error");
  pageDiv.innerHTML = listInscriptionsDiv;
  const listInscriptionsPage = document.querySelector("#listInscriptionsPage")
  let inscriptions = await getListInscriptions();

  // display inscriptions denied and pending
  console.log(inscriptions)
  displayInscriptions(inscriptions)
}

export default ListInscriptionsPage;