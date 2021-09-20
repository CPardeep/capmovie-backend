package services

import models.{Movie, Review}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MovieServiceSpec extends AbstractServiceTest {

  val repo: MovieRepo = mock[MovieRepo]
  val service: MovieService = new MovieService(repo)
  val movie: Movie = Movie(
    id = "TESTMOV",
    plot = "Test plot",
    genres = List(
      "testGenre1",
      "testGenre2"),
    rated = "testRating",
    cast = List(
      "testPerson",
      "TestPerson"),
    poster = "testURL",
    title = "testTitle")

  val review: Review = Review("testUser", "testReview", 1.0, isApproved = false)

  val movieWithReview: Movie = movie.copy(
    reviews = List(Review("testUser", "testReview", 1.0, isApproved = false)))

  "createMovie" should {
    "return true if the movie details have been added" in {
      when(repo.create(any())) thenReturn Future.successful(true)
      await(service.create(movie)) shouldBe true
    }
    "return false if the movies details are incorrect" in {
      when(repo.create(any())) thenReturn Future.successful(false)
      await(service.create(movie)) shouldBe false
    }
  }

  "createReviewRating" should {
    "return true if review has been added" in {
      when(repo.createReview(any(), any()))
        .thenReturn(Future.successful(true))
      await(service.createReviewRating("TESTMOV", review)) shouldBe true
    }
    "return false if review details are incorrect" in {
      when(repo.createReview(any(), any()))
        .thenReturn(Future.successful(false))
      await(service.createReviewRating("BADID", review)) shouldBe false
    }
  }

  "read" should {
    "return a movie and double" in {
      when(repo.read(any()))
        .thenReturn(Future(Some(movieWithReview)))
      await(service.read("TESTMOV")) shouldBe Some(movieWithReview, 1.0)
    }

    "return none if movieId does not exist" in {
      when(repo.read(any()))
        .thenReturn(Future(None))
      await(service.read("BADID")) shouldBe None
    }
  }

}
