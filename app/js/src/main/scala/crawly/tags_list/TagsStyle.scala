package crawly.tags_list


import scalacss.Defaults._
object TagsStyle extends StyleSheet.Inline {
  import dsl._
  val searchList = style(
    display.flex,
    flexDirection.column
  )

  val tagsList = style(
    display.flex,
    flexDirection.column,
    flexWrap.wrap,
    maxHeight(200 px),
    paddingLeft(0 px)
  )

  val tagItem = style(
    display.flex,
    alignItems.center,
    marginTop(3 px)
  )

  val label = style(
    addClassNames("label", "label-default"),
    marginLeft(7 px),
    fontWeight._600,
    fontSize.large,
    padding(8 px)
  )

  val inputTags = style(
    addClassName("form-group"),
    display.flex,
    flexDirection.row
  )

  val btn = style(
    marginLeft(7 px),
    addClassNames("btn", "btn-outline-success")
  )
}