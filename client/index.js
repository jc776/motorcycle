const app = require("./target/scala-2.12/motorcycle-client-fastopt.js")
app.Motorcycle.start()

if(module.hot) {
  console.log("[HMR] Accepted.")
  module.hot.accept()
}