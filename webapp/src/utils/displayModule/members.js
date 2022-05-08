import {
  confirmInscription,
  denyInscription,
  preclude,
  unpreclude
} from "../api/memberApi";
import {came, giveItem} from "../api/itemsApi";

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

  }
}

function displayInterests(interests) {
  const listInterestsPage = document.querySelector("#modal");
  let idOffer = window.localStorage.getItem("item");
  let recever = false

  for (let i = 0; i < interests.length; i++) {
    if (interests[i].isRecipient === true){
      recever = true;
    }
  }

  if (recever === false) {
    for (let i = 0; i < interests.length; i++) {

      listInterestsPage.innerHTML += `
        <div id="interests" class="receptionInterests">
          <div class="receptionInterests">
          <form id="interestsForm">
            <div class="receptionValidGrandChild">
              <p>
                ${interests[i].member.username}
                ${interests[i].member.lastName} 
                ${interests[i].member.firstName}
              </p>
            </div>
            <div class="receptionValidGrandChild">
                <input id ="give${i}" type="submit" value="donner a ce membre">
                
            </div>
          </form>
          </div>
        </div>
        <br>
      `;
    }
  } else{
    for (let i = 0; i < interests.length; i++) {
      if (interests[i].isRecipient === true){
        listInterestsPage.innerHTML += `
        <div id="interests" class="receptionInterests">
          <div class="receptionInterests">
          <form id="interestsForm">
            <div class="receptionValidGrandChild">
              <p>La personne que vous avez choisi comme receveur est : 
                ${interests[i].member.username}
                ${interests[i].member.lastName} 
                ${interests[i].member.firstName}
              </p>
            </div>
             
            <div class="receptionValidGrandChild">
            <br>
            <p>Est-il venue chercher son objet ? </p>
                <input id ="came${i}" type="submit" value="Oui">
                 <input id ="NotCame${i}" type="submit" value="Non">      
            </div>
          </form>
          </div>
        </div>
        <br>
      `;
      }
    }
  }
  for (let i = 0; i < interests.length; i++) {

    const buttonGive = document.getElementById("give" + i);
    const buttonCame = document.getElementById("came" + i);
    //const buttonNotCame = document.getElementById("NotCame" + i);

    buttonGive.addEventListener("click", async (e) => {
      e.preventDefault();
      await giveItem(idOffer, interests[i].idMember);
    });
    buttonCame.addEventListener("click", async (e) => {
      e.preventDefault();
      await came(interests[i].interestId, interests[i].item.idItem);
    });
  }

}

export {displayInscriptions, displayMembers, displayInterests};
