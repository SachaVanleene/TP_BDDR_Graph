import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD


object TestGraph  {

  def main(args: Array[String]) {

    var fight_ended: Boolean = false

    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //def team
    val team_alpha = new Team
    team_alpha.setName("Alpha")

    val team_beta = new Team
    team_beta.setName("Beta")

    //crete all gameEntity ( vertex)
    //Solar
    val solar = new Solar
    solar.setPosition(0, 20, 0)
    solar.setId(15L)
    //Worgs_riders
    val wr1 = new Worgs_Riders
    wr1.setPosition(105, 35, 0)
    wr1.setId(1L)

    val wr2 = new Worgs_Riders
    wr2.setPosition(100, 32, 0)
    wr1.setId(2L)

    val wr3 = new Worgs_Riders
    wr3.setPosition(100, 30, 0)
    wr1.setId(3L)

    val wr4 = new Worgs_Riders
    wr4.setPosition(108, 22, 0)
    wr1.setId(4L)

    val wr5 = new Worgs_Riders
    wr5.setPosition(110, 20, 0)
    wr1.setId(5L)

    val wr6 = new Worgs_Riders
    wr6.setPosition(110, 18, 0)
    wr1.setId(6L)

    val wr7 = new Worgs_Riders
    wr7.setPosition(100, 11, 0)
    wr1.setId(7L)

    val wr8 = new Worgs_Riders
    wr8.setPosition(111, 10, 0)
    wr1.setId(8L)

    val wr9 = new Worgs_Riders
    wr9.setPosition(110, 8, 0)
    wr1.setId(9L)

    //Barbare_orc
    val bo1 = new Barbares_Orc
    bo1.setPosition(130, 22, 0)
    wr1.setId(10L)

    val bo2 = new Barbares_Orc
    bo2.setPosition(130, 20, 0)
    wr1.setId(11L)

    val bo3 = new Barbares_Orc
    bo3.setPosition(130, 18, 0)
    wr1.setId(12L)

    val bo4 = new Barbares_Orc
    bo4.setPosition(130, 16, 0)
    wr1.setId(13L)

    //Warlord
    val warlord = new Warlord
    warlord.setPosition(140, 19, 0)
    wr1.setId(14L)

    //addMemeberToTeam
    team_alpha.addMemeber(solar)

    val liste_ennemi = List(wr1, wr2, wr3, wr4, wr5, wr6, wr7, wr8, wr9, bo1, bo2, bo3, bo4, warlord)
    team_beta.addMultipleMemeber(liste_ennemi)
    // Create an RDD for the vertices
    val VertexEntity: RDD[(VertexId, GameEntity)] =
      sc.parallelize(Array(
        (1L, wr1), (2L, wr2), (3L, wr3), (4L, wr4), (5L, wr5), (6L, wr6), (7L, wr7), (8L, wr8), (9L, wr9)
        , (10L, bo1), (11L, bo2), (12L, bo3), (13L, bo4)
        , (14L, warlord)
        , (15L, solar)))

    // Create an RDD for edges
    val EdgeDistance: RDD[Edge[Double]] =
      sc.parallelize(Array(
        Edge(1L, 15L, wr1.position.Distance(solar.position)),
        Edge(2L, 15L, wr2.position.Distance(solar.position)),
        Edge(3L, 15L, wr3.position.Distance(solar.position)),
        Edge(4L, 15L, wr4.position.Distance(solar.position)),
        Edge(5L, 15L, wr5.position.Distance(solar.position)),
        Edge(6L, 15L, wr6.position.Distance(solar.position)),
        Edge(7L, 15L, wr7.position.Distance(solar.position)),
        Edge(8L, 15L, wr8.position.Distance(solar.position)),
        Edge(9L, 15L, wr9.position.Distance(solar.position)),
        Edge(10L, 15L, bo1.position.Distance(solar.position)),
        Edge(11L, 15L, bo2.position.Distance(solar.position)),
        Edge(12L, 15L, bo3.position.Distance(solar.position)),
        Edge(13L, 15L, bo4.position.Distance(solar.position)),
        Edge(14L, 15L, warlord.position.Distance(solar.position)),
        Edge(15L, 1L, solar.position.Distance((wr1.position)))
      ))

    // Define a default user in case there are relationship with missing user
    val defaultEntity = solar
    // Build the initial Graph
    var graph = Graph(VertexEntity, EdgeDistance, defaultEntity)
    //Calculate distance
    val dist: RDD[String] =
      graph.triplets.map(triplet =>
        "AVANT : " + triplet.srcAttr.toString + " se trouve à une distance de " + triplet.attr.toString + " vis à vis de  " + triplet.dstAttr.toString)
    //Print distance
    dist.collect.foreach(println(_))
    //aggregatemessageTest

    while (!fight_ended) {
      val attaqueOrder: VertexRDD[(Int, Int, Int)] = graph.aggregateMessages[(Int, Int, Int)]( // attaque, indice mouvement, Mouvement effectif
        triplet => {
          if (triplet.srcAttr.position.Distance((triplet.dstAttr.position)) <= triplet.srcAttr.range) { //if in range
            triplet.sendToDst(triplet.srcAttr.Attaque(triplet.dstAttr), 0, 0)
            println(triplet.srcAttr.toString + " attaque " + triplet.dstAttr.toString)
          } else {
            triplet.sendToSrc(0, triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._1, triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._2)
            println(triplet.srcAttr.toString + " moving to " + triplet.dstAttr.toString)
          }
        },
        (a, b) => (a._1 + b._1, a._2 + b._2, a._3 + b._3)
      )


      val newgraph = graph.joinVertices(attaqueOrder)(
        //(a,b,c) => b.Update(c._1,c._2,c._3)
        (a, b, c) => b.Update(a, c._1, c._2, c._3)
      )

      val newtarget = newgraph.mapTriplets( // Modify target if possible
        triplet => {
          if (triplet.srcAttr.health > 0) {
            if (triplet.dstAttr.health < 0) {
              val test = triplet.srcAttr.team.getNextTarget(triplet.dstAttr.team)
              if (test != null) {
                triplet.dstId = test.id_graph
              } else {
                fight_ended = true
              }
            } else {
              //
            }
          } else {
            //dont send msg to delete this vertice
          }
        }
      )
      //Send msg to people that are still alive
      val checkTarget: VertexRDD[(Boolean)] = newtarget.aggregateMessages[(Boolean)]( //(ABesoinDeChanger,Atrouverunetarget,IdTarget)
        triplet => {
          if (triplet.srcAttr.health > 0) {
            triplet.sendToSrc(true)
          }
        },
        (a, b) => a && b

      )


      val graph_modify = newgraph.joinVertices(checkTarget)( // delete entity using join
        (a, b, c) => b
      )

      // AFFICHAGE
      val attaque: VertexRDD[String] =
        attaqueOrder.mapValues {
          a => a.toString
        }

      attaque.collect.foreach(println(_))

      val dist2: RDD[String] =
        newgraph.triplets.map(triplet =>
          //"APRES" + triplet.srcAttr.toString + " se trouve à une distance de " + triplet.srcAttr.position.Distance(triplet.dstAttr.position) + " vis à vis de  " + triplet.dstAttr.toString)
          "APRES : " + triplet.srcAttr.toString + " se trouve à un x de " + triplet.srcAttr.position.x.toString() + " vis à vis de  " + triplet.dstAttr.toString)
      //Print distance
      dist2.collect.foreach(println(_))

      graph = graph_modify
    }

  }


}
