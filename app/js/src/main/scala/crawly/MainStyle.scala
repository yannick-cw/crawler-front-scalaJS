package crawly

import scalacss.Defaults._
import scalacss.defaults.Exports.StyleSheet

object MainStyle extends StyleSheet.Standalone {
  import dsl._

  "body" - (
    height(100 %%),
    margin(0 px),
    padding(0 px)
  )

  "html" - (
    height(100 %%),
    margin(0 px),
    padding(0 px)
  )

  "header" - (
    backgroundColor(c"#5cb85c"),
    height(100 px),
    color.white,
    textAlign.center,
    marginTop(10 px)
    )

  "footer" - (
    backgroundColor(c"#343434"),
    height(50 px),
    color.white,
    textAlign.center
    )


}