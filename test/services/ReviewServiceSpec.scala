package services

import models.{Movie, Review}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo

import scala.concurrent.Future

class ReviewServiceSpec extends AbstractServiceTest {
  val repo: MovieRepo = mock[MovieRepo]
  val service: ReviewService = new ReviewService(repo)
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

  val review: Review = Review(
    review = "TESTREVIEW",
    rating = 5.0,
    isApproved = false
  )

  "createReviewRating" should {
    "return true if the review, rating and avergae rating have beeen inserted in the database" in {
      when(repo.createReview(any(), any())) thenReturn Future.successful(true)
      when(repo.createRating(any(), any())) thenReturn Future.successful(true)
      when(repo.read(any())) thenReturn Future.successful(Some(movie))
      await(service.createReviewRating("TESTMOV", "TESTUSER", review)) shouldBe true
    }
    "return false if the wrong movie id has been given" in {
      when(repo.createReview(any(), any())) thenReturn Future.successful(true)
      when(repo.createRating(any(), any())) thenReturn Future.successful(true)
      when(repo.read(any())) thenReturn Future.successful(Some(movie))
      await(service.createReviewRating("TESTMOV1", "TESTUSER", review)) shouldBe false
    }
  }

}
