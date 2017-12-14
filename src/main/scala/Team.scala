 class Team extends  java.io.Serializable{


  var member : List[GameEntity] = List[GameEntity]()
  var name : String = null

  def setName (string: String) : Unit = {
      name = string
  }

  def addMemeber (gameEntity: GameEntity) : Unit = {
    member = gameEntity :: member
    gameEntity.setTeam(this)
  }

    def addMultipleMemeber(liste : List[GameEntity]) : Unit = {
      liste.foreach( g=>addMemeber(g))
    }


  def getNextTarget(team: Team) : GameEntity = {
    val listepotentielle  : List[GameEntity] = team.member.filter(p=>p.health>0)
    if (listepotentielle == null){
      return null
    } else {
      listepotentielle(0)
    }
  }
}
