const receptionDiv = `
  <button id="typeSortedButton" class="sortedButton">Type</button>
  <div id="receptionPage">
    
  </div>
`

const HomePage = async () => {

  const pageDiv = document.querySelector("#page");
  const error = document.getElementById("errorText");
  pageDiv.innerHTML = receptionDiv;
  const receptionPage = document.querySelector("#receptionPage")
  let orderType = "ASC";

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

      // items = commits
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
  receptionPage.innerHTML = ""
  for (let i = 0; i < items.length; i++) {
    receptionPage.innerHTML += `
        <div id="receptionItem${i}" class="receptionItems">
          <img src="" alt="" class="receptionImage" id="receptionImage${i}">
          <p class="receptionDescription">${items[i].description}</p>
          <p class="receptionOfferingMember">${items[i]["offeringMember"].username}</p>
          <p class="receptionType">${items[i]["type"].type}</p>
        </div>
      `;
    const photoSrc = document.querySelector("#receptionImage" + i);
    if (items[i]["photo"] === null) {
      photoSrc.src = "https://vignette2.wikia.nocookie.net/mariokart/images/4/4a/Blue_Fake_Item_Box.png/revision/latest?cb=20170103200344";
    } else {
      photoSrc.src = items[i]["photo"];
    }
  }
}

export default HomePage;