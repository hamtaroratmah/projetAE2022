import {getMember, updateMember} from "../utils/functions/member";
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
  console.log(member)
  main.innerHTML += profile;
  const button = document.querySelector("#updateButton");
  button.addEventListener("click", async () => {
    if (
        member.password !== document.querySelector("#password").value ||
        member.username !== document.querySelector("#username").value ||
        member.lastName !== document.querySelector("#lastName").value ||
        member.firstName !== document.querySelector("#firstName").value ||
        member.callNumber !== document.querySelector("#callNumber").value ||
        address.street !== document.querySelector("#street").value ||
        address.buildingNumber !== parseInt(
            document.querySelector("#buildingNumber").value) ||
        address.postcode !== parseInt(
            document.querySelector("#postcode").value) ||
        address.city !== document.querySelector("#city").value ||
        address.unitNumber !== parseInt(
            document.querySelector("#unitNumber").value)
    ) {
      member.password = document.querySelector("#password").value;
      member.username = document.querySelector("#username").value;
      member.lastName = document.querySelector("#lastName").value;
      member.firstName = document.querySelector("#firstName").value;
      member.callNumber = document.querySelector("#callNumber").value;
      address.street = document.querySelector("#street").value;
      address.buildingNumber = parseInt(
          document.querySelector("#buildingNumber").value);
      address.postcode = parseInt(
          document.querySelector("#postcode").value);
      address.city = document.querySelector("#city").value;
      address.unitNumber = parseInt(
          document.querySelector("#unitNumber").value);
      member.address = address;
      console.log(member);
      await updateMember(member);
      console.log("");
      console.log("fetch à venir");
    } else {
      console.log("égal");
    }

  });
}

export default ProfilePage;