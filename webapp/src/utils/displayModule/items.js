<<<<<<< HEAD
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
=======
import {cancelOffer, likeItem, modifyOffer, rateItem} from "../api/itemsApi";
import {getToken, reformateDate} from "../utils";
import {getMember} from "../api/memberApi";

async function displayItems(items, receptionPage) {
  let item, offer;
  // const receptionPage = document.querySelector("#receptionPage");
  let page = document.querySelector("#page");
  if (items.length === 0) {
    receptionPage.innerHTML = `
>>>>>>> f24bee0657b2417bf5dda675eb490476c76fa94f
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
<<<<<<< HEAD

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
=======
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
      const idOffer = document.querySelector(
          "#receptionIdOffer" + j).innerHTML;
      modifyOffer(idOffer);
    });
    const photoSrc = document.querySelector("#receptionImage" + j);
    if (items[j]["photo"] === null) {
      photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
    } else {
      photoSrc.src = items[j]["photo"];
    }
    if (getToken()) {
      const member = await getMember(getToken());
      const likeButton = document.querySelector("#likeItem" + j);
      likeButton.addEventListener("click", () => {
        likeItem(items[j].idItem, member.idMember);
      });
    }
  }
  return page;
}

function openItemModal(item, j) {
  const modal = document.querySelector("#modal");
  modal.innerHTML = `
>>>>>>> f24bee0657b2417bf5dda675eb490476c76fa94f
      <div>
        <img src="" alt="" class="receptionImage" id="modalReceptionImage${j}">
          <p class="receptionDescription">${item.description}</p>
          <p class="receptionOfferingMember">${item["offeringMember"].username}</p>
          <p class="receptionType">${item["type"].type}</p>
          <p class="modalItemInfo"></p>
          <div class="" id="ratingDiv">
            <h2>Evaluer un objet</h2>
            <input id="ratingComment" type="text" placeholder="Commentaire">
            <p>Entrer une note entre 1 et 5 compris</p>
            <input id="ratingStars" type="text">
            <button id="rateItem${j}">Soumettre évaluation</button>
          </div>
          
      </div>
    `
<<<<<<< HEAD
    //const photoSrc = document.querySelector("#receptionImage" + j);

    // if (items[j]["photo"] === null) {
    //   photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
    // } else {
    //   photoSrc.src = items[j]["photo"];
    // }
  }

}
export {displayItems}
=======
  const photoSrc = document.querySelector("#receptionImage" + j);
  const ratingDiv = document.querySelector("#ratingDiv");
  const rateButton = document.querySelector("#rateItem" + j);
  if (!getToken()) {
    ratingDiv.className += " displayNone";
  }
  rateButton.addEventListener("click", async () => {
    const member = await getMember(getToken());
    const idItem = item.idItem;
    const comment = document.querySelector("#ratingComment").value;
    const stars = document.querySelector("#ratingStars").value;
    const memberId = member.idMember;
    await rateItem(idItem, memberId, stars, comment);

  });
  if (item["photo"] === null) {
    console.log("pas de photo")
    photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
  } else {
    photoSrc.src = item["photo"];
  }
  // const photoSrc = document.querySelector("#receptionImage" + j);
  // if (items[j]["photo"] === null) {
  //   photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
  // } else {
  //   photoSrc.src = items[j]["photo"];
  // }
}

export {displayItems}
>>>>>>> f24bee0657b2417bf5dda675eb490476c76fa94f
