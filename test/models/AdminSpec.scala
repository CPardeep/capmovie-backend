package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class AdminSpec extends AbstractModelsTest {
  val admin: Admin = Admin(
    id = "testID",
    password ="testPass"
  )
  val adminJson: JsValue = Json.parse(
    s"""{
       |  "id" : "testID",
       |  "password" : "testPass"
       |}
       |""".stripMargin
  )
  "Movie" can {
    "OFormat" should {
      "convert object to json" in {
        Json.toJson(admin) shouldBe adminJson
      }
      "convert json to object" in {
        Json.fromJson[Admin](adminJson) shouldBe JsSuccess(admin)
      }
    }
  }
}
