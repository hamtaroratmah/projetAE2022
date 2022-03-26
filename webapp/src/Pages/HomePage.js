const receptionDiv = `
  <button id="typeSortedButtonASC" class="sortedButton">Type ASC</button>
  <button id="typeSortedButtonDESC" class="sortedButton">Type DESC</button>
  <button id="dateSortedButtonASC" class="sortedButton">Date ASC</button>
  <button id="dateSortedButtonDESC" class="sortedButton">Date DESC</button>
  <select name="itemCondition" id="selectItemConditionList" class="sortedList sortedButton">
    <option selected="yes" value="default">Etat de l'objet</option>
    <option value="published">Publié</option>
    <option value="interestShown">Intérêt marqué</option>
    <option value="assigned">assigné</option>
    <option value="given">Donné</option>
    <option value="canceled">Annulé</option>
  </select>
  <div id="receptionPage">
    
  </div>
`
//todo trier par type, par date et par état d'un objet
const HomePage = async () => {

  const pageDiv = document.querySelector("#page");
  const error = document.getElementById("errorText");
  pageDiv.innerHTML = receptionDiv;

  let items = [];
  try {

    const request = {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    };

    await fetch("/api/item/", request)
    .then(response => response.json())
    .then((commits) => {
      for (let i = 0; i < commits.length; i++) {
        items.push(commits[i]);
      }
    })
    .catch(() =>
        error.innerHTML = "Une erreur est survenue"
            + " durant la récupération des objets"
    );

    displayItems(items);

  } catch (e) {
    console.error("Home page error", e);
  }

  //Sort part
  const typeButtonASC = document.querySelector("#typeSortedButtonASC");
  typeButtonASC.addEventListener("click", () => {
    items.sort((a, b) => {
      return b["type"].idType - a["type"].idType;

    });
    displayItems(items);
  });
  const typeButtonDESC = document.querySelector("#typeSortedButtonDESC");
  typeButtonDESC.addEventListener("click", () => {
    items.sort((a, b) => {
      return a["type"].idType - b["type"].idType;

    });
    displayItems(items);
  });
  const dateButtonASC = document.querySelector("#dateSortedButtonASC");
  dateButtonASC.addEventListener("click", () => {
    items.sort((a, b) => {
      let dateA = reformateDate(a["offer"].dateOffer);
      let dateB = reformateDate(b["offer"].dateOffer);
      if (dateA < dateB) {
        return -1;
      }
      if (dateA > dateB) {
        return 1;
      }
      return 0;
    })
    displayItems(items);
  });
  const dateButtonDESC = document.querySelector("#dateSortedButtonDESC");
  dateButtonDESC.addEventListener("click", () => {
    items.sort((a, b) => {
      let dateA = reformateDate(a["offer"].dateOffer);
      let dateB = reformateDate(b["offer"].dateOffer);
      if (dateA < dateB) {
        return 1;
      }
      if (dateA > dateB) {
        return -1;
      }
      return 0;
    })
    displayItems(items);
  });

  const selectItemCondition = document.querySelector(
      "#selectItemConditionList");
  selectItemCondition.addEventListener("change", () => {
    let value = selectItemCondition.options[selectItemCondition.selectedIndex].value;
    items.sort((a, b) => {
      if (a.itemCondition === value && b.itemCondition !== value) {
        return -1;
      }
      if (a.itemCondition !== value && b.itemCondition === value) {
        return 1;
      }
      return 0;
    });
    displayItems(items);
  })
};

function displayItems(items) {
  let item, offer;
  const receptionPage = document.querySelector("#receptionPage")
  receptionPage.innerHTML = ""
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
          <p id="receptionDate">${reformateDate(item["offer"].dateOffer)}</p>
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

  function openItemModal(item, j) {
    const modal = document.querySelector("modal");
    modal.innerHTML = `
      <div>
        <img src="" alt="" class="receptionImage" id="receptionImage${j}">
          <p class="receptionDescription">${item.description}</p>
          <p class="receptionOfferingMember">${item["offeringMember"].username}</p>
          <p class="receptionType">${item["type"].type}</p>
          <p class="modalItemInfo"></p>
      </div>
    `
    const photoSrc = document.querySelector("#receptionImage" + j);
    if (items[j]["photo"] === null) {
      photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
    } else {
      photoSrc.src = items[j]["photo"];
    }
  }

}

function reformateDate(date) {
  let stringDate = date.toString();
  stringDate.replace(",", "/");
  return new Date(date[0], date[1], date[2]);
}

export default HomePage;