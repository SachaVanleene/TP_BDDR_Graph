 class Team extends  java.io.Serializable{


  var member : List[GameEntity] = List[GameEntity]()
  var name : String = null

  def setName (string: String) : Unit = {
      name = string
  }

  def addMemeber (gameEntity: GameEntity) : Unit = {
    member = gameEntity :: member
    gameEntity.setTeam(this)
    println("Ajout de machin avec ca de hp " + gameEntity.health)
  }

    def addMultipleMemeber(liste : List[GameEntity]) : Unit = {
      liste.foreach( g=>addMemeber(g))
    }


  def getNextTarget() : GameEntity = {
    val listepotentielle  : List[GameEntity] = member.filter(p=> p.health>0 )
    if (listepotentielle.isEmpty){
      return null
    } else {
      listepotentielle(0)
    }
  }
}
