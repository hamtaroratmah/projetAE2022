const receptionDiv = `
  <button id="typeSortedButton" class="sortedButton">Type</button>
  <div id="receptionPage">
    
  </div>
`

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

  const typeButton = document.querySelector("#typeSortedButton")
  typeButton.addEventListener("click", () => {
    items.reverse();
    displayItems(items);
  })

};

function displayItems(items) {
  let itemDiv, item, offer;
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
    console.log(item)
    receptionPage.innerHTML += `
        <div id="receptionItem${i}" class="receptionItems">
          <img src="" alt="" class="receptionImage" id="receptionImage${i}">
          <p class="receptionDescription">${item.description}</p>
          <p class="receptionOfferingMember">${item["offeringMember"].username}</p>
          <p class="receptionType">${item["type"].type}</p>
        </div>
      `;

    itemDiv = document.querySelector("#receptionItem" + i);
    itemDiv.addEventListener("click", () => {
    });
  }
  for (let j = 0; j < items.length; j++) {
    itemDiv = document.querySelector("#receptionItem" + j);
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
  }

  function reformateDate(date) {
    let year = date[0];
    date[0] = date[2];
    date[2] = year
    return date[0] + "/" + date[1] + "/" + date[2];
  }

}

export default HomePage;