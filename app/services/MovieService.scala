package services

import models.{Movie, Review}
import repos.MovieRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MovieService @Inject()(repo: MovieRepo) {

  def create(movie: Movie): Future[Boolean] = {
    repo.create(movie)
  }

  def createReviewRating(movieId: String, review: Review): Future[Boolean] = {
    repo.createReview(movieId, review)
  }

  def read(movieId: String): Future[Option[(Movie, Double)]] = {
    for {
      optMovie <- repo.read(movieId)
    } yield optMovie.map { movie =>
      val ratings = movie.reviews.map(_.rating)
      movie -> ratings.sum / ratings.size
    }
  }

}
