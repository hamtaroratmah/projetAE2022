import {cancelOffer, modifyOffer} from "../api/items";
import {getToken, reformateDate} from "../utils";
import {likeItem} from "../api/items";
import {getMember} from "../api/member";

function displayItems(items) {

  async function displayItems(items) {
    const member = await getMember(getToken());

    let item, offer;
    const receptionPage = document.querySelector("#receptionPage");
    let page = document.querySelector("#page");
    if (items.length === 0) {
      receptionPage.innerHTML = `
      <p>Aucun objet à afficher, change tes critères de recherche &#x1F9D0;</p>
    `;
    }
    for (let i = 0; i < items.length; i++) {
      offer = {
        idOffer: items[i]["offer"].idOffer,
        dateOffer: reformateDate(items[i]["offer"]["dateOffer"]),
        idItem: items[i]["offer"].idItem
      }
      item = {
        idItem: items[i].idItem,
        description: items[i].description,
        itemCondition: items[i].itemCondition,
        offeringMember: items[i]["offeringMember"],
        photo: items[i].photo,
        rating: items[i].rating,
        type: items[i].type,
        availabilities: items[i].availabilities,
        offer: offer
      }
      receptionPage.innerHTML += `
       <div class="modalItemInfo receptionItems" id="receptionItem${i}">
        <img src="" alt="" class="receptionImage${i}" id="receptionImage${i}">
          <p id="receptionDescription${i}">${item.description}</p>
          <p id="receptionOfferingMember${i}">${item["offeringMember"].username}</p>
          <p id="receptionType${i}">${item["type"].type}</p>
          <p id="receptionDate${i}">${item["offer"].dateOffer}</p>
          <p id="receptionItemCondition${i}">${item.itemCondition}</p>
          <p id="receptionAvailabilities${i}">${item.availabilities}</p>
          <p id="receptionIdOffer${i}" class="displayNone">${item["offer"].idOffer}</p>
          <div  class="modalItemInfo receptionItems" id="likeItem${i}">
          <button>Aimer l'offre</button>
          </div>

          <p class="modalItemInfo"></p>
          <div  class="modalItemInfo receptionItems" id="cancelOffer${i}">
          <button>Annuler l'offre</button>
          </div>
          <div  class="modalItemInfo receptionItems" id="modifyOffer${i}">
          <button>Modifier l'offre</button>
          <div  class="modalItemInfo receptionItems" id="rateOffer${i}">
          <button>Evaluer l'offre</button>
          </div>
      </div>
      
      `;

    }
    page += receptionPage;
    for (let j = 0; j < items.length; j++) {
      const itemDiv = document.querySelector("#receptionItem" + j);
      itemDiv.addEventListener("click", () => {
        openItemModal(items[j], j);
      });
      const cancelButton = document.querySelector("#cancelOffer" + j)
      cancelButton.addEventListener("click", () => {
        cancelOffer(items[j].idItem);
      });
      const modifyButton = document.querySelector("#modifyOffer" + j)
      modifyButton.addEventListener("click", () => {
        console.log("buttonClicked");
        const idOffer = document.querySelector(
            "#receptionIdOffer" + j).innerHTML;
        console.log(idOffer);
        modifyOffer(idOffer);
      });
      const photoSrc = document.querySelector("#receptionImage" + j);
      if (items[j]["photo"] === null) {
        photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
      } else {
        photoSrc.src = items[j]["photo"];
      }
      const likeButton = document.querySelector("#likeItem" + j);
      likeButton.addEventListener("click", () => {
        console.log(member.idMember);

        likeItem(items[j].idItem, member.idMember);
      });
      // const photoSrc = document.querySelector("#receptionImage" + j);
      // if (items[j]["photo"] === null) {
      //   photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
      // } else {
      //   photoSrc.src = items[j]["photo"];
      // }
    }
  }

  function openItemModal(item, j) {
    const modal = document.querySelector("modal");
    modal.innerHTML = `
      <div>
        <img src="" alt="" class="receptionImage" id="modalReceptionImage${j}">
          <p class="receptionDescription">${item.description}</p>
          <p class="receptionOfferingMember">${item["offeringMember"].username}</p>
          <p class="receptionType">${item["type"].type}</p>
          <p class="modalItemInfo"></p>
      </div>
    `
    //const photoSrc = document.querySelector("#receptionImage" + j);

    // if (items[j]["photo"] === null) {
    //   photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
    // } else {
    //   photoSrc.src = items[j]["photo"];
    // }
  }

}
export {displayItems}
