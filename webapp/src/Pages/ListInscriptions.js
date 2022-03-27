import {Redirect} from "../Router";
import Navbar from "../Components/Navbar";

const listInscriptionsDiv = `
        <div id="listInscriptionsPage">
            <div id="ListInscriptionsButtons">
               <button id="listDeniedButton"> Liste d'inscriptions refusées</button>
               <button id="listPendingButton">Liste d'inscription en attentes</button>
           </div>
        </div>
`;
const ListInscriptionsPage = async() => {

    const pageDiv = document.querySelector("#page");
    const error = document.getElementById("errorText");
    pageDiv.innerHTML = listInscriptionsDiv;
    const ListInscriptionsPage = document.querySelector("#ListInscriptionsPage")

    let inscriptionsDenied = [];
    let inscriptionsPending = [];
    try {

        const request = {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        };
        // fill inscriptionsPending []
        await fetch("/api/member/pending", request)
            .then(response => response.json())
            .then((commits) => {
                for (let i = 0; i < commits.length; i++) {
                    inscriptionsPending.push(commits[i]);
                }

            })
            .catch(() =>
                error.innerHTML = "Une erreur est survenue"
                    + " durant la récupération des objets"
            );

        // fill inscriptionsDenied []
        await fetch("/api/member/denied", request)
            .then(response => response.json())
            .then((commits) => {
                for (let i = 0; i < commits.length; i++) {
                    inscriptionsDenied.push(commits[i]);
                }

            })
            .catch(() =>
                error.innerHTML = "Une erreur est survenue"
                    + " durant la récupération des objets"
            );
        DisplayInscriptionPending(inscriptionsPending);
    }catch (e) {
        console.error("Home page error", e);
    }

    const DisplayInscriptionDeniedButton = document.querySelector("#listDeniedButton")
    DisplayInscriptionDeniedButton.addEventListener("click", () => {
        DisplayInscriptionDenied(inscriptionsDenied);
    })

    const DisplayInscriptionPendingButton = document.querySelector("#listPendingButton")
    DisplayInscriptionDeniedButton.addEventListener("click", () => {
        DisplayInscriptionPending(inscriptionsPending);
    })

}

function DisplayInscriptionDenied(inscriptionsDenied){
    ListInscriptionsPage.innerHTML = ""
    for (let i = 0; i < inscriptionsDenied.length; i++) {
        ListInscriptionsPage.innerHTML += `
        <div id="inscriptionDenied" class="inscriptionDenied">
          <p>${inscriptionsDenied[i].username}</p>
          <p>${inscriptionsDenied[i].lastName}</p>
          <p>${inscriptionsDenied[i].firstName}</p>
          <p>${inscriptionsDenied[i].state}</p>
        </div>
      `;
    }
}

function DisplayInscriptionPending(inscriptionsPending){
    ListInscriptionsPage.innerHTML = ""
    for (let i = 0; i < inscriptionsPending.length; i++) {
        ListInscriptionsPage.innerHTML += `
        <div id="inscriptionDenied" class="inscriptionDenied">
          <p>${inscriptionsPending[i].username}</p>
          <p>${inscriptionsPending[i].lastName}</p>
          <p>${inscriptionsPending[i].firstName}</p>
          <p>${inscriptionsPending[i].state}</p>
        </div>
      `;
    }
}




export default ListInscriptionsPage;