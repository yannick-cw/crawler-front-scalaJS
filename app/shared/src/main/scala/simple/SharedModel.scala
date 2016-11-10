package simple

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._


object SharedModel {
  case class Tags(tags: List[String])
  object Tags {
    implicit val tagEncoder: Encoder[Tags] = deriveFor[Tags].encoder
    implicit val tagDecoder: Decoder[Tags] = deriveFor[Tags].decoder
  }
}
