package simple.tags_list

import org.scalajs.dom
import org.scalajs.dom.Event
import org.scalajs.dom.html.Button
import org.scalajs.dom.raw.HTMLStyleElement
import rx.{Rx, Var}
import simple.SharedModel.Tags
import simple.helper.RxToFrag._

import scalatags.JsDom.all._
import scalacss.ScalatagsCss._
import scalacss.Defaults._
import scalatags.JsDom.TypedTag

class TagsList(mailTags: Var[Tags], userTags: Var[Tags]) {

  def rmTerm(term: String): (Event) => Unit = (e: dom.Event) => {
    mailTags() = Tags(mailTags().tags.filterNot(_ == term))
    userTags() = Tags(userTags().tags.filterNot(_ == term))
  }

  val liRx = Rx {
    (mailTags().tags ::: userTags().tags).distinct.map(tag => {
      li(
        TagsStyle.tagItem,
        button(TagsStyle.btn, onclick := rmTerm(tag), "-"),
        span(TagsStyle.label, tag)
      )
    }
    )
  }
  val ulDiv = ul(TagsStyle.tagsList, liRx).render
  val btn = button(TagsStyle.btn, `type` := "button", "+").render
  val tagInput = input(`class` := "form-control").render

  btn.onclick = (e: dom.Event) => {
    userTags() = Tags(userTags().tags :+ tagInput.value)
    tagInput.value = ""
  }

  val searchList =
    div(
      TagsStyle.render[scalatags.JsDom.TypedTag[HTMLStyleElement]],
      TagsStyle.searchList,
      ulDiv,
      div(
        TagsStyle.inputTags,
        tagInput,
        btn
      )
    ).render
}
