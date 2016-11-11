package simple.app
import scalacss.Defaults._
object AppStyle  extends StyleSheet.Inline {
  import dsl._

  val app = style(
    height.inherit
  )

  val container = style(
    marginTop(20 px),
    maxWidth(800 px),
    backgroundColor.ghostwhite,
    height.inherit
  )

  val formContainer = style(
    display.flex,
    flexDirection.column
  )

  val submitBtn = style(
    addClassNames("btn", "btn-primary"),
    alignSelf.center,
    padding(10 px, 32 px)
  )

}