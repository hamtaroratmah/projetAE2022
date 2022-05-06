import {getToken} from "../utils";

const verifyToken = async () => {
  const request = {
    method: "GET", headers: {
      "Content-Type": "application/json", "Authorization": getToken()
    }
  };
  const response = await fetch("/api/members/", request);
  if (!response.ok) {
    response.text().catch((err) => {
      console.log("token invalid </3")
      const errorDiv = document.querySelector("#errorText");
      errorDiv.innerHTML = `${err}`
      console.log(err)
    })
  } else {
    let member = await response.json()
    console.log("token valid <3")
  }
}

const error = document.querySelector("#errorText");

async function getMember(token) {
  const request = {
    method: "GET", headers: {
      "Content-Type": "application/json", "Authorization": token
    }
  };
  const response = await fetch(`/api/members/`, request);
  if (!response.ok) {
    const error = document.getElementById("errorText");
    error.innerText = `Error while fetching username`;
  }
  return await response.json();
}

// get the list of inscriptions
async function getListInscriptions() {
  const request = {
    method: "GET", headers: {
      "Content-Type": "application/json"
    }
  };

  let inscriptions = [];

  // fill inscriptions [] with inscriptions pending
  await fetch("/api/members/listPending", request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      inscriptions.push(commits[i]);
    }

  })
  .catch(() => error.innerHTML = "Une erreur est survenue"
      + " durant la récupération des inscriptions");

  // fill inscriptions [] with inscriptions denied
  await fetch("/api/members/listDenied", request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      inscriptions.push(commits[i]);
    }

  })
  .catch(() => error.innerHTML = "Une erreur est survenue"
      + " durant la récupération des inscriptions");
  return inscriptions;
}

async function getListMembers() {
  const request = {
    method: "GET", headers: {
      "Content-Type": "application/json"
    }
  };

  let members = [];

  // fill inscriptions [] with inscriptions pending
  await fetch("/api/members/listMembers", request)
  .then(response => response.json())
  .then((commits) => {
    for (let i = 0; i < commits.length; i++) {
      members.push(commits[i]);
    }

  })
  .catch(() => error.innerHTML = "Une erreur est survenue"
      + " durant la récupération des membres")

  return members;
}

async function confirmInscription(username, isAdmin) {
  const request = {
    method: "PUT", body: JSON.stringify({
      username: username, isAdmin: isAdmin
    }), headers: {
      "Content-Type": "application/json"
    }
  };
  await fetch("/api/members/confirm", request)
}

async function denyInscription(username, reasonForConnRefusal) {
  const request = {
    method: "PUT", body: JSON.stringify({
      username: username, reasonForConnRefusal: reasonForConnRefusal
    }), headers: {
      "Content-Type": "application/json"
    }
  };
  await fetch("/api/members/deny", request)
}

async function updateMember(member, confirmPassword) {
  const request = {
    method: "PUT", headers: {
      "Content-Type": "application/json", "Authorization": getToken()
    }, body: JSON.stringify({
      idMember: member.idMember,
      password: member.password,
      username: member.username,
      lastName: member.lastName,
      firstName: member.firstName,
      callNumber: member.callNumber,
      state: member.state,
      idAddress: member.address.idAddress,
      street: member.address.street,
      buildingNumber: member.address.buildingNumber,
      postcode: member.address.postcode,
      city: member.address.city,
      unitNumber: member.address.unitNumber,
      confirmPassword: confirmPassword
    })
  };
  const response = await fetch("/api/members/updateMember", request);
  if (!response.ok) {
    //todo display good error message
    const error = document.getElementById("errorText");
    error.innerText = "Error while updating member";
  }
  console.log("ok jusque ici");
  return await response.json();
}

async function precludMember(idMember) {
  const request = {
    method: "PUT", body: JSON.stringify({
      idMember: idMember,
    }), headers: {
      "Content-Type": "application/json"
    }
  };
  await fetch("/api/members/preclude", request)
}

export {
  getMember,
  getListInscriptions,
  verifyToken,
  confirmInscription,
  denyInscription,
  updateMember,
  getListMembers,
  precludMember
};
