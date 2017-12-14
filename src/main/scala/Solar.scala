import scala.util.Random

class Solar extends GameEntity {
  override var health: Int = 363

  override def armor: Int = 44

  override def attaque: Int = 10

  val range : Double = 110

  def Attaque (ennemy: GameEntity) : Int = {
    val distance = position.Distance(ennemy.position)
    var degats = 0

    if (distance <= 10) { // melee attack
      // attack 1
      val att1 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att1 == 20) || (att1 == 19)) { //critical hit : degat x2
        degats = degats + 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18)
      } else { // normal hit
        if ((att1+35) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18
        }
      }

      // attack 2
      val att2 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att2 == 20) || (att2 == 19)) { //critical hit : degat x2
        degats = degats + 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18)
      } else { // normal hit
        if ((att2+30) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18
        }
      }

      // attack 3
      val att3 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att3 == 20) || (att3 == 19)) { //critical hit : degat x2
        degats = degats + 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18)
      } else { // normal hit
        if ((att3+25) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18
        }
      }

      // attack 4
      val att4 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att4 == 20) || (att4 == 19)) { //critical hit : degat x2
        degats = degats + 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18)
      } else { // normal hit
        if ((att4+20) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18
        }
      }
    } else { // range attack
      // attack 1
      val att1 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att1 == 20)) { //critical hit : degat x3
        degats = degats + 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
      } else { // normal hit
        if ((att1+31) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
        }
      }

      // attack 2
      val att2 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att2 == 20)) { //critical hit : degat x3
        degats = degats + 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
      } else { // normal hit
        if ((att2+26) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
        }
      }

      // attack 3
      val att3 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att3 == 20)) { //critical hit : degat x3
        degats = degats + 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
      } else { // normal hit
        if ((att3+21) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
        }
      }

      // attack 4
      val att4 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att4 == 20)) { //critical hit : degat x3
        degats = degats + 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
      } else { // normal hit
        if ((att4+16) > ennemy.armor) { // check if attack hit
          degats = degats + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
        }
      }
    }

    return degats
  }
}
