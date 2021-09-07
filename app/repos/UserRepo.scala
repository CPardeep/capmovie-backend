package repos

import models.User
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions}
import org.mongodb.scala.model.Indexes.ascending
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserRepo @Inject()(mc: MongoComponent) extends PlayMongoRepository[User](
  collectionName = "UserRepo",
  mongoComponent = mc,
  domainFormat = User.format,
  indexes = Seq(IndexModel(ascending("id"), IndexOptions().unique(true)))
) {
  def create(user: User): Future[Boolean] = {
    collection.insertOne(user).toFuture().map { response =>
      response.wasAcknowledged() && !response.getInsertedId.isNull
    }.recover { case _ => false }
  }

  def read(id: String): Future[Option[User]] = collection.find(Filters.eq("id", id)).headOption()

}
