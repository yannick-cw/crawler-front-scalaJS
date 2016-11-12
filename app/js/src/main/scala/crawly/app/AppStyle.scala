package crawly.app
import scalacss.Defaults._
object AppStyle  extends StyleSheet.Inline {
  import dsl._

  val app = style(
    height.inherit
  )

  val container = style(
    boxShadow := "0px -6px 13px -8px black",
    margin(0 px, auto),
    padding(10 px),
    marginTop(-35 px),
    maxWidth(600 px),
    width(100%%),
    minWidth(300 px),
    backgroundColor.white,
    height.inherit,
    borderRadius(2 px)
  )

  val formContainer = style(
    display.flex,
    flexDirection.column
  )

  val submitBtn = style(
    addClassNames("btn", "btn-outline-success"),
    alignSelf.center,
    padding(10 px, 32 px)
  )

}