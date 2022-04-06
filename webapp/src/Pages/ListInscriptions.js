const listInscriptionsDiv = `
        <div id="listInscriptionsPage">
           
        </div>
`;
const ListInscriptionsPage = async () => {

  const pageDiv = document.querySelector("#page");
  const error = document.getElementById("errorText");
  pageDiv.innerHTML = listInscriptionsDiv;
  const listInscriptionsPage = document.querySelector("#listInscriptionsPage")

  let inscriptions = [];
  try {

    const request = {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    };
    // fill inscriptions [] with inscriptions pending
    await fetch("/api/members/pending", request)
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
    await fetch("/api/members/denied", request)
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
    DisplayInscriptions(inscriptions)

  } catch (e) {
    console.error("Home page error", e);
  }

  function DisplayInscriptions(inscriptions) {
    listInscriptionsPage.innerHTML = ""
    for (let i = 0; i < inscriptions.length; i++) {
      if (inscriptions[i].state === "Denied") {
        listInscriptionsPage.innerHTML += `
        <div id="inscriptionPending" class="receptionInscriptionParent">
          <div class="receptionInscriptionChild">
              <p>
                ${inscriptions[i].username}
                ${inscriptions[i].lastName} 
                ${inscriptions[i].firstName}
              </p>
         </div>
         <div  class="receptionInscriptionChild">
           state : ${inscriptions[i].state}
        </div>
        <div  class="receptionInscriptionChild">
          <button>Confirmer inscription</button>
        </div>
        <div  class="receptionInscriptionChild">
          <button>X</button>
        </div>
     </div>
      `;
      } else {
        listInscriptionsPage.innerHTML += `
        <div id="inscriptionPending" class="receptionInscriptionParent">
          <div class="receptionInscriptionChild">
              <p>
                ${inscriptions[i].username}
                ${inscriptions[i].lastName} 
                ${inscriptions[i].firstName}
              </p>
         </div>
         <div  class="receptionInscriptionChild">
           state : ${inscriptions[i].state}
        </div>
        <div  class="receptionInscriptionChild">
          <button>Confirmer inscription</button>
        </div>
     </div>
      `;
      }
    }
  }
}

export default ListInscriptionsPage;