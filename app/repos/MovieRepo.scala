package repos

import models._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Indexes.ascending
import org.mongodb.scala.model.Updates.{addToSet, pull, set}
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions, UpdateOptions}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MovieRepo @Inject()(mongoComponent: MongoComponent) extends PlayMongoRepository[Movie](
  collectionName = "MovieRepo",
  mongoComponent = mongoComponent,
  domainFormat = Movie.format,
  indexes = Seq(IndexModel(ascending("id"), IndexOptions().unique(true)))
) {
  def readAll(): Future[List[Movie]] = {
    collection.find().toFuture().map {
      _.toList
    }
  }

  def read(id: String): Future[Option[Movie]] = collection.find(Filters.eq("id", id)).headOption()

  def create(movie: Movie): Future[Boolean] = {
    collection.insertOne(movie).toFuture().map { response =>
      response.wasAcknowledged && !response.getInsertedId.isNull
    }.recover { case _ => false }
  }

  def delete(id: String): Future[Boolean] = {
    collection.deleteOne(equal("id", id)).toFuture().map { response =>
      response.wasAcknowledged && response.getDeletedCount == 1
    }
  }

  def updateTitle(id: String, title: String): Future[Boolean] = {
    collection.updateOne(
      Filters.equal("id", id),
      set("title", title)
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def updateGenre(id: String, genre: String): Future[Boolean] = {
    collection.updateOne(
      Filters.equal("id", id),
      addToSet("genres", genre)
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def removeGenre(id: String, genre: String): Future[Boolean] = {
    collection.updateOne(Filters.equal("id", id), pull("genres", genre))
      .toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def updateAgeRating(id: String, rated: String): Future[Boolean] = {
    collection.updateOne(
      Filters.equal("id", id),
      set("rated", rated)
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def updatePlot(id: String, plot: String): Future[Boolean] = {
    collection.updateOne(
      Filters.equal("id", id),
      set("plot", plot)
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def updatePoster(id: String, poster: String): Future[Boolean] = {
    collection.updateOne(
      Filters.equal("id", id),
      set("poster", poster)
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def updateCast(id: String, cast: String): Future[Boolean] = {
    collection.updateOne(
      Filters.equal("id", id),
      addToSet("cast", cast)
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def removeCast(id: String, cast: String): Future[Boolean] = {
    collection.updateOne(Filters.equal("id", id), pull("cast", cast))
      .toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def createReview(id: String, review: Review): Future[Boolean] = {
    collection.updateOne(Filters.and(equal("id", id), Filters.ne("reviews.userId", {
      review.userId
    })),
      addToSet("reviews", Map("userId" -> review.userId, "review" -> review.review, "rating" -> review.rating, "isApproved" -> review.isApproved))
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }

  def updateReview(movieId: String, reviews: List[Review]): Future[Boolean] = {
    collection.updateOne(
      Filters.equal("id", movieId),
      set("reviews", {
        reviews.map(review => Map("userId" -> review.userId, "review" -> review.review, "rating" -> review.rating, "isApproved" -> review.isApproved))
      }),
      (new UpdateOptions()).upsert(true)
    ).toFuture().map(result => result.getModifiedCount == 1 && result.wasAcknowledged())
  }
}
