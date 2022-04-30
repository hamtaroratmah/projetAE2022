import {confirmInscription} from "../api/memberApi";

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
                <input type="checkbox" id="isAdmin${i}" name="isAdmin${i}">
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
    const isAdmin = document.getElementById("isAdmin" + i);
    const buttonConfirm = document.getElementById("confirm" + i);

    buttonConfirm.addEventListener("click", async (e) => {
      e.preventDefault();
      await confirmInscription(inscriptions[i].username, isAdmin.checked);
    })
  }
}

export {displayInscriptions};