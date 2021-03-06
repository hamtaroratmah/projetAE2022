import {displayInterests} from "./members";
import {
  cancelOffer,
  getInterests,
  likeItem,
  modifyOffer,
  rateItem
} from "../api/itemsApi";
import {getToken, reformateDate} from "../utils";
import {getMember} from "../api/memberApi";

async function displayItems(items) {
  let item, offer;
  let receptionPage = document.querySelector("#listItems");
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
    if (!item.photo) {
      item.photo = "mario.png";
    }
    receptionPage.innerHTML += `
       <div class="modalItemInfo receptionItems" id="receptionItem${i}">
        <img src="/api/images/${item.photo}" alt="" class="receptionImage" id="receptionImage${i}">
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
       <p class="displayNone" id="precludedText${i}">Ce membre est momentanément empêché, vous ne recevrez donc pas votre objet tout de suite</p>
      `;
  }
  let member;
  if (getToken()) {
    member = await getMember(getToken());
  }
  page += receptionPage;
  for (let j = 0; j < items.length; j++) {
    const itemDiv = document.querySelector("#receptionItem" + j);
    if (items[j].itemCondition === "cancelled" || items[j].itemCondition
        === "given") {
      itemDiv.className += " displayNone";
      continue;
    }
    if (getToken()) {
      itemDiv.addEventListener("click", () => {
        openItemModal(items[j], j, member);
      });
    }
    const cancelButton = document.querySelector("#cancelOffer" + j)
    cancelButton.addEventListener("click", () => {
      cancelOffer(items[j].idItem);
    });
    const likeButton = document.querySelector("#likeItem" + j);
    const modifyButton = document.querySelector("#modifyOffer" + j)
    modifyButton.addEventListener("click", () => {
      const idOffer = document.querySelector(
          "#receptionIdOffer" + j).innerHTML;
      modifyOffer(idOffer);
    });
    if (!getToken()) {
      console.log("non connecté")
      likeButton.className += " displayNone";
      modifyButton.className += " displayNone";
      cancelButton.className += " displayNone";
    } else {
      likeButton.addEventListener("click", () => {
        likeItem(items[j].idItem, member.idMember);
      });
      if (member.idMember === items[j].offeringMember.idMember) {
        likeButton.className += " displayNone";
        (document.querySelector("#rateOffer" + j)).className += " displayNone";
      } else {
        modifyButton.className += " displayNone";
        cancelButton.className += " displayNone";
      }
      if (items[j].offeringMember.isPrecluded) {
        (document.querySelector("#precludedText" + j)).className = "";
      }
    }
  }
}

async function openItemModal(item, j, member) {
  const idItem = item.idItem;
  let interests = await getInterests(idItem);
  window.localStorage.setItem("item", item["offer"].idOffer);

  let nbreInterests = interests.length;

  const listInterestsDiv = `
        <div id="listInterestsPage">
        </div>
`;

  const modal = document.querySelector("#modal");
  if (!item.photo) {
    item.photo = "mario.png";
  }

  modal.innerHTML = `
      <div>
        <img src="/api/images/${item.photo}" alt="${item.photo}" class="receptionImage" id="modalReceptionImage${j}">
          <p class="receptionDescription">${item.description}</p>
          <p class="receptionOfferingMember">${item["offeringMember"].username}</p>
          <p class="receptionType">${item["type"].type}</p>
          <p class="interests"> Nombre d'interets : ${nbreInterests}</p>
          <p class="modalItemInfo"></p>
          <div class="" id="ratingDiv">
            <h2>Evaluer un objet</h2>
            <input id="ratingComment" type="text" placeholder="Commentaire">
            <p>Entrer une note entre 1 et 5 compris</p>
            <input id="ratingStars" type="text">
            <button id="rateItem${j}">Soumettre évaluation</button>
          </div>

      </div>
    `;
  const ratingDiv = document.querySelector("#ratingDiv");
  if (item.offeringMember.idMember === member.idMember) {
    ratingDiv.className += " displayNone";
  } else {
    const rateButton = document.querySelector("#rateItem" + j);
    if (!getToken()) {
      ratingDiv.className += " displayNone";
    }
    rateButton.addEventListener("click", async () => {
      const comment = document.querySelector("#ratingComment").value;
      const stars = document.querySelector("#ratingStars").value;
      const memberId = member.idMember;

      await rateItem(idItem, memberId, stars, comment);

    });
  }

  displayInterests(interests);
}

export {displayItems}
