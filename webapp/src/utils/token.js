function getToken() {
  let token;
  if (window.sessionStorage.getItem("user") === null) {
    token = window.localStorage.getItem("user");
  } else {
    token = window.sessionStorage.getItem("user");
  }
  if (token !== null) {
    token = token.split("\"")[3];
  }
  return token;
}

const verifyToken = async () => {
  const request = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": getToken()
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
    sessionStorage.setItem("member", member)
    console.log("token valid <3")
  }
}

export {getToken, verifyToken};
