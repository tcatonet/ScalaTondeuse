package data
import play.api.libs.json._
import fr.esgi.al.funprog.CoupleMowerSnapshot

object MyObject {
  trait Data[E, T, K, H] {
    def formatData(
        limitX: Int,
        limitY: Int,
        mowerListe: List[CoupleMowerSnapshot]
    ): K

    def genetateMowerData(
        mowerListe: List[CoupleMowerSnapshot],
        result: T
    ): H

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

  implicit object JSONData
      extends Data[JsValue, List[JsValue], JsValue, List[JsValue]] {

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

  implicit object CSVData extends Data[String, String, String, String] {

    def formatCSVAction(listAction: List[String], res: String): String =
      listAction match {

        case head :: tail =>
          val newRes: String = res + head
          formatCSVAction(tail, newRes)

        case _ => res

      }

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
          : String = startX.toString + ";" + startY.toString + ";" + startOrientation + ";" + endX.toString + ";" + endY.toString + ";" + endOrientation + ";" + formatCSVAction(
        action,
        ""
      ) + "\n"

      stringData
    }

    def genetateMowerData(
        mowerListe: List[CoupleMowerSnapshot],
        res: String
    ): String = mowerListe match {

      case head :: tail =>
        val newLigne = mowerListe.length.toString + ";" + mowerData(
          head.mowerSnapshot1.x,
          head.mowerSnapshot1.y,
          head.mowerSnapshot1.orientation,
          head.mowerSnapshot2.x,
          head.mowerSnapshot2.y,
          head.mowerSnapshot2.orientation,
          head.mowerSnapshot1.parcourt
        )
        val newRes: String = newLigne + res
        genetateMowerData(tail, newRes)

      case _ => res
    }

    def formatData(
        limitX: Int,
        limitY: Int,
        mowerListe: List[CoupleMowerSnapshot]
    ): String = {

      val firstLigne: String =
        "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions;limitX:" + limitX.toString + ";" + "limitY:" + limitY.toString + "\n"
      val nextLigne: String = genetateMowerData(mowerListe, "")
      firstLigne + nextLigne
    }

  }
}
