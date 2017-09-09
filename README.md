## Motorcycle

- Client re-runs on changes (hot module reloading!)
- React + global.[name] for "figwheel" preserved state soon, I've done this before
- Server (e.g. /hello) modified on changes (spring-loaded!)
  * results may vary, I haven't put my forked sbt-spring-loaded for SBT 1.0 up yet

```
$ yarn install
$ yarn run dev
-- second terminal --
$ sbt
> start
> ~dev
```
`localhost:8080` is the webpack-dev-server, `localhost:3000` is the actual server. (Undertow)

## Other
- scalajs-bundler? Not on SBT 1.0. Not sure how the "new reload" flow would do HMR.
- IDE? ENSIME doesn't like `slinky-web`. Eclipse should work with SBT 1.0, needs a weird beta to use ScalaJS 2.12.3.
- [http://www.famfamfam.com/lab/icons/silk/](famfamfam's silk icons) are still cute
