package crawly

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import scala.concurrent.Future

class HttpConnection(url: String, port: Int)(implicit system: ActorSystem, materialize: Materializer) {
  val connPool = Http().cachedHostConnectionPool[Int](url, port)


  def runWith(httpRequest: HttpRequest): Future[HttpResponse] =
    Source.single(httpRequest -> 0)
    .via(connPool)
    .map(_._1.get)
    .runWith(Sink.head)
}

object HttpConnection {
  def apply(url: String, port: Int)(implicit system: ActorSystem, materialize: Materializer): HttpConnection = new HttpConnection(url, port)
}
