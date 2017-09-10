const path = require('path');
const webpack = require('webpack');

module.exports = {
  context: __dirname,
  entry: {
    game: "./client/index.js",
  },
  output: {
    path: path.resolve(__dirname,"scala/server/src/main/resources/public"),
    filename: "[name]-bundle.js"
  },
  devServer: {
    "publicPath": "/",
	  "proxy": {
	    "/ws": { target: "http://localhost:3000/ws", secure: false, ws: true},
	    "*": "http://localhost:3000"
	  }
  },
  plugins: [
    new webpack.NamedModulesPlugin()
  ]
}