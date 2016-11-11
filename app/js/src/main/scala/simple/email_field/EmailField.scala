package simple.email_field

import org.scalajs.dom
import rx.Var
import simple.EmailValidation.mailValid

import scala.util.Try
import scalatags.JsDom.all._

class EmailField(validEmail: Var[String]) {

  val helpText = p(id := "emailHelp", `class` := "form-text text-muted", "Email not valid").render
  val mailIn = input(`type` := "email", `class` := "form-control", id := "mailInput", placeholder := "Enter email").render
  val mailDiv = div(
    `class` := "form-group",
    label(`for` := "mailInput", "Email address", aria.describedby := "emailHelp"),
    mailIn
  ).render

  mailIn.onchange = (e: dom.Event) => {
      val mail = mailIn.value
      if (!mailValid(mail) && mail.nonEmpty)
        mailDiv.appendChild(helpText)
      else if(mail.nonEmpty) {
        validEmail() = mail
        Try(mailDiv.removeChild(helpText))
      }
    }
}