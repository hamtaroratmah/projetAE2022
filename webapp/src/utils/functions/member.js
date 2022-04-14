
const error = document.querySelector("#errorText");

async function getMember(token) {
  const request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": token
    }
  };
  const response = await fetch(`/api/members/`, request);
  if (!response.ok) {
    const error = document.getElementById("errorText");
    error.innerText = `Error while fetching username`;
  }
  return await response.json();
}

// get the list of inscriptions
async function getListInscriptions(){
  const request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json"
    }
  };

  let inscriptions = [];

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
  return inscriptions;
}
// display the list of inscriptions
function displayInscriptions(inscriptions) {
  const listInscriptionsPage = document.querySelector("#listInscriptionsPage");
  for (let i = 0; i < inscriptions.length; i++) {
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
         <form id="ConfirmForm">
                <input type="submit" value="Confirmer inscription">
         </form>
        </div>
        <div  class="receptionInscriptionChild">
        <form id="DenyForm">
                <input type="submit" value="X">
        </form>
        </div>
     </div>
      `;
    }
}

export {
  getMember,
  getListInscriptions,
  displayInscriptions
};