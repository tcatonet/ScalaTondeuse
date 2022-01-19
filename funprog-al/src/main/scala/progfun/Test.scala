package test
import play.api.libs.json._
import fr.esgi.al.funprog.EnvTest
import fr.esgi.al.funprog.MowerSnapshot
import data.MyObject

class Test(
    val limitx: Int,
    val limity: Int,
    val mowerSnapshotlist: List[MowerSnapshot]
) {

  val env = new EnvTest(limitx, limity, mowerSnapshotlist)

  def generateJSONDataTest(): JsValue = {

    val t1: JsValue = Json.obj(
      "debut" -> Json.obj(
        "point" -> Json.obj(
          "x" -> 1,
          "y" -> 2
        ),
        "direction" -> "N"
      ),
      "instructions" -> List("G", "A", "G", "A", "G", "A", "G", "A", "A"),
      "fin" -> Json.obj(
        "point" -> Json.obj(
          "x" -> 1,
          "y" -> 3
        ),
        "direction" -> "N"
      )
    )

    val t2: JsValue = Json.obj(
      "debut" -> Json.obj(
        "point" -> Json.obj(
          "x" -> 3,
          "y" -> 3
        ),
        "direction" -> "E"
      ),
      "instructions" -> List("A", "A", "D", "A", "A", "D", "A", "D", "D", "A"),
      "fin" -> Json.obj(
        "point" -> Json.obj(
          "x" -> 5,
          "y" -> 1
        ),
        "direction" -> "E"
      )
    )

    val jsonDatatTest: JsValue =
      Json.obj(
        "limite" -> Json.obj(
          "x" -> 5,
          "y" -> 5
        ),
        "tondeuses" -> List(t1, t2)
      )
    jsonDatatTest
  }

  def generateCSVDataTest(): String = {

    val csvDataTest: String =
      "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions;limitX:5;limitY:5\n1;1;2;N;1;3;N;GAGAGAGAA\n2;3;3;E;5;1;E;AADAADADDA\n"

    csvDataTest
  }

  def assertString(input: String, expectedResult: String): Boolean = {
    input == expectedResult
  }

  def assertInt(input: Int, expectedResult: Int): Boolean = {
    input == expectedResult
  }

  def assertJson(input: JsValue, expectedResult: JsValue): Boolean = {
    input.toString == expectedResult.toString
  }

  def assertCSV(
      input: String,
      expectedResult: String
  ): Boolean = {
    input == expectedResult
  }

  def testJSON(expectedResult: JsValue): Boolean = {
    val listMowerSnapshotEnd = this.env.grid.findAllFinalPos(
      this.env.limitX,
      this.env.limitY,
      this.env.mowerSnapshotList,
      List()
    )

    val listCoupleMowerSnapshot =
      this.env.initCoupleMowerSnapshot(
        this.env.mowerSnapshotList,
        listMowerSnapshotEnd,
        List()
      )

    val dataJson: JsValue =
      MyObject.JSONData.formatData(
        this.env.limitX,
        this.env.limitY,
        listCoupleMowerSnapshot
      )

    println(dataJson)
    assertJson(dataJson, expectedResult)
  }

  def testCSV(expectedResult: String): Boolean = {
    val listMowerSnapshotEnd = this.env.grid.findAllFinalPos(
      this.env.limitX,
      this.env.limitY,
      this.env.mowerSnapshotList,
      List()
    )

    val listCoupleMowerSnapshot =
      this.env.initCoupleMowerSnapshot(
        this.env.mowerSnapshotList,
        listMowerSnapshotEnd,
        List()
      )

    val dataCSV: String =
      MyObject.CSVData.formatData(
        env.limitX,
        env.limitY,
        listCoupleMowerSnapshot
      )
    println(dataCSV)

    assertCSV(dataCSV, expectedResult)

  }

}

object TestObject {

  def testProg(): Unit = {

    val mowerSnapshotList: List[MowerSnapshot] =
      List(
        new MowerSnapshot(
          1,
          2,
          "N",
          List("G", "A", "G", "A", "G", "A", "G", "A", "A")
        ),
        new MowerSnapshot(
          3,
          3,
          "E",
          List("A", "A", "D", "A", "A", "D", "A", "D", "D", "A")
        )
      )

    val test = new Test(5, 5, mowerSnapshotList)

    val jsonDatatTest: JsValue = test.generateJSONDataTest()
    val csvDataTest: String = test.generateCSVDataTest()

    println(test.testJSON(jsonDatatTest))
    println(test.testCSV(csvDataTest))

  }

}
