const app = require("./target/scala-2.12/motorcycle-client-fastopt.js")
app.Motorcycle.start()

if(module.hot) {
  module.hot.accept()
}