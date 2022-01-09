package fr.esgi.al.funprog
import fr.esgi.al.funprog.MowerSnapshot
import fr.esgi.al.funprog.CoupleMowerSnapshot

class Env() {
  def chooseAction(
      saisie: String,
      result: List[String]
  ): List[String] = saisie match {

    case "O" =>
      println("Choisissez les action (A, D, G)")
      val action = scala.io.StdIn.readLine()

      if (action == "A" || action == "D" || action == "G") {
        println("voulez-vous ajouter une autre action? O pour oui")
        val saisie = scala.io.StdIn.readLine()
        chooseAction(saisie, action :: result)

      } else {
        println("Action incorrecte, réessayez")
        chooseAction("O", result)
      }

    case _ => result.reverse

  }

  def initListMowerSnapshot(
      saisie: String,
      result: List[MowerSnapshot]
  ): List[MowerSnapshot] = saisie match {

    case "O" =>
      println("Pos x départ ")
      val x = scala.io.StdIn.readInt()

      println("Pos y départ")
      val y = scala.io.StdIn.readInt()

      if (x >= 0 && x <= this.limitX && y >= 0 && y <= this.limitY) {

        println("Orientation départ (N,S,E,W)")
        val o = scala.io.StdIn.readLine()

        if (o == "N" || o == "S" || o == "E" || o == "W") {
          val actions = chooseAction("O", List())
          val mowerSnapshot = new MowerSnapshot(
            x,
            y,
            o,
            actions
          )

          println("Voulez-vous ajouter une tondeuse? O pour oui")
          val reponse = scala.io.StdIn.readLine()
          initListMowerSnapshot(reponse, mowerSnapshot :: result)

        } else {
          println("Orientation incorrecte, réessayez")
          initListMowerSnapshot("O", result)
        }

      } else {
        println("Coordonnées incorrecte, réessayez")
        initListMowerSnapshot("O", result)
      }

    case _ =>
      println(result)
      result

  }

  def findLimitX(): Int = {
    println("Xlimite de la grille ")
    val x = scala.io.StdIn.readInt()
    x
  }

  def findLimitY(): Int = {
    println("Y limite de la grille ")
    val y = scala.io.StdIn.readInt()
    y
  }

  val limitX: Int = findLimitX()
  val limitY: Int = findLimitY()

  val grid = new Grid(this.limitX, this.limitY)
  val mowerSnapshotList: List[MowerSnapshot] =
    initListMowerSnapshot("O", List())

  def initCoupleMowerSnapshot(
      listMowerSnapshot1: List[MowerSnapshot],
      listMowerSnapshot2: List[MowerSnapshot],
      result: List[CoupleMowerSnapshot]
  ): List[CoupleMowerSnapshot] =
    listMowerSnapshot1 match {

      case head :: tail =>
        val couple = new CoupleMowerSnapshot(head, listMowerSnapshot2(0))
        initCoupleMowerSnapshot(
          tail,
          listMowerSnapshot2.take(0) ++ listMowerSnapshot2.drop(1),
          couple :: result
        )

      case _ => result

    }

}
