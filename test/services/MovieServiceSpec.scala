package services

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo
import utils.TestObjects
import scala.concurrent.Future

class MovieServiceSpec extends AbstractServiceTest {

  val repo: MovieRepo = mock[MovieRepo]
  val service: MovieService = new MovieService(repo)

  "createMovie" should {
    "return true if the movie details have been added" in {
      when(repo.create(any())) thenReturn Future.successful(true)
      await(service.create(TestObjects.movie)) shouldBe true
    }
    "return false if the movies details are incorrect" in {
      when(repo.create(any())) thenReturn Future.successful(false)
      await(service.create(TestObjects.movie)) shouldBe false
    }
  }
}
