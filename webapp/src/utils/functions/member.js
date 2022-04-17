import {getToken} from "./token";

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
          <div class="receptionInscriptionChild1">
          <form id="confirmForm${i}">
            <div class="receptionInscriptionGrandChild">
              <p>
                ${inscriptions[i].username}
                ${inscriptions[i].lastName} 
                ${inscriptions[i].firstName}
              </p>
            </div>
            <div class="receptionInscriptionGrandChild">
                state : ${inscriptions[i].state}
            </div>
            <div class="receptionInscriptionGrandChild">
                <input type="checkbox" value="isAdmin" id="isAdmin${1}">
            </div>
            <div class="receptionInscriptionGrandChild">
                <input type="submit" value="Confirmer inscription">
            </div>
          </form>
          </div>
            <div  class="receptionInscriptionChild2">
             <form id="denyForm">
                <input type="submit" value="X">
                </form>
            </div>
        </div>
      `;
      const buttonConfirm = document.querySelector("#confirmForm"+i);
      buttonConfirm.addEventListener("confirm",async () => {
          await confirmInscription(inscriptions[i].username,
              document.getElementById("isAdmin"+i).checked);
      })
    }
}

async function confirmInscription(username, isAdmin){
    const request = {
        method: "PUT",
        body: JSON.stringify(
            {
                username: username,
                isAdmin : isAdmin
            }
        ),
        headers: {
            "Content-Type": "application/json"
        }
    };
    await fetch("/api/members/confirm", request).catch()
}

async function denyInscription(username){
    const request = {
        method: "PUT",
        body: JSON.stringify(
            {
                username: username,
            }
        ),
        headers: {
            "Content-Type": "application/json"
        }
    };
    await fetch("/api/members/deny", request)
}

export {
  getMember,
  getListInscriptions,
  displayInscriptions
};