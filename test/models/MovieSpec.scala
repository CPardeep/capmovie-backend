package models

import play.api.libs.json.{JsSuccess, Json}
import utils.TestObjects

class MovieSpec extends AbstractModelsTest {

  "Movie" can {
    "OFormat" should {
      "convert object to json" in {
        Json.toJson(TestObjects.movie) shouldBe TestObjects.movieJson
      }
      "convert json to object" in {
        Json.fromJson[Movie](TestObjects.movieJson) shouldBe JsSuccess(TestObjects.movie)
      }
    }
  }
}
