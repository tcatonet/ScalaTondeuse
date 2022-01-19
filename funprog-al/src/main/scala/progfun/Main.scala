package fr.esgi.al.funprog
import data.MyObject
//import test.TestObject
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

  val dataCSV: String =
    MyObject.CSVData.formatData(
      env.limitX,
      env.limitY,
      listCoupleMowerSnapshot
    )
  println(dataCSV)
   
  //TestObject.testProg()
}
