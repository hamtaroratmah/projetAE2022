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
  console.log(member);
  profile.innerHTML = `
    
  `;
}

export default ProfilePage;