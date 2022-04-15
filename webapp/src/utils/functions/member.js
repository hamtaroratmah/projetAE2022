import {getToken} from "./token";

async function getMember(token) {
  const request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": token
    }
  };
  const response = await fetch(`/api/members/`, request);
  if (!response.ok) {
    const error = document.getElementById("errorText");
    error.innerText = `Error while fetching username`;
  }
  return await response.json();
}

async function updateMember(member) {
  const request = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": getToken()
    },
    body: JSON.stringify(
        {
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
          unitNumber: member.address.unitNumber
        }
    )
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

export {getMember, updateMember};