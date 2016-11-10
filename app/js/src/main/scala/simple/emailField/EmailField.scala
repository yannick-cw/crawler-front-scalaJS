package simple.emailField

import org.scalajs.dom
import simple.EmailValidation.mailValid
import scala.util.Try
import scalatags.JsDom.all._

class EmailField(foundValidMail: (String) => _) {

  val mailInput = input(`type` := "email", `class` := "form-control", id := "mailInput", placeholder := "Enter email").render
  val helpText = p(id := "emailHelp", `class` := "form-text text-muted", "Email not valid").render
  val emailField =
    div(
      `class` := "form-group",
      label(`for` := "mailInput", "Email address", aria.describedby := "emailHelp"),
      mailInput
    ).render

  mailInput.onkeyup = (e: dom.Event) => {
    val mail = mailInput.value
    if (!mailValid(mail) && mail.nonEmpty)
      emailField.appendChild(helpText)
    else {
      Try(emailField.removeChild(helpText))
      foundValidMail(mail)
    }
  }
}
