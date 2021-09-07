package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class UserSpec extends AbstractModelsTest {
  val user: User = User(
    id = "testID",
    password ="testPass"
  )
  val userJson: JsValue = Json.parse(
    s"""{
       |  "id" : "testID",
       |  "password" : "testPass"
       |}
       |""".stripMargin
  )
  "Movie" can {
    "OFormat" should {
      "convert object to json" in {
        Json.toJson(user) shouldBe userJson
      }
      "convert json to object" in {
        Json.fromJson[User](userJson) shouldBe JsSuccess(user)
      }
    }
  }
}
