import {displayInscriptions} from "../utils/displayModule/members";

const listInscriptionsDiv = `
        <div id="listInscriptionsPage">
        </div>
`;
const ListInscriptionsPage = async () => {

  const pageDiv = document.querySelector("#page");
  const error = document.getElementById("errorText");
  pageDiv.innerHTML = listInscriptionsDiv;

  let inscriptions = [];
  try {

    const request = {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    };
    // fill inscriptions [] with inscriptions pending
    await fetch("/api/members/listPending", request)
    .then(response => response.json())
    .then((commits) => {
      for (let i = 0; i < commits.length; i++) {
        inscriptions.push(commits[i]);
      }

    })
    .catch(() =>
        error.innerHTML = "Une erreur est survenue"
            + " durant la récupération des inscriptions"
    );

    // fill inscriptions [] with inscriptions denied
    await fetch("/api/members/listDenied", request)
    .then(response => response.json())
    .then((commits) => {
      for (let i = 0; i < commits.length; i++) {
        inscriptions.push(commits[i]);
      }

    })
    .catch(() =>
        error.innerHTML = "Une erreur est survenue"
            + " durant la récupération des inscriptions"
    );
    // display inscriptions denied and pending
    displayInscriptions(inscriptions)
    console.log(inscriptions)

  } catch (e) {
    console.error("Home page error", e);
  }

}

export default ListInscriptionsPage;