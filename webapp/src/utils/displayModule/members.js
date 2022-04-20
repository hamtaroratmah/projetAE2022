function displayInscriptions(inscriptions) {
  const listInscriptionsPage = document.querySelector("#listInscriptionsPage")
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
         <div  class="receptionInscriptionChild">
        </div>
     </div>
      `;
    }
  }
}

export {displayInscriptions};