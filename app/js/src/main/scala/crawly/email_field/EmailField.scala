package crawly.email_field

import org.scalajs.dom
import rx.{Ctx, Rx, Var}
import scalatags.JsDom.all._
import crawly.helper.RxToFrag._
import crawly.EmailValidation._

class EmailField(validEmail: Var[String])(implicit owner: Ctx.Owner) {

  val currentMail = Var("")
  val mailIn = input(`type` := "email", `class` := "form-control", placeholder := "Enter email").render
  val helpText = Rx {
    if (validEmail().isEmpty && currentMail().nonEmpty) small(`class` := "form-text text-muted", "Please enter a valid email")
    else span()
  }

  val mailDiv = div(
    `class` := "form-group",
    mailIn,
    rxFrag(helpText)
  ).render

  mailIn.onkeyup = (e: dom.Event) => {
    currentMail() = mailIn.value
    val mail = mailIn.value
    if (!mailValid(mail)) validEmail() = ""
    else if (mail.nonEmpty) validEmail() = mail
  }
}