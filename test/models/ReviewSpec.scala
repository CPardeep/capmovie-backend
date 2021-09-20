package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class ReviewSpec extends AbstractModelsTest {

  val review: Review = Review(
    userId = "TESTUSER",
    review = "TESTREVIEW",
    rating = 5.0,
    isApproved = false
  )

  val reviewJson: JsValue = Json.parse(
    s"""{
       |"userId" : "TESTUSER",
       |"review" : "TESTREVIEW",
       |"rating" : 5.0,
       |"isApproved" : false
       |}
       |""".stripMargin
  )

  "Review" can {
    "OFormat" should {
      "convert object into json" in {
        Json.toJson(review).toString() shouldBe reviewJson.toString()
      }
      "convert Json into object" in {
        Json.fromJson[Review](reviewJson).get.toString shouldBe JsSuccess(review).get.toString
      }
    }
  }

}
