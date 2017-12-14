import scala.util.Random

class Warlord extends GameEntity {
  override var health: Int = 141

  override def armor: Int = 27

  override def attaque: Int = 50

  val range : Double = 10

  def Attaque (ennemy: GameEntity) : Int = {
    var degats = 0

    // attack 1
    val att1 = Random.nextInt(20)+1 //random number 1 to 20
    if ((att1 == 20) || (att1 == 19)) { //critical hit : degat x2 + 2d6
      degats = degats + 2*((Random.nextInt(8) + 1) + 10) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1)
    } else { // normal hit
      if ((att1+20) > ennemy.armor) { // check if attack hit
        degats = degats + (Random.nextInt(8) + 1) + 10
      }
    }

    // attack 2
    val att2 = Random.nextInt(20)+1 //random number 1 to 20
    if ((att2 == 20) || (att2 == 19)) { //critical hit : degat x2 + 2d6
      degats = degats + 2*((Random.nextInt(8) + 1) + 10) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1)
    } else { // normal hit
      if ((att2+15) > ennemy.armor) { // check if attack hit
        degats = degats + (Random.nextInt(8) + 1) + 10
      }
    }

    // attack 3
    val att3 = Random.nextInt(20)+1 //random number 1 to 20
    if ((att3 == 20) || (att3 == 19)) { //critical hit : degat x2 + 2d6
      degats = degats + 2*((Random.nextInt(8) + 1) + 10) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1)
    } else { // normal hit
      if ((att3+10) > ennemy.armor) { // check if attack hit
        degats = degats + (Random.nextInt(8) + 1) + 10
      }
    }

    return degats
  }
}
