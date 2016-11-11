package simple

import io.circe.parse._
import io.circe.syntax._
import org.scalajs.dom.ext._
import simple.SharedModel.Tags

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object Requester {
  def getTags(mail: String): Future[Tags] = Ajax
      .get(s"/mail/$mail")
      .map(response => parse(response.responseText).flatMap(_.as[Tags]).getOrElse(Tags(List.empty)))

  def register(mail: String, tags: Tags) = {
    Ajax
      .post(s"/mail/$mail", tags.asJson.noSpaces, headers = Map("Content-Type" -> "application/json"))
    println("posting " + tags.asJson.noSpaces+ " to " + mail)
  }
}