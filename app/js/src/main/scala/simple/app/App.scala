package simple.app
import org.scalajs.dom
import dom.html
import simple.Requester
import simple.emailField.EmailField

import scalatags.JsDom.all._
import scala.scalajs.js.annotation.JSExport
import scala.concurrent.ExecutionContext.Implicits.global

@JSExport
object App {

  val mail = new EmailField((m: String) => Requester.getTags(m).foreach(println))

  @JSExport
  def main(anchorDiv: html.Div): Unit = {
    anchorDiv
      .appendChild(mail.emailField)
      .render
  }
}
