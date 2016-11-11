package simple.app

import org.scalajs.dom.{Event, html}
import rx._
import simple.{MainStyle, Requester}
import simple.SharedModel.Tags
import simple.email_field.EmailField
import simple.tags_list.TagsList

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.annotation.JSExport
import AppStyle._
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLStyleElement

import scalacss.Defaults._
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._


@JSExport
object App {
  @JSExport
  def main(document: html.Document): Unit = {

    val validEmail = Var("")
    val mailTags = Var(Tags(List.empty))
    val userTags = Var(Tags(List.empty))

    Obs(validEmail, skipInitial = true)(Requester.getTags(validEmail()).foreach(tags => mailTags() = tags))

    val mailField = new EmailField(validEmail)
    val tagsList = new TagsList(mailTags, userTags)
    val register = (e: Event) => Requester.register(validEmail(), mailTags().copy(mailTags().tags ::: userTags().tags))

    document.head.appendChild(MainStyle.render[scalatags.JsDom.TypedTag[HTMLStyleElement]].render)
    document.body.appendChild(
      div(
        app,
        AppStyle.render[scalatags.JsDom.TypedTag[HTMLStyleElement]],
        div(
          container,
          form( onsubmit := register,
            div(
              formContainer,
              mailField.mailDiv,
              tagsList.searchList,
              input(submitBtn, `type` := "submit", "Submit")
            )
          )
        )
      ).render
    )
  }
}