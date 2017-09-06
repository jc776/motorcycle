## Motorcycle

- Client re-runs on changes
- React + global.[name] for "figwheel" preserved state soon, I've done this before
- Server (e.g. /hello) modified on changes (spring-loaded!)
- * results may vary, I haven't put my forked sbt-spring-loaded for SBT 1.0 up yet

```
$ npm run dev
-- second terminal --
$ sbt
> server/reStart
> ~;server/compile;client/fastOptJS
```
Then go to `localhost:8080`.

## Other
- scalajs-bundler? Not on SBT 1.0. Not sure how the "new reload" flow would do HMR.
- IDE? ENSIME doesn't like `slinky-web`. Eclipse should work with SBT 1.0, needs a weird beta to use ScalaJS 2.12.3.