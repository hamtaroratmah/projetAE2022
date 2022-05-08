import {
  confirmInscription,
  denyInscription,
  preclude,
  unpreclude
} from "../api/memberApi";
import {giveItem} from "../api/itemsApi";
import {Redirect} from "../../Router";

function displayInscriptions(inscriptions) {
  const listInscriptionsPage = document.querySelector("#listInscriptionsPage");
  for (let i = 0; i < inscriptions.length; i++) {
    if (inscriptions[i].state === "pending") {
      listInscriptionsPage.innerHTML += `
        <div id="inscriptionPending" class="receptionInscriptionPending">
          <div class="receptionConfirmForm">
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
                <input type="checkbox" id="isAdmin${i}" name="isAdmin${i}">
            </div>
            <div class="receptionInscriptionGrandChild">
                <input id ="confirm${i}" type="submit" value="Confirmer inscription">
            </div>
          </form>
          </div>
            <div class="receptionRefusalForm">
             <form id="denyForm">
                <label>Raison(s) du refus : </label>
                <input class="inputDeny" id="reasonRefusal${i}" type="text">
                <input class="inputDeny" id="deny${i}" type="submit" value="X">
                </form>
            </div>
        </div>
      `;
    } else {
      listInscriptionsPage.innerHTML += `
        <div id="inscriptionPending" class="receptionInscriptionDenied">
          <div class="receptionConfirmForm">
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
            <p id="reasonConnRefusal">raison de refus : ${inscriptions[i].reasonForConnRefusal}</p>
            <div class="receptionInscriptionGrandChild">
                <input type="checkbox" id="isAdmin${i}" name="isAdmin${i}">
            </div>
            <div class="receptionInscriptionGrandChild">
                <input id ="confirm${i}" type="submit" value="Confirmer inscription">
            </div>
          </form>
          </div>
        </div>
        `;
    }
  }
  for (let i = 0; i < inscriptions.length; i++) {
    const isAdmin = document.getElementById("isAdmin" + i);
    const buttonConfirm = document.getElementById("confirm" + i);
    const buttonDeny = document.getElementById("deny" + i);
    const reasonForRefusal = document.getElementById("reasonRefusal" + i);
    // Confirm inscription
    buttonConfirm.addEventListener("click", async (e) => {
      e.preventDefault();
      await confirmInscription(inscriptions[i].username, isAdmin.checked);
      window.location.reload();
    })

    // Deny inscription
    try {
      buttonDeny.addEventListener("click", async (e) => {
        e.preventDefault();
        await denyInscription(inscriptions[i].username, reasonForRefusal.value);
        window.location.reload();
      })
    } catch (e) {
      //Try catch pour pouvoir boucler sur toute la liste de membre même s'ils sont déjà refusé
    }
  }
}

function displayMembers(members) {
  const listMembersPage = document.querySelector("#listMembersPage");
  for (let i = 0; i < members.length; i++) {
    if (members[i].isPrecluded) {
      members[i].state = "precluded";
    }
    listMembersPage.innerHTML += `
        <div id="inscriptionValid" class="receptionInscriptionPending">
          <div class="receptionValidForm">
          <form id="validForm">
            <div class="receptionValidGrandChild">
              <p>
                ${members[i].username}
                ${members[i].lastName} 
                ${members[i].firstName}
              </p>
            </div>
            <div class="receptionValidGrandChild">
                state : ${members[i].state}
            </div>
             
            <div class="receptionValidGrandChild">
                <input id ="preclude${i}" type="submit" value="Empecher le membre">
                <input id ="unpreclude${i}" type="submit" value="Ne plus empecher le membre">
                <input id="seeItems${i}" type="submit" value="Voir objets offerts par le membre">
            </div>
          </form>
          </div>
        </div>
      `;
  }
  for (let i = 0; i < members.length; i++) {

    const buttonPreclude = document.getElementById("preclude" + i);
    buttonPreclude.addEventListener("click", async (e) => {
      e.preventDefault();
      await preclude(members[i].idMember);
    });

    const buttonUnpreclude = document.getElementById("unpreclude" + i);

    buttonUnpreclude.addEventListener("click", async (e) => {
      e.preventDefault();
      await unpreclude(members[i].idMember);
      window.location.reload();
    });

    buttonPreclude.addEventListener("click", async function () {
      await preclude(members[i].idMember)
      window.location.reload();
    });

    const listOffers = document.querySelector("#seeItems" + i);
    listOffers.addEventListener("click", function (e) {
      e.preventDefault()
      window.sessionStorage.setItem("idOffer", members[i].idMember)
      Redirect("/listOffers");
    });

  }
}

function displayInterests(members) {
  const listInterestsPage = document.querySelector("#modal");
  let idOffer = window.localStorage.getItem("item");
  for (let i = 0; i < members.length; i++) {

    listInterestsPage.innerHTML += `
        <div id="interests" class="receptionInterests">
          <div class="receptionInterests">
          <form id="interestsForm">
            <div class="receptionValidGrandChild">
              <p>
                ${members[i].username}
                ${members[i].lastName} 
                ${members[i].firstName}
              </p>
            </div>
            
             
            <div class="receptionValidGrandChild">
                <input id ="give${i}" type="submit" value="donner a ce membre">
                
            </div>
          </form>
          </div>
        </div>
      `;

  }

  for (let i = 0; i < members.length; i++) {

    const buttonGive = document.getElementById("give" + i);

    buttonGive.addEventListener("click", async (e) => {
      e.preventDefault();
      await giveItem(idOffer, members[i].idMember);
    });

  }

}

export {displayInscriptions, displayMembers, displayInterests};
