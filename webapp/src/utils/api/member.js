import {getToken} from "../utils";

const verifyToken = async () => {
  const request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": getToken()
    }
  };
  const response = await fetch("/api/members/", request);
  if (!response.ok) {
    response.text().catch((err) => {
      console.log("token invalid </3")
      const errorDiv = document.querySelector("#errorText");
      errorDiv.innerHTML = `${err}`
      console.log(err)
    })
  } else {
    let member = await response.json()
    console.log("token valid <3")
  }
}

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
          <form id="confirmForm">
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
                <input type="checkbox" value="isAdmin" id="isAdmin${i}">
            </div>
            <div class="receptionInscriptionGrandChild">
                <input id ="confirm${i}" type="submit" value="Confirmer inscription">
            </div>
          </form>
          </div>
            <div class="receptionInscriptionChild2">
             <form id="denyForm${i}">
                <input type="submit" value="X">
                </form>
            </div>
        </div>
      `;
      }
    for (let i = 0; i < inscriptions.length; i++) {
        // Confirm inscription
        const buttonConfirm = document.getElementById("confirm"+i);
        buttonConfirm.addEventListener("click",async (e) => {
            e.preventDefault();
            await confirmInscription(inscriptions[i].username,false);
        })
  }
}

<<<<<<< HEAD:webapp/src/utils/api/member.js
export {getMember, updateMember, verifyToken};
=======
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
    console.log(request)
    await fetch("/api/members/confirm", request)
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
>>>>>>> 4ac2fab16dd8f215f48575e14fd00361018b502c:webapp/src/utils/functions/member.js
