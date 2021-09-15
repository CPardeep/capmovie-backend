package repos

import models.Movie
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class MovieItSpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[Movie] {
  val repository = new MovieRepo(mongoComponent)
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

  val updatedTitle: Movie = movie.copy(
    title = "newTestTitle")

  val updatedGenre: Movie = movie.copy(
    genres = List(
      "testGenre1",
      "testGenre2",
      "newTestGenre"))

  val updatedRating: Movie = movie.copy(
    rated = "newAgeRating"
  )

  val updatedPlot: Movie = movie.copy(
    plot = "newPlot"
  )

  val updatedPoster: Movie = movie.copy(
    poster = "newPoster"
  )

  val updatedCast: Movie = movie.copy(
    cast = List(
      "testPerson",
      "TestPerson",
      "newCast")
  )


  "Create" should {
    "return true when valid details are submitted" in {
      await(repository.create(movie)) shouldBe true
    }
    "return false when duplicate details are submitted" in {
      await(repository.create(movie))
      await(repository.create(movie)) shouldBe false

    }
  }
  "Read" should {
    "return a movie successfully" in {
      await(repository.create(movie))
      val result = await(repository.read(movie.id))
      result shouldBe Some(movie)
    }
    "return nothing" in {
      await(repository.read(movie.id)) shouldBe None
    }
  }
  "readAll" should {
    "return a list of movies successfully" in {
      await(repository.create(movie))
      await(repository.create(movie.copy(id = "TESTMOV2")))
      await(repository.readAll()).size shouldBe 2
    }
    "returns an empty list" in {
      await(repository.readAll()) shouldBe List()
    }
    "Delete" should {
      "return true when deleted successfully" in {
        await(repository.create(movie))
        await(repository.delete(movie.id)) shouldBe true
      }
      "return false when id doesnt exist" in {
        await(repository.delete("badID")) shouldBe false
      }
    }
  }

  "updateTitle" should {
    "return true if title is updated" in {
      await(repository.create(movie))
      await(repository.updateTitle("TESTMOV", "newTestTitle")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(updatedTitle)
    }
    "return false if title is not updated" in {
      await(repository.create(movie))
      await(repository.updateTitle("BADID", "newTestTitle")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }
  }

  "updateGenre" should {
    "return true if genre is updated" in {
      await(repository.create(movie))
      await(repository.updateGenre("TESTMOV", "newTestGenre")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(updatedGenre)
    }
    "return false if genre is not updated" in {
      await(repository.create(movie))
      await(repository.updateGenre("BADID", "newTestGenre")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }
  }

  "removeGenre" should {
    "return true when genre is successfully removed" in {
      await(repository.create(movie))
      await(repository.updateGenre("TESTMOV", "newTestGenre"))
      await(repository.removeGenre("TESTMOV", "newTestGenre")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }

    "return false when genre is not removed" in {
      await(repository.create(movie))
      await(repository.updateGenre("TESTMOV", "newTestGenre"))
      await(repository.removeGenre("BADID", "newTestGenre")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(updatedGenre)
    }
  }

  "updateAgeRating" should {
    "return true if ageRating is updated" in {
      await(repository.create(movie))
      await(repository.updateAgeRating("TESTMOV", "newAgeRating")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(updatedRating)
    }
    "return false if ageRating is not updated" in {
      await(repository.create(movie))
      await(repository.updateAgeRating("BADID", "newAgeRating")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }
  }

  "updatePlot" should {
    "return true if plot is updated" in {
      await(repository.create(movie))
      await(repository.updatePlot("TESTMOV", "newPlot")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(updatedPlot)
    }
    "return false if plot is not updated" in {
      await(repository.create(movie))
      await(repository.updatePlot("BADID", "newPlot")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }
  }

  "updatePoster" should {
    "return true if poster is updated" in {
      await(repository.create(movie))
      await(repository.updatePoster("TESTMOV", "newPoster")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(updatedPoster)
    }
    "return false if poster is not updated" in {
      await(repository.create(movie))
      await(repository.updatePoster("BADID", "newPoster")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }
  }

  "updateCast" should {
    "return true if cast is updated" in {
      await(repository.create(movie))
      await(repository.updateCast("TESTMOV", "newCast")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(updatedCast)
    }
    "return false if cast is not updated" in {
      await(repository.create(movie))
      await(repository.updateCast("BADID", "newCast")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }
  }

  "removeCast" should {
    "return true when cast is successfully removed" in {
      await(repository.create(movie))
      await(repository.updateCast("TESTMOV", "newCast"))
      await(repository.removeCast("TESTMOV", "newCast")) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(movie)
    }

    "return false when cast is not removed" in {
      await(repository.create(movie))
      await(repository.updateCast("TESTMOV", "newCast"))
      await(repository.removeCast("BADID", "newCast")) shouldBe false
      await(repository.read("TESTMOV")) shouldBe Some(updatedCast)
    }
  }

  "createReview" should {
    "return true if review has successfully been added" in {
      await(repository.create(movie))
      await(repository.createReview("TESTMOV", List("USER101", "TESTREVIEW", false))) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(movie.copy(review = List(("USER101", "TESTREVIEW", false))))
    }
    "return false if wrong movie id is given" in {
      await(repository.create(movie))
      await(repository.createReview("BADID", List("USER101", "TESTREVIEW", false))) shouldBe false
    }
  }

  "createRating" should {
    "return true if rating has successfully been added" in {
      await(repository.create(movie))
      await(repository.createRating("TESTMOV", List("USER101", 1.0))) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(movie.copy(rating = List(("USER101", 1.0))))
    }
    "return false if wrong movie id is given" in {
      await(repository.create(movie))
      await(repository.createRating("BADID", List("USER101", 1.0))) shouldBe false
    }
  }

  "updateAvgRating" should {
    "return true if average rating has successfully been added" in {
      await(repository.create(movie))
      await(repository.updateAvgRating("TESTMOV", 1.0)) shouldBe true
      await(repository.read("TESTMOV")) shouldBe Some(movie.copy(avgRating = 1.0))
    }
    "return false if wrong movie id is given" in {
      await(repository.create(movie))
      await(repository.updateAvgRating("BADID", 1.0)) shouldBe false
    }
  }
}
