
class Position(var x : Int, var y : Int, var z : Int) extends java.io.Serializable{

  def Distance (position: Position) : Double = {
    return Math.sqrt(  Math.pow((position.x-this.x),2) + Math.pow((position.y-this.y)^2,2) + Math.pow((position.z-this.z),2)  )
  }

  def Sens(vennemie : Int, indice : Int) : Int = {
    indice match {
      case 1 =>
        if(vennemie - this.x > 0){
          return 1
        } else {
          return - 1
        }
      case 2 =>
        if(vennemie - this.y > 0){
          return 1
        } else {
          return -1
        }
      case 3 =>
        if(vennemie - this.z > 0){
          return 1
        } else {
          return -1
        }
    }

  }
}
