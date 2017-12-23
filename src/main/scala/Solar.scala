import org.apache.spark.graphx.VertexId

import scala.util.Random

class Solar extends GameEntity {
  override var health: Int = 363

  override val  max_health : Int = 363

  override def move : Int = 0

  def damage_reduction : Int = 15

  override def regeneration: Int = 15

  override def armor: Int = 44

  override def attaque: Int = 10

  val range : Double = 110

  def Update (vertexId: VertexId, attaque : Int, indice : Int, mvt : Int) : GameEntity = {
    val new_entity = new Solar()
    new_entity.id_graph = this.id_graph
    new_entity.health = this.health
    new_entity.position = this.position

    new_entity.health = this.reduceHealth(attaque)
    this.health -= attaque
    new_entity.position = this.position



    new_entity.team = this.team

    indice match {
      case 0 =>
      case 1 => new_entity.position.x = new_entity.position.x + mvt
      case 2 => new_entity.position.y = new_entity.position.y + mvt
      case 3 => new_entity.position.z = new_entity.position.z + mvt
    }
   // println("ID : " + vertexId + " et " + this.position.x)
    return new_entity
  }

  def Attaque (ennemy: GameEntity) : Int = {
    val distance = position.Distance(ennemy.position)
    var degats = 0

    if (distance <= 10) { // melee attack
      // attack 1
      val att1 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att1 == 20) || (att1 == 19)) { //critical hit : degat x2
        var current_deg = 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18) - ennemy.damage_reduction
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att1+35) > ennemy.armor) { // check if attack hit
          var current_deg = (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18 - ennemy.damage_reduction
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }

      // attack 2
      val att2 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att2 == 20) || (att2 == 19)) { //critical hit : degat x2
        var current_deg = 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18) - ennemy.damage_reduction
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att2+30) > ennemy.armor) { // check if attack hit
          var current_deg = (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18 - ennemy.damage_reduction
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }

      // attack 3
      val att3 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att3 == 20) || (att3 == 19)) { //critical hit : degat x2
        var current_deg = 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18) - ennemy.damage_reduction
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att3+25) > ennemy.armor) { // check if attack hit
          var current_deg =(Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18 - ennemy.damage_reduction
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }

      // attack 4
      val att4 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att4 == 20) || (att4 == 19)) { //critical hit : degat x2
        var current_deg = 2*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18)
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att4+20) > ennemy.armor) { // check if attack hit
          var current_deg = (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 18
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }
    } else { // range attack
      // attack 1
      val att1 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att1 == 20)) { //critical hit : degat x3
        var current_deg = 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att1+31) > ennemy.armor) { // check if attack hit
          var current_deg = (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }

      // attack 2
      val att2 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att2 == 20)) { //critical hit : degat x3
        var current_deg = 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att2+26) > ennemy.armor) { // check if attack hit
          var current_deg = (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }

      // attack 3
      val att3 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att3 == 20)) { //critical hit : degat x3
        var current_deg = 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att3+21) > ennemy.armor) { // check if attack hit
          var current_deg = (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }

      // attack 4
      val att4 = Random.nextInt(20)+1 //random number 1 to 20
      if ((att4 == 20)) { //critical hit : degat x3
        var current_deg = 3*((Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14)
        if (current_deg < 0) {current_deg = 0}
        degats = degats + current_deg
      } else { // normal hit
        if ((att4+16) > ennemy.armor) { // check if attack hit
          var current_deg = (Random.nextInt(6) + 1) + (Random.nextInt(6) + 1) + 14
          if (current_deg < 0) {current_deg = 0}
          degats = degats + current_deg
        }
      }
    }

    return degats
  }
}
