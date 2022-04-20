/**
 * @param date as an array
 * @return string of date
 */
function reformateDate(date) {
  return new Date(date[0], date[1], date[2]).toDateString();
}

function getToken() {
  const key = "user";
  let token;
  if (window.sessionStorage.getItem(key) === null) {
    token = window.localStorage.getItem(key);
  } else {
    token = window.sessionStorage.getItem(key);
  }
  if (token !== null) {
    token = token.split("\"")[3];
  }
  return token;
}

export {reformateDate, getToken}