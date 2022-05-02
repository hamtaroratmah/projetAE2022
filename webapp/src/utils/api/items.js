import {getToken} from "../utils";
import {getMember} from "./member";
import Navbar from "../../Components/Navbar";
import {Redirect} from "../../Router";
import {displayPhoto} from "../displayModule/items";

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
  await fetch("/api/items/getLastOfferedItemsNonConnected", request)
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
      "/api/items/getItemSortedBy/?sortingParam=" + sortingParam + "&order="
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
      method: "POST",
      body: JSON.stringify(
          {
            type: type,
            photo: photo,
            description: description,
            availabilities: availabilities,
            idOfferingMember: member.idMember,
          }
      ),
      headers: {
        "Content-Type": "application/json"
      }
    };
    const response = await fetch("/api/offer/createOffer", request);
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

  async function likeItem(idItem,idMember){

    console.log(idItem," + ", idMember);
      const request = {
        method: "POST",
        body: JSON.stringify(
            {
              idItem: idItem,
              idMember:idMember,
            }
        ),
        headers: {
          "Content-Type": "application/json"
        }
      };
      try {
        const response = await fetch("/api/items/like",request);
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
        cosole.error("likeItem::error ", e);

      }

    }





export {getItemUnordered, getOrderedItems, createItem,likeItem};