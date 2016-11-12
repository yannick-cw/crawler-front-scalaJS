package simple.app

import org.scalajs.dom.raw.HTMLStyleElement
import org.scalajs.dom.{Event, html}
import rx._
import simple.SharedModel.Tags
import simple.app.AppStyle._
import simple.email_field.EmailField
import simple.tags_list.TagsList
import simple.{MainStyle, Requester}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._


@JSExport
object App {

  implicit val owner = Ctx.Owner.safe()
  val validEmail = Var("")
  val mailTags = Var(Tags(List.empty))
  val userTags = Var(Tags(List.empty))
  val submitNotReady = Rx { validEmail().isEmpty }

  @JSExport
  def main(document: html.Document): Unit = {
    // trigger on valid email change
    validEmail.triggerLater(submitNotReady.now match {
      case false => Requester.getTags(validEmail.now).foreach(tags => mailTags() = tags)
      case true => mailTags() = Tags(List.empty)
    })

    val mailField = new EmailField(validEmail)
    val tagsList = new TagsList(mailTags, userTags)
    def register = (e: Event) => Requester.register(validEmail.now, Tags(mailTags.now.tags ::: userTags.now.tags))
    document.head.appendChild(MainStyle.render[scalatags.JsDom.TypedTag[HTMLStyleElement]].render)

    val subBtn = input(submitBtn, `type` := "submit", "Submit").render
    submitNotReady.trigger{ subBtn.disabled = submitNotReady.now }

    document.body.appendChild(
      div(
        app,
        AppStyle.render[scalatags.JsDom.TypedTag[HTMLStyleElement]],
        div(
          container,
          form(onsubmit := register,
            div(
              formContainer,
              mailField.mailDiv,
              tagsList.searchList,
              subBtn
            )
          )
        )
      ).render
    )
  }
}