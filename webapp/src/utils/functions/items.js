import {getToken} from "./token";
import {reformateDate} from "../utils";

const error = document.querySelector("#errorText");

async function getItemUnordered() {
  let request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": getToken()
    }
  };
  let items = [];
  await fetch("/api/item/getLastOfferedItemsNonConnected", request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      items.push(commits[i]);
    }
  })
  .catch(() =>
      error.innerHTML = "Une erreur est survenue durant la récupération des objets"
  );
  return items;
}

async function getOrderedItems(sortingParam, order) {
  let items = [];
  const request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": getToken()
    }
  };
  await fetch(
      "/api/item/getItemSortedBy/?sortingParam=" + sortingParam + "&order="
      + order,
      request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      items.push(commits[i]);
    }
  })
  .catch(() =>
      error.innerHTML = "Une erreur est survenue durant la récupération des objets"
  );
  return items;
}

function displayItems(items) {
  let item, offer;
  const receptionPage = document.querySelector("#receptionPage");
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
        <img src="" alt="" class="receptionImage" id="receptionImage${i}">
          <p id="receptionDescription">${item.description}</p>
          <p id="receptionOfferingMember">${item["offeringMember"].username}</p>
          <p id="receptionType">${item["type"].type}</p>
          <p id="receptionDate">${item["offer"].dateOffer}</p>
          <p id="receptionItemCondition">${item.itemCondition}</p>
          <p id="receptionAvailabilities">${item.availabilities}</p>
          <p class="modalItemInfo"></p>
      </div>
      `;

  }
  for (let j = 0; j < items.length; j++) {
    const itemDiv = document.querySelector("#receptionItem" + j);
    itemDiv.addEventListener("click", () => {
      openItemModal(items[j], j);
    });
    const photoSrc = document.querySelector("#receptionImage" + j);
    if (items[j]["photo"] === null) {
      photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
    } else {
      photoSrc.src = items[j]["photo"];
    }
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
  const photoSrc = document.querySelector("#receptionImage" + j);
  if (item["photo"] === null) {
    photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
  } else {
    photoSrc.src = item["photo"];
  }
}

export {
  getOrderedItems,
  displayItems,
  getItemUnordered
}