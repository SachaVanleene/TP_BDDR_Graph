import org.apache.spark.graphx.VertexId

import scala.util.Random

class Barbares_Orc extends GameEntity {
  override var health: Int = 142

  override def armor: Int = 17

  override val  max_health : Int = 142

  def damage_reduction : Int = 0

  override def regeneration: Int =  0

  override def attaque: Int = 50

  val range : Double = 10

  def Update (vertexId: VertexId, attaque : Int, indice : Int, mvt : Int) : GameEntity = {
    val new_entity = new Barbares_Orc()
    new_entity.id_graph = this.id_graph
    new_entity.health = this.health
    new_entity.position = this.position

    new_entity.health = this.reduceHealth(attaque)
    this.health -= attaque
    new_entity.position = this.position

    /*if (this.health < 0) {
      this.team.deleteMember(this)
    }
    
    new_entity.team = this.team*/

    indice match {
      case 1 => new_entity.position.x = new_entity.position.x + mvt
      case 2 => new_entity.position.y = new_entity.position.y + mvt
      case 3 => new_entity.position.z = new_entity.position.z + mvt
    }
    //println("ID : " + vertexId + " et " + this.position.x)
    return new_entity
  }

  def Attaque (ennemy: GameEntity) : Int = {
    var degats = 0

    // attack 1
    val att1 = Random.nextInt(20)+1 //random number 1 to 20
    if ((att1 == 20) || (att1 == 19)) { //critical hit : degat x2 + 2d6
      degats = degats + 3*((Random.nextInt(8) + 1) + 10)
    } else { // normal hit
      if ((att1+19) > ennemy.armor) { // check if attack hit
        degats = degats + (Random.nextInt(8) + 1) + 10
      }
    }

    // attack 2
    val att2 = Random.nextInt(20)+1 //random number 1 to 20
    if ((att2 == 20) || (att2 == 19)) { //critical hit : degat x2 + 2d6
      degats = degats + 3*((Random.nextInt(8) + 1) + 10)
    } else { // normal hit
      if ((att2+14) > ennemy.armor) { // check if attack hit
        degats = degats + (Random.nextInt(8) + 1) + 10
      }
    }

    // attack 3
    val att3 = Random.nextInt(20)+1 //random number 1 to 20
    if ((att3 == 20) || (att3 == 19)) { //critical hit : degat x2 + 2d6
      degats = degats + 3*((Random.nextInt(8) + 1) + 10)
    } else { // normal hit
      if ((att3+9) > ennemy.armor) { // check if attack hit
        degats = degats + (Random.nextInt(8) + 1) + 10
      }
    }

    return degats
  }
}
