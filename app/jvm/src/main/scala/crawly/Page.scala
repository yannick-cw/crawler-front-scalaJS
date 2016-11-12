package crawly

import scalatags.Text.all._
object Page {

  val boot = "crawly.app.App().main(document)"

  val skeleton =
    html(
      head(
        script(src:="/app-fastopt.js"),
        link(rel:="stylesheet", href:="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"),
        link(rel:="script", href:="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js")
      ),
      body(onload:=boot)
    )
}