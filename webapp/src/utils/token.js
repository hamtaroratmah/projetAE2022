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

export {getToken};