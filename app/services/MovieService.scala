package services

import models.Movie
import repos.MovieRepo
import javax.inject.Inject
import scala.concurrent.Future

class MovieService @Inject()(repo: MovieRepo) {

  def create(movie: Movie): Future[Boolean] = {
    repo.create(movie)
  }

}
