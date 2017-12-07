
class Position(var x : Int, var y : Int, var z : Int) extends java.io.Serializable{

  def Distance (position: Position) : Double = {
    return Math.sqrt(  Math.pow((position.x-this.x),2) + Math.pow((position.y-this.y)^2,2) + Math.pow((position.z-this.z),2)  )
  }
}
