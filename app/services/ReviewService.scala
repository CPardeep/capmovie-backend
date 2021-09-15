package services

import models.Review
import repos.MovieRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.math.BigDecimal.RoundingMode

class ReviewService @Inject()(repo: MovieRepo) {

  def createReviewRating(movieId: String, userId: String, body: Review): Future[(Boolean, Boolean, Boolean)] = {
    for {
      review <- repo.createReview(movieId, List(userId, body.review, body.isApproved))
      rating <- repo.createRating(movieId, List(userId, body.rating))
      avgRating <- calculateAvgRating(movieId)
    } yield (review, rating, avgRating)
  }

  def calculateAvgRating(movieId: String): Future[Boolean] = {
    repo.read(movieId) flatMap { movie =>
      val ratings = movie.get.rating
      val ave = ratings.map(_._2).foldLeft(0.0)(_ + _) / ratings.size
      repo.updateAvgRating(movieId, BigDecimal(ave).setScale(1, RoundingMode.HALF_UP).toDouble)
    }
  }


}