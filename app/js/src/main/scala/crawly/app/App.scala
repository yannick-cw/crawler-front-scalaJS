package crawly.app

import crawly.{MainStyle, Requester}
import crawly.SharedModel.Tags
import crawly.email_field.EmailField
import crawly.tags_list.TagsList
import crawly.texts.Texts
import org.scalajs.dom.raw.HTMLStyleElement
import org.scalajs.dom.{Event, html}
import rx._

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

    val subBtn = input(AppStyle.submitBtn, `type` := "submit").render
    submitNotReady.trigger{ subBtn.disabled = submitNotReady.now }

    document.body.appendChild(header(h2(Texts.header)).render)
    document.body.appendChild(meta(name:= "viewport", content:="width=device-width, initial-scale=1").render)
    document.body.appendChild(
      div(
        AppStyle.app,
        AppStyle.render[scalatags.JsDom.TypedTag[HTMLStyleElement]],
        div(
          AppStyle.container,
          p(
            h3(Texts.heading),
            p(Texts.description)
          ),
          form(onsubmit := register,
            div(
              AppStyle.formContainer,
              mailField.mailDiv,
              tagsList.searchList,
              subBtn
            )
          )
        )
      ).render
    )
//    document.body.appendChild(footer(p(Texts.footer)).render)
  }
}