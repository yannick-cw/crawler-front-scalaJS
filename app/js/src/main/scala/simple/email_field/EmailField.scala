package simple.email_field

import org.scalajs.dom
import rx.{Ctx, Rx, Var}
import simple.EmailValidation.mailValid
import simple.helper.RxToFrag._
import scalatags.JsDom.all._

class EmailField(validEmail: Var[String])(implicit owner: Ctx.Owner) {

  val helpText = Rx {
    if (validEmail().isEmpty) p(id := "emailHelp", `class` := "form-text text-muted", "Please enter a valid email")
    else span()
  }

  val mailIn = input(`type` := "email", `class` := "form-control", id := "mailInput", placeholder := "Enter email").render
  val mailDiv = div(
    `class` := "form-group",
    label(`for` := "mailInput", "Email address", aria.describedby := "emailHelp"),
    mailIn,
    helpText
  ).render

  mailIn.onkeyup = (e: dom.Event) => {
    val mail = mailIn.value
    if (!mailValid(mail)) validEmail() = ""
    else if (mail.nonEmpty) validEmail() = mail
  }
}