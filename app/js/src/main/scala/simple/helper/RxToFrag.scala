package simple.helper

import org.scalajs.dom
import rx._

import scala.scalajs.js
import scalatags.JsDom.all._

object RxToFrag {

  implicit def rxFrag[T](r: Rx[T])(implicit ev: T => Frag, ctx: Ctx.Owner): Frag = {
    def rSafe: dom.Node = span(r.now).render
    var last = rSafe
    r.triggerLater {
      val newLast = rSafe
      js.Dynamic.global.last = last
      last.parentNode.replaceChild(newLast, last)
      last = newLast
    }
    last
  }
}
