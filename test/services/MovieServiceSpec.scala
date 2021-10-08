package services

import models.{Movie, MovieWithAvgRating, Review}
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
    reviews = Nil,
    genres = List(
      "testGenre1",
      "testGenre2"),
    rated = "testRating",
    cast = List(
      "testPerson",
      "TestPerson"),
    poster = "testURL",
    title = "testTitle")

  val review: Review = Review(None, "testUser", "testReview", 1.0, isApproved = false)

  val movieWithReview: Movie = movie.copy(
    reviews = List(Review(None, "testUser", "testReview", 1.0, isApproved = false))
  )

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

  "createReview" should {
    "return true if review has been added" in {
      when(repo.createReview(any(), any()))
        .thenReturn(Future.successful(true))
      await(service.createReview("TESTMOV", review)) shouldBe true
    }
    "return false if review details are incorrect" in {
      when(repo.createReview(any(), any()))
        .thenReturn(Future.successful(false))
      await(service.createReview("BADID", review)) shouldBe false
    }
  }

  "read" should {
    "return a movie and double" in {
      when(repo.read(any()))
        .thenReturn(Future(Some(movieWithReview)))
      await(service.read("TESTMOV")) shouldBe Some(MovieWithAvgRating(movieWithReview, 1.0))
    }
    "return none if movieId does not exist" in {
      when(repo.read(any()))
        .thenReturn(Future(None))
      await(service.read("BADID")) shouldBe None
    }
    "returns a 0.0 rating if movie does not contain any ratings" in {
      when(repo.read(any()))
        .thenReturn(Future(Some(movie)))
      await(service.read("TESTMOV")) shouldBe Some(MovieWithAvgRating(movie, 0.0))
    }
  }

  "readReviewById" should {
    "return all the movies associated to an ID" in {
      when(repo.readAll())
        .thenReturn(Future(List(movieWithReview)))
      await(service.readReviewByID("testUser")) shouldBe List(review.copy(movieId = Some("TESTMOV")))
    }
    "returns an empty list if there is no movie associated to user" in {
      when(repo.readAll())
        .thenReturn(Future(Nil))
      await(service.readReviewByID("testUser")) shouldBe Nil
    }
  }

  "updateReview" should {
    "returns true if the review has been updated" in {
      when(repo.read(any()))
        .thenReturn(Future(Some(movieWithReview)))
      when(repo.updateReview(any(), any()))
        .thenReturn(Future(true))
      await(service.updateReview("TESTMOV", review.copy(review = "TEST UPDATE MOVIE 2"))) shouldBe true
    }
    "return false" when {
      "the movie doesn't exist" in {
        when(repo.read(any()))
          .thenReturn(Future(None))
        when(repo.updateReview(any(), any()))
          .thenReturn(Future(false))
        await(service.updateReview("BADMOVIEID", review.copy(review = "TEST UPDATE MOVIE 2"))) shouldBe false
      }
      "the review doesn't exist" in {
        when(repo.read(any()))
          .thenReturn(Future(Some(movieWithReview)))
        when(repo.updateReview(any(), any()))
          .thenReturn(Future(false))
        await(service.updateReview("BADMOVIEID", review.copy(review = "TEST UPDATE MOVIE 2"))) shouldBe false
      }
    }
  }

  "removeReview" should {
    "returns true if the review has been updated" in {
      when(repo.read(any()))
        .thenReturn(Future(Some(movieWithReview)))
      when(repo.updateReview(any(), any()))
        .thenReturn(Future(true))
      await(service.removeReview("TESTMOV", "testUser")) shouldBe true
    }
    "return false" when {
      "the movie doesn't exist" in {
        when(repo.read(any()))
          .thenReturn(Future(None))
        when(repo.updateReview(any(), any()))
          .thenReturn(Future(false))
        await(service.removeReview("BADMOVIEID", "testUser")) shouldBe false
      }
      "the review doesn't exist" in {
        when(repo.read(any()))
          .thenReturn(Future(Some(movieWithReview)))
        when(repo.updateReview(any(), any()))
          .thenReturn(Future(false))
        await(service.removeReview("BADMOVIEID", "testUser")) shouldBe false
      }
    }
  }


}
