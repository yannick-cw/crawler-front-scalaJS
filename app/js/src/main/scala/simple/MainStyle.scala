package simple

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
}