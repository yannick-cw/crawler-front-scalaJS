package crawly

import scalatags.Text.all._
object Page {

  val boot = "crawly.app.App().main(document)"

  val skeleton =
    html(
      head(
        script(src:="/app-fastopt.js"),
        link(rel:="stylesheet", href:="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"),
        link(rel:="script", href:="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js")
      ),
      body(onload:=boot)
    )
}