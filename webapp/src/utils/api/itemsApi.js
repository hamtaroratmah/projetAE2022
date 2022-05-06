import {getToken} from "../utils";
import {getMember} from "./memberApi";
import Navbar from "../../Components/Navbar";
import {Redirect} from "../../Router";

const error = document.querySelector("#errorText");
let idOffer = -1;

async function getItemUnordered() {
  let request = {
    method: "GET", headers: {
      "Content-Type": "application/json", "Authorization": getToken()
    }
  };
  let items = [];
  await fetch("/api/items/getLastOfferedItemsNonConnected", request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      items.push(commits[i]);
    }
  })
  .catch(
      () => error.innerHTML = "Une erreur est survenue durant la récupération des objets");
  return items;
}

async function getOrderedItems(sortingParam, order) {
  let items = [];
  const request = {
    method: "GET", headers: {
      "Content-Type": "application/json", "Authorization": getToken()
    }
  };
  await fetch(
      "/api/items/getItemSortedBy/?sortingParam=" + sortingParam + "&order="
      + order, request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      items.push(commits[i]);
    }
  })
  .catch(
      () => error.innerHTML = "Une erreur est survenue durant la récupération des objets");
  return items;
}

// get the list of interests
async function getInterests(idItem) {
  const request = {
    method: "POST",
    body: JSON.stringify(
        {
          idItem: idItem
        }
    ),
    headers: {
      "Content-Type": "application/json",
      "Authorization": getToken()
    }
  };

  let members = [];
  console.log(request);
  // fill inscriptions [] with inscriptions pending
  await fetch("/api/offers/interests", request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      members.push(commits[i]);
    }

  })
  .catch(() =>
      error.innerHTML = "Une erreur est survenue"
          + " durant la récupération des membres"
  )

  return members;
}


async function modifyOfferFunction(e) {

  console.log(idOffer);
  e.preventDefault();
  //todo
  //avoir l id de l offre pour le trenasmettre dans la requete
  const type = document.getElementById("type").value;
  const photo = document.getElementById("photo").value;
  const description = document.getElementById("description").value;
  const availabilities = document.getElementById("availabilities").value;
  let member = await getMember(getToken());

  // if(window.localStorage.getItem("user"))
  // idMember = window.localStorage.getItem("user") !== null
  //     || window.sessionStorage.getItem("user") !== null;
  //Verify the user entered all informations toto create an item
  // and show an error message if not
  try {
    if (!type) {
      error.innerHTML = "Choose a type";
    } else if (!description) {
      error.innerHTML = "Enter a description";

    } else if (!availabilities) {
      error.innerHTML = "Enter your availabilities";
    }

    const request = {
      method: "POST", body: JSON.stringify({
        idOffer: idOffer,
        type: type,
        photo: photo,
        description: description,
        availabilities: availabilities,
        idOfferingMember: member.idMember,
      }), headers: {
        "Content-Type": "application/json"
      }
    };
    const response = await fetch("/api/offers/modify", request);
    console.log(response);
    console.log(request);
    if (!response.ok) {
      if (response.status === 403) {
        error.innerHTML = "Impossible to modify this item";
      }
    } else {
      error.innerHTML = "";

    }
    await Navbar();

    Redirect("/");
  } catch (e) {
    console.error("CreatePage::error ", e);
  }

}

async function createItem(e) {
  e.preventDefault();
  const type = document.getElementById("type").value;
  const photo = document.getElementById("photo").value;
  const description = document.getElementById("description").value;
  const availabilities = document.getElementById("availabilities").value;
  let member = await getMember(getToken());

  // if(window.localStorage.getItem("user"))
  // idMember = window.localStorage.getItem("user") !== null
  //     || window.sessionStorage.getItem("user") !== null;
  //Verify the user entered all informations toto create an item
  // and show an error message if not
  try {
    if (!type) {
      error.innerHTML = "Choose a type";
    } else if (!description) {
      error.innerHTML = "Enter a description";

    } else if (!availabilities) {
      error.innerHTML = "Enter your availabilities";
    }
    const request = {
      method: "POST", body: JSON.stringify({
        type: type,
        photo: photo,
        description: description,
        availabilities: availabilities,
        idOfferingMember: member.idMember,

      }), headers: {
        "Content-Type": "application/json"
      }
    };
    const response = await fetch("/api/offers/createOffer", request);
    if (!response.ok) {
      if (response.status === 403) {
        error.innerHTML = "Impossible to create a new item";
      }
    } else {
      error.innerHTML = "";
    }
    await Navbar();
    Redirect("/");
  } catch (e) {
    console.error("CreatePage::error ", e);
  }
}

async function modifyOffer(idOfferParam) {
  console.log("ok");
  console.log(idOfferParam);
  idOffer = idOfferParam;

  Redirect("/modifyOffer");
}

async function cancelOffer(idItem) {
  console.log("cancel offer " + idItem)

  const request = {
    method: "POST", body: JSON.stringify({
      idItem: idItem
    }), headers: {
      "Content-Type": "application/json", "Authorization": getToken()
    }
  };
  try {
    const response = await fetch("/api/offers/cancel", request);
    console.log(request);
    console.log(response);
    if (!response.ok) {
      if (response.status === 403) {
        "imposssible to cancel this offer"
      } else {
        error.innerHTML = "error404";

      }

    }
    console.log("ok")
    await Navbar();
    window.location.reload();
    //Redirect("/");
  } catch (e) {
    console.error("CreatePage::error ", e);

  }
}

async function likeItem(idItem, idMember) {
  console.log(idItem, " + ", idMember);
  const request = {
    method: "POST", body: JSON.stringify({
      idItem: idItem, idMember: idMember,
    }), headers: {
      "Content-Type": "application/json"
    }
  };
  try {
    const response = await fetch("/api/items/like", request);
    console.log(request);
    console.log(response);
    if (!response.ok) {
      if (response.status === 403) {
        "imposssible to cancel this offer"
      } else {
        error.innerHTML = "errorrr";

      }

    }
    console.log("ok")
    await Navbar();
    Redirect("/");
  } catch (e) {
    console.error("likeItem::error ", e);
  }
}

async function rateItem(idItem, idMember, stars, comment) {
  const request = {
    method: "POST", body: JSON.stringify({
      itemId: idItem, memberId: idMember, stars: stars, comment: comment
    }), headers: {
      "Content-type": "application/json"
    }
  };
  try {
    const response = await fetch("/api/items/rate", request);
    if (!response.ok) {
      if (response.status === 403) {
        "imposssible to rate this offer"
      } else {
        error.innerHTML = "errorrr";

      }

    }
    await Navbar();
    Redirect("/");
  } catch (e) {
    console.error("rateItem::error ", e);

  }
}

export {
  getItemUnordered,
  getOrderedItems,
  createItem,
  cancelOffer,
  modifyOfferFunction,
  modifyOffer,
  rateItem,
  likeItem,
    getInterests
};
