abstract class GameEntity extends java.io.Serializable{
  def armor : Int
  def health : Int
  def attaque : Int
  var position: Position = new Position(0, 0, 0)
  def setPosition(x : Int, y : Int, z : Int) : Unit = {
    position = new Position (x,y,z)
  }
}
