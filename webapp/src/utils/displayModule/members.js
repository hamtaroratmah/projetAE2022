import {
  confirmInscription,
  denyInscription,
  preclude,
  precludMember,
  unpreclude
} from "../api/memberApi";

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
    //Try catch pour pouvoir boucler sur toute la liste de membre même s'ils sont déjà refusé
    try {
      buttonDeny.addEventListener("click", async (e) => {
        e.preventDefault();
        await denyInscription(inscriptions[i].username, reasonForRefusal.value);
        console.log(reasonForRefusal.value)
      })
    } catch (e) {

    }

  }

}

function displayMembers(members) {
  const listMembersPage = document.querySelector("#listMembersPage");
  for (let i = 0; i < members.length; i++) {
    console.log("is precluded ? ")
    console.log(members[i].isPrecluded)
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
                
            </div>
          </form>
          </div>
        </div>
      `;
  }

  for (let i = 0; i < members.length; i++) {

    const buttonPreclude = document.getElementById("preclude" + i);
    const buttonUnpreclude = document.getElementById("unpreclude" + i);

    const buttonDeny = document.getElementById("deny" + i);
    const reasonForRefusal = document.getElementById("reasonRefusal" + i);
    buttonPreclude.addEventListener("click", async (e) => {
      e.preventDefault();
      console.log(members[i].idMember);
      await preclude(members[i].idMember);
    });
    buttonUnpreclude.addEventListener("click", async (e) => {
      e.preventDefault();
      await unpreclude(members[i].idMember);
    });

    buttonPreclude.addEventListener("click", async function () {
      await precludMember(members[i].idMember)
      window.location.reload()
    });

  }

}

export {displayInscriptions, displayMembers};