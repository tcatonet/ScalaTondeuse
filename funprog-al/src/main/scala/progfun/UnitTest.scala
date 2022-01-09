package unitTest
import play.api.libs.json._
import fr.esgi.al.funprog.EnvTest
import fr.esgi.al.funprog.MowerSnapshot
import data.MyObject

class UnitTest(
    val limitx: Int,
    val limity: Int,
    val mowerSnapshotlist: List[MowerSnapshot]
) {

  val env = new EnvTest(limitx, limity, mowerSnapshotlist)

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
      input: List[String],
      expectedResult: List[String],
      res: Boolean
  ): Boolean = {

    if (input.length == expectedResult.length)
      assertCSVRec(input, expectedResult, res)
    else {
      false
    }
  }

  def assertCSVRec(
      input: List[String],
      expectedResult: List[String],
      res: Boolean
  ): Boolean = input match {

    case head :: tail =>
      val newRes = head == expectedResult(0)
      assertCSV(
        tail,
        expectedResult.take(0) ++ expectedResult.drop(1),
        res && newRes
      )

    case _ => res
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

    assertJson(dataJson, expectedResult)
  }

  def testCSV(expectedResult: List[String]): Boolean = {
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

    val dataCSV: List[String] =
      MyObject.CSVData.formatData(
        env.limitX,
        env.limitY,
        listCoupleMowerSnapshot
      )

    assertCSV(dataCSV, expectedResult, true)

  }

}
