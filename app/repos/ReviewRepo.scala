package repos

import com.mongodb.client.model.Filters.and
import models.Review
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Indexes.ascending
import org.mongodb.scala.model.{IndexModel, IndexOptions}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReviewRepo @Inject()(mc: MongoComponent) extends PlayMongoRepository[Review](
  collectionName = "ReviewRepo",
  mongoComponent = mc,
  domainFormat = Review.format,
  indexes = Seq(
    IndexModel(ascending("userId"), IndexOptions().unique(true)),
    IndexModel(ascending("movieID"), IndexOptions().unique(true)))
) {
  def create(review: Review): Future[Boolean] = {
    collection.insertOne(review).toFuture().map {
      response => response.wasAcknowledged() && !response.getInsertedId.isNull
    }.recover { case _ => false }
  }

  def deleteReview(movieId: String, userId: String): Future[Boolean] = {
    collection.deleteOne(and(equal("movieId", movieId), equal("userId", userId)))
      .toFuture().map { response =>
      response.wasAcknowledged && response.getDeletedCount == 1
    }
  }


}
