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

  def createReview(movieId: String, review: Review): Future[Boolean] = {
    repo.createReview(movieId, review)
  }

  def read(movieId: String): Future[Option[(Movie, Double)]] = {
    for {
      optMovie <- repo.read(movieId)
    } yield optMovie.map { movie =>
      val ratings = movie.reviews.map(_.rating)
      val rating = if (ratings.isEmpty) 0.0 else ratings.sum / ratings.size
      movie -> rating
    }
  }

  def readReviewByID(userId: String): Future[List[Review]] = {
    for {
      optMovie <- repo.readAll()
    } yield optMovie.flatMap { movie =>
      movie.reviews.collect { case a if a.userId == userId => Review(Some(movie.id), a.userId, a.review, a.rating, a.isApproved) }
    }
  }

  def updateReview(movieId: String, newReview: Review): Future[Boolean] = {
    for {
      movieReviews <- repo.read(movieId).map(movies => movies.map(_.reviews.map {
        case x if x.userId == newReview.userId => newReview
        case y => y
      }))
      reviews <- movieReviews match {
        case Some(a) => repo.updateReview(movieId, a)
        case None => Future(false)
      }
    } yield reviews
  }

  def removeReview(movieId: String, userId: String): Future[Boolean] = {
    for {
      movieReviews <- repo.read(movieId).map(movies => movies.map(movie => movie.reviews.filterNot(_.userId == userId)))
      review <- movieReviews match {
        case Some(a) => repo.updateReview(movieId, a)
        case None => Future(false)
      }
    } yield review

  }

}
