package data
import play.api.libs.json._
import fr.esgi.al.funprog.CoupleMowerSnapshot

object MyObject {
  trait Data[E, T, K] {
    def formatData(
        limitX: Int,
        limitY: Int,
        mowerListe: List[CoupleMowerSnapshot]
    ): K

    def genetateMowerData(
        mowerListe: List[CoupleMowerSnapshot],
        result: T
    ): List[E]

    def mowerData(
        startX: Int,
        startY: Int,
        startOrientation: String,
        endX: Int,
        endY: Int,
        endOrientation: String,
        action: List[String]
    ): E

  }

  implicit object JSONData extends Data[JsValue, List[JsValue], JsValue] {

    def formatData(
        limitX: Int,
        limitY: Int,
        mowerListe: List[CoupleMowerSnapshot]
    ): JsValue = {

      val json: JsValue = Json.obj(
        "limite" -> Json.obj(
          "x" -> limitX,
          "y" -> limitY
        ),
        "tondeuses" -> genetateMowerData(mowerListe, List())
      )
      json
    }

    def genetateMowerData(
        mowerListe: List[CoupleMowerSnapshot],
        result: List[JsValue]
    ): List[JsValue] =
      mowerListe match {

        case head :: tail =>
          val mowerdata: JsValue = mowerData(
            head.mowerSnapshot1.x,
            head.mowerSnapshot1.y,
            head.mowerSnapshot1.orientation,
            head.mowerSnapshot2.x,
            head.mowerSnapshot2.y,
            head.mowerSnapshot2.orientation,
            head.mowerSnapshot1.parcourt
          )
          val newRes: List[JsValue] = mowerdata :: result
          genetateMowerData(tail, newRes)
        case _ => result

      }

    def mowerData(
        startX: Int,
        startY: Int,
        startOrientation: String,
        endX: Int,
        endY: Int,
        endOrientation: String,
        action: List[String]
    ): JsValue = {

      val json: JsValue = Json.obj(
        "debut" -> Json.obj(
          "point" -> Json.obj(
            "x" -> startX,
            "y" -> startY
          ),
          "direction" -> startOrientation
        ),
        "instructions" -> action,
        "fin" -> Json.obj(
          "point" -> Json.obj(
            "x" -> endX,
            "y" -> endY
          ),
          "direction" -> endOrientation
        )
      )

      json
    }
  }

  implicit object CSVData extends Data[String, List[String], List[String]] {

    def mowerData(
        startX: Int,
        startY: Int,
        startOrientation: String,
        endX: Int,
        endY: Int,
        endOrientation: String,
        action: List[String]
    ): String = {

      val stringData
          : String = startX.toString + ";" + startY.toString + ";" + startOrientation + ";" + endX.toString + ";" + endY.toString + ";" + endOrientation + ";" + action.toString

      stringData
    }

    def genetateMowerData(
        mowerListe: List[CoupleMowerSnapshot],
        res: List[String]
    ): List[String] = mowerListe match {

      case head :: tail =>
        val newLigne = res.length.toString + ";" + mowerData(
          head.mowerSnapshot1.x,
          head.mowerSnapshot1.y,
          head.mowerSnapshot1.orientation,
          head.mowerSnapshot2.x,
          head.mowerSnapshot2.y,
          head.mowerSnapshot2.orientation,
          head.mowerSnapshot1.parcourt
        )
        val newRes: List[String] = newLigne :: res
        genetateMowerData(tail, newRes)

      case _ => res.reverse
    }

    def formatData(
        limitX: Int,
        limitY: Int,
        mowerListe: List[CoupleMowerSnapshot]
    ): List[String] = {

      val firstLigne =
        "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions;limitX:" + limitX.toString + ";" + "limitY:" + limitY.toString
      val nextLigne: List[String] = genetateMowerData(mowerListe, List())
      firstLigne :: nextLigne
    }

  }
}
