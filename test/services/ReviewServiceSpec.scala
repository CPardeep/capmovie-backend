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
    title = "testTitle",
    review = List(("TESTUSER", "TESTREVIEW", false)),
    rating = List(("TESTUSER", 0.0)),
  )

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
      when(repo.updateAvgRating(any(), any())) thenReturn Future.successful(true)
      await(service.createReviewRating("TESTMOV", "TESTUSER1", review)) shouldBe(true, true, true)
    }
    "return false if the wrong movie id has been given" in {
      when(repo.createReview(any(), any())) thenReturn Future.successful(false)
      when(repo.createRating(any(), any())) thenReturn Future.successful(false)
      when(repo.read(any())) thenReturn Future.successful(Some(movie))
      when(repo.updateAvgRating(any(), any())) thenReturn Future.successful(false)
      await(service.createReviewRating("TESTMOV1", "TESTUSER1", review)) shouldBe(false, false, false)
    }
  }

  "calculateAvgRating" should {
    "return true when an average is calculated" in {
      when(repo.read(any())) thenReturn Future.successful(Some(movie))
      when(repo.updateAvgRating(any(), any())) thenReturn Future.successful(true)
      await(service.calculateAvgRating("TESTMOV")) shouldBe true
    }
    "return false when it fails to calculate" in {
      when(repo.read(any())) thenReturn Future.successful(Some(movie))
      when(repo.updateAvgRating(any(), any())) thenReturn Future.successful(false)
      await(service.calculateAvgRating("TESTMOV1")) shouldBe false
    }
  }

}
