package services

import models.Movie
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo

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
}
