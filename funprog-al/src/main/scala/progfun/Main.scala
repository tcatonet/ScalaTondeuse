package fr.esgi.al.funprog
import data.MyObject
import unitTest.UnitTest
import play.api.libs.json._

object Main extends App {

  println("Ici le programme principal")

  val env = new Env()
  val listMowerSnapshotEnd = env.grid.findAllFinalPos(
    env.limitX,
    env.limitY,
    env.mowerSnapshotList,
    List()
  )

  val listCoupleMowerSnapshot =
    env.initCoupleMowerSnapshot(
      env.mowerSnapshotList,
      listMowerSnapshotEnd,
      List()
    )

  val dataJson: JsValue =
    MyObject.JSONData.formatData(
      env.limitX,
      env.limitY,
      listCoupleMowerSnapshot
    )
  println(dataJson)

  val dataCSV: List[String] =
    MyObject.CSVData.formatData(
      env.limitX,
      env.limitY,
      listCoupleMowerSnapshot
    )
  println(dataCSV)

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

  val unitTest = new UnitTest(env.limitX, env.limitY, env.mowerSnapshotList)
  val jsonDatatTest: JsValue =
    Json.obj(
      "limite" -> Json.obj(
        "x" -> 5,
        "y" -> 5
      ),
      "tondeuses" -> List(t1)
    )

  val csvDataTest: List[String] = List(
    "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions;limitX:5;limitY:5",
    "0;1;2;N;1;3;N;List(G, A, G, A, G, A, G, A, A)"
  )
  println(jsonDatatTest)

  println(unitTest.testJSON(jsonDatatTest))
  println(unitTest.testCSV(csvDataTest))

}
