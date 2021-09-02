package repos

import models._
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions}
import org.mongodb.scala.model.Indexes.ascending
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
  def readAll(): Future[List[Movie]] ={
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

}
