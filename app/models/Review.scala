package models

import play.api.libs.json._

case class Review(movieId: Option[String],
                  userId: String,
                  review: String,
                  rating: Double,
                  isApproved: Boolean)

object Review {

  implicit val format: OFormat[Review] = Json.format[Review]

  val newReviewReads: Reads[Review] = new Reads[Review] {

    def reads(json: JsValue): JsResult[Review] = {
      val result = for {
        userId <- (json \ "userId").asOpt[String]
        review <- (json \ "review").asOpt[String]
        rating <- (json \ "rating").asOpt[Double]
        isApproved <- (json \ "isApproved").asOpt[Boolean]
      } yield Review(None, userId, review, rating, isApproved)
      result match {
        case Some(x) => JsSuccess(x)
        case _ => JsError("ERROR")
      }
    }
  }
}