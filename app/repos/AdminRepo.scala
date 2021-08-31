package repos

import models.Admin
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions}
import org.mongodb.scala.model.Indexes.ascending
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AdminRepo @Inject()(mongoComponent: MongoComponent) extends PlayMongoRepository[Admin](
  collectionName = "AdminRepo",
  mongoComponent = mongoComponent,
  domainFormat = Admin.format,
  indexes = Seq(IndexModel(ascending("id"), IndexOptions().unique(true)))
) {
  def create(admin: Admin): Future[Boolean] = {
    collection.insertOne(admin).toFuture().map { response =>
      response.wasAcknowledged() && !response.getInsertedId.isNull
    }.recover{ case _ => false}
  }
  def read(id: String): Future[Option[Admin]] = collection.find(Filters.eq("id", id)).headOption()
}
