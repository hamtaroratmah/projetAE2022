import {getListMembers} from "../utils/api/memberApi";
import {displayMembers} from "../utils/displayModule/members";
import {getToken} from "../utils/utils";
import {Redirect} from "../Router";

const listMemberDiv = `
        <div id="listMembersPage">
        </div>
`;

const listMembersDiv = async () => {

  if (!getToken()) {
    Redirect("/");
    window.location.reload();
  }

  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = listMemberDiv;

  let members = await getListMembers();
  displayMembers(members)
}

export default listMembersDiv;