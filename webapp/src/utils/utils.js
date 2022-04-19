function reformateDate(date) {
  return new Date(date[0], date[1], date[2]).toDateString();
}

export {reformateDate}