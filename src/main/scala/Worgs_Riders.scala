import scala.util.Random

class Worgs_Riders extends  GameEntity {
  override var health: Int = 13

  override def armor: Int = 18

  override def attaque: Int = 50

  val range : Double = 10

  def Attaque (ennemy: GameEntity) : Int = {
    var degats = 0

    // attack 1
    val att1 = Random.nextInt(20)+1 //random number 1 to 20
    if ((att1 == 20)) { //critical hit : degat x3
      degats = degats + 3*((Random.nextInt(8) + 1) + 2)
    } else { // normal hit
      if ((att1+6) > ennemy.armor) { // check if attack hit
        degats = degats + (Random.nextInt(8) + 1) + 2
      }
    }

    return degats
  }
}
