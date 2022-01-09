package fr.esgi.al.funprog
import fr.esgi.al.funprog.MowerSnapshot
import fr.esgi.al.funprog.CoupleMowerSnapshot

class EnvTest(
    val limitx: Int,
    val limity: Int,
    val mowerSnapshotlist: List[MowerSnapshot]
) {

  val limitX: Int = limitx
  val limitY: Int = limity
  val grid = new Grid(this.limitX, this.limitY)
  val mowerSnapshotList: List[MowerSnapshot] = mowerSnapshotlist

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
