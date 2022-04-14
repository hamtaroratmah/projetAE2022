import {getMember} from "../utils/functions/member";
import {getToken} from "../utils/functions/token";

let page = `
  <div id="profilePage">
     
  </div>
`;

const ProfilePage = async () => {
  const main = document.querySelector("#page");
  main.innerHTML = page;
  const profile = document.querySelector("#profilePage");
  const member = await getMember(getToken());
  const address = member.address;
  profile.innerHTML = `
    <p class="enteteProfile">Pseudo</p>
    <input type="text" class="field" id="username" value="${member.username}">
    <p class="enteteProfile">Nom</p>
    <input type="text" class="field" id="lastName" value="${member.lastName}">
    <p class="enteteProfile">Prénom</p>
    <input type="text" class="field" id="firstName" value="${member.firstName}">
    <p class="enteteProfile">Mot de passe</p>
    <input type="password" class="field" id="password" value="${member.password}">
    <p class="enteteProfile">Numéro de téléphone (optionnel)</p>
    <input type="tel" class="field" id="callNumber" value="${member.callNumber}">
    <p class="enteteProfile">Rue</p>
    <input type="text" class="field" id="street" value="${address.street}">
    <p class="enteteProfile">Numéro</p>
    <input type="text" class="field" id="buildingNumber" value="${address.buildingNumber}">
    <p class="enteteProfile">Code postal</p>
    <input type="text" class="field" id="postcode" value="${address.postcode}">
    <p class="enteteProfile">Ville</p>
    <input type="text" class="field" id="city" value="${address.city}">
    <p class="enteteProfile">Boîte (optionnel)</p>
    <input type="text" class="field" id="unitNumber" value="${address.unitNumber}">
    <button type="submit" id="updateButton">Modifier mes informations</button> 
 `;
  main.innerHTML += profile;
  const button = document.querySelector("#updateButton");
  button.addEventListener("click", () => {
    let newMember = new member;

    let newAddress = new address;

    newMember.username = document.querySelector("#username").value;
    newMember.lastName = document.querySelector("#lastName").value;
    newMember.firstName = document.querySelector("#firstName").value;
    newMember.password = document.querySelector("#password").value;
    newMember.callNumber = document.querySelector("#callNumber").value;

    newAddress.street = document.querySelector("#street").value;
    newAddress.buildingNumber = document.querySelector("#buildingNumber").value;
    newAddress.postcode = document.querySelector("#postcode").value;
    newAddress.city = document.querySelector("#city").value;
    newAddress.unitNumber = document.querySelector("#unitNumber").value;
    newMember.address = newAddress;
    console.log(JSON.stringify(newMember))
    console.log(JSON.stringify(member))
    if (JSON.stringify(newMember) !== JSON.stringify(member)) {
      console.log("fetch à venir");
    } else {
      console.log("égal")
    }
    // updateMember(member);
  });
}

export default ProfilePage;