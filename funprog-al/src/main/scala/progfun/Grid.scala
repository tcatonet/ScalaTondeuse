package fr.esgi.al.funprog

import fr.esgi.al.funprog.MowerSnapshot

class Grid(val x: Int, val y: Int) {

  def moveForward(
      currentOrientation: Int,
      currentX: Int,
      currentY: Int
  ): (Int, Int) =
    currentOrientation match {

      case 0 => (currentX, currentY + 1)
      case 1 => (currentX + 1, currentY)
      case 2 => (currentX, currentY - 1)
      case 3 => (currentX - 1, currentY)

    }

  def createIntOrientation(
      stringOrientation: String
  ): Int =
    stringOrientation match {

      case "N" => 0
      case "E" => 1
      case "S" => 2
      case "W" => 3
      case _   => -1

    }

  def createStringOrientation(
      intOrientation: Int
  ): String =
    intOrientation match {

      case 0 => "N"
      case 1 => "E"
      case 2 => "S"
      case 3 => "W"
      case _ => ""

    }

  def findNewOrientation(currentOrientation: Int, action: String): Int =
    currentOrientation match {

      case 0 =>
        if (action == "D") {
          1
        } else {
          3
        }

      case 1 =>
        if (action == "D") {
          2
        } else {
          0
        }

      case 2 =>
        if (action == "D") {
          3
        } else {
          1
        }

      case 3 =>
        if (action == "D") {
          0
        } else {
          2
        }
      case _ => -1

    }

  //Trouve la position finale de la tondeuse
  def findFinalPos(
      limitX: Int,
      limitY: Int,
      currentX: Int,
      currentY: Int,
      orientation: Int,
      parcourt: List[String]
  ): MowerSnapshot = parcourt match {

    case "A" :: tail =>
      val newXY: (Int, Int) = moveForward(orientation, currentX, currentY)

      if (newXY._1 >= 0 && newXY._1 <= limitX && newXY._2 >= 0 && newXY._2 <= limitY) {
        findFinalPos(limitX, limitY, newXY._1, newXY._2, orientation, tail)

      } else {
        findFinalPos(limitX, limitY, currentX, currentY, orientation, tail)
      }

    case "D" :: tail =>
      val newOrientation: Int = findNewOrientation(orientation, "D")
      findFinalPos(limitX, limitY, currentX, currentY, newOrientation, tail)

    case "G" :: tail =>
      val newOrientation: Int = findNewOrientation(orientation, "G")
      findFinalPos(limitX, limitY, currentX, currentY, newOrientation, tail)

    case _ =>
      new MowerSnapshot(
        currentX,
        currentY,
        createStringOrientation(orientation),
        List()
      )
  }

  def findAllFinalPos(
      limitX: Int,
      limitY: Int,
      mowerList: List[MowerSnapshot],
      result: List[MowerSnapshot]
  ): List[MowerSnapshot] = mowerList match {

    case head :: tail =>
      val newResult = findFinalPos(
        limitX,
        limitY,
        head.x,
        head.y,
        createIntOrientation(head.orientation),
        head.parcourt
      )
      findAllFinalPos(limitX, limitY, tail, newResult :: result)
    case _ => result.reverse

  }

  //Teste si la nouvelle position est dans la pelouse
  def testIfPosInLawn(newX: Int, newY: Int): Boolean = {
    println(newX + newY)
    true
  }

}
