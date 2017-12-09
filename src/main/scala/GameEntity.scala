abstract class GameEntity extends java.io.Serializable{
  def Update(b: GameEntity): GameEntity = ???


  def Update (attaque : Int, indice : Int, mvt : Int) : GameEntity = {
    this.reduceHealth(attaque)
    this.Move(indice, mvt)
    return this
  }

  def armor : Int
  var health : Int
  def attaque : Int
  val move : Int = 4
  val range : Double = 10
  var position: Position = new Position(0, 0, 0)
  def setPosition(x : Int, y : Int, z : Int) : Unit = {
    position = new Position (x,y,z)
  }

  def MovingToTarget(position: Position): (Int,Int) = {
    val diffx: Int = Math.abs((this.position.x - position.x))
    val diffy: Int = Math.abs((this.position.y - position.y))
    val diffz: Int = Math.abs((this.position.z - position.z))

    if( diffx > diffy) {
      if (diffx > diffz) {
            return(1,move*position.Sens(position.x,1))
      } else {
        return (2, move * position.Sens(position.y, 2))
      }
    }else {
      if(diffy > diffz){
        return (2, move * position.Sens(position.y, 2))
      }else {
        return (3, move * position.Sens(position.z, 3))
      }
    }
  }

  def reduceHealth(degat : Int): Unit ={
    this.health = this.health - degat
  }

  def Move(indice : Int, mvt : Int) : Unit = {
      indice match {
        case 1 => setPosition(position.x + mvt, position.y, position.z)
        case 2 => this.position.y = this.position.y + mvt
        case 3 => this.position.z = this.position.z + mvt
      }

  }
}
