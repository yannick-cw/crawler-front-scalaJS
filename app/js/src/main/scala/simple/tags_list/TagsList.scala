package simple.tags_list

import org.scalajs.dom
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLStyleElement
import rx.{Ctx, Rx, Var}
import simple.SharedModel.Tags
import simple.helper.RxToFrag._
import scalacss.Defaults._
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

class TagsList(mailTags: Var[Tags], userTags: Var[Tags])(implicit owner: Ctx.Owner) {


  // list of li elements
  val liRx = Rx {
    (mailTags().tags ::: userTags().tags).distinct.map(tag =>
      li(
        TagsStyle.tagItem,
        button(TagsStyle.btn, onclick := rmTerm(tag), "-"),
        span(TagsStyle.label, tag)
      )
    )
  }

  val tagInput = input(`class` := "form-control").render

  val searchList =
    div(
      TagsStyle.render[scalatags.JsDom.TypedTag[HTMLStyleElement]],
      TagsStyle.searchList,
      ul(
        TagsStyle.tagsList,
        liRx),
      div(
        TagsStyle.inputTags,
        tagInput,
        button(TagsStyle.btn, `type` := "button", "+", onclick := addTag)
      )
    ).render


  def addTag = (e: dom.Event) => {
    val currentTags = userTags.now.tags
    userTags() = Tags(currentTags :+ tagInput.value)
    tagInput.value = ""
  }

  def rmTerm(term: String): (Event) => Unit = (e: dom.Event) => {
    mailTags() = Tags(mailTags.now.tags.filterNot(_ == term))
    userTags() = Tags(userTags.now.tags.filterNot(_ == term))
  }

}
