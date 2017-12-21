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

   def deleteMember(gameEntity: GameEntity) : Unit = {
     println("BEFORE :" + this.member)
     println(gameEntity.id_graph)
     println("AFTER :" + member.filter(_.id_graph != gameEntity.id_graph))
     member = member.filter(_.id_graph != gameEntity.id_graph)
   }


  def getNextTarget() : GameEntity = {
    val listepotentielle  : List[GameEntity] = member.filter(p=> p.health>0 )
    println (listepotentielle(0).health);
    println (listepotentielle);
    if (listepotentielle.isEmpty){
      return null
    } else {
      listepotentielle(0)
    }
  }
}
