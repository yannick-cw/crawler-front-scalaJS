package simple

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, Uri}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import de.heikoseeberger.akkahttpcirce.CirceSupport._
import simple.SharedModel.Tags

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Server {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val basePage = HttpEntity(ContentTypes.`text/html(UTF-8)`, Page.skeleton.render)

    val connPool = Http().cachedHostConnectionPool[Int]("104.198.201.227", 8080)

    val route: Route = get {
      pathSingleSlash {
        complete(basePage)
      }
    } ~ getFromResourceDirectory("") ~
      path("mail" / Segment) { email =>
        get {
          val a: Future[Tags] = Source.single(email)
            .map(mail => HttpRequest(uri = Uri(s"/tags/$mail")) -> 0)
            .via(connPool)
            .map(_._1.get)
            .mapAsync(1)(resp => Unmarshal(resp.entity).to[Tags])
            .runWith(Sink.head)
          complete(a)
        }
      }

    Http().bindAndHandle(route, "0.0.0.0", 8080)
  }
}


