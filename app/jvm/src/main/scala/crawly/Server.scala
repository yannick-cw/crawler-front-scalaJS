package crawly

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import crawly.SharedModel.Tags
import de.heikoseeberger.akkahttpcirce.CirceSupport._

import scala.concurrent.ExecutionContext.Implicits.global


object Server {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val basePage = HttpEntity(ContentTypes.`text/html(UTF-8)`, Page.skeleton.render)

    val connection = HttpConnection(system.settings.config.getString("crawler.host"), system.settings.config.getInt("crawler.port"))

    val basePageRoute: Route = get(pathSingleSlash(complete(basePage))) ~ getFromResourceDirectory("")

    val tagsRoute: Route =
      path("mail" / Segment) { email =>
        get {
          complete(
            connection.runWith(HttpRequest(uri = Uri(s"/tags/$email")))
              .flatMap(resp => Unmarshal(resp.entity).to[Tags])
          )
        }
      }

    val registerRoute: Route =
      path("mail" / Segment) { email =>
        post {
          entity(as[Tags]) { tags =>

            val params = Query(tags.tags.map(t => "tags" -> t): _*)
            val uri: Uri = Uri(s"/tags/$email").withQuery(params)
            val request = HttpRequest(uri = uri, method = HttpMethods.POST)
            complete(connection.runWith(request)
              .map(_.status))
          }
        }
      }

    Http().bindAndHandle(basePageRoute ~ tagsRoute ~ registerRoute, "0.0.0.0", 8080)
  }
}


