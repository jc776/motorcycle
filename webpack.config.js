const path = require('path');
module.exports = {
  entry: path.resolve(__dirname,"client/index.js"),
  output: {
    path: path.resolve(__dirname,"server/src/main/resources/public"),
    filename: "motorcycle-client-bundle.js"
  },
  devServer: {
    proxy: {
      "/": "http://localhost:3000"
    }
  }
}