import org.apache.hadoop.yarn.util.RackResolver
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD

import org.apache.log4j.{Level, Logger}

object NewTestGraph  {

  def main(args: Array[String]) {

    var fight_ended: Boolean = false

    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[*]")
    val sc = new SparkContext(conf)

    Logger.getLogger(classOf[RackResolver]).getLevel
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

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

    //addMemeberToTeam
    team_alpha.addMemeber(solar)

    val liste_ennemi = List(wr1, wr2)
    team_beta.addMultipleMemeber(liste_ennemi)
    // Create an RDD for the vertices
    val VertexEntity: RDD[(VertexId, GameEntity)] =
      sc.parallelize(Array(
        (1L, wr1), (2L, wr2), (15L, solar)))

    // Create an RDD for edges
    val EdgeDistance: RDD[Edge[Int]] =
      sc.parallelize(Array(
        Edge(1L, 15L, 1),
        Edge(2L, 15L, 1),
        Edge(1L, 2L, 0)
      ))

    // Define a default user in case there are relationship with missing user
    val defaultEntity = solar
    // Build the initial Graph
    var graph = Graph(VertexEntity, EdgeDistance, defaultEntity)
    //Calculate distance
    val dist: RDD[String] =
      graph.triplets.map(triplet =>
        "AVANT : " + triplet.srcAttr.toString + " est " + triplet.attr.toString + " vis à vis de  " + triplet.dstAttr.toString)
    //Print distance
    dist.collect.foreach(println(_))
    //aggregatemessageTest

    while (!fight_ended) {
      val attaqueOrder: VertexRDD[(Int, Int, Int)] = graph.aggregateMessages[(Int, Int, Int)]( // attaque, indice mouvement, Mouvement effectif
        triplet => {
          if (triplet.attr.toString == 1) { // ennemis
            if (triplet.srcAttr.position.Distance((triplet.dstAttr.position)) <= triplet.srcAttr.range) { //if in range
              triplet.sendToDst(triplet.srcAttr.Attaque(triplet.dstAttr), 0, 0)
              println(triplet.srcAttr.toString + " attaque " + triplet.dstAttr.toString)
            } else {
              triplet.sendToSrc(0, triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._1, triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._2)
              println(triplet.srcAttr.toString + " moving to " + triplet.dstAttr.toString)
            }
          } else { // if alliés

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
              val test = triplet.dstAttr.team.getNextTarget()
              if (test != null) {
                //triplet.dstId = test.id_graph
                triplet.dstId = 2L
                triplet.dstAttr = wr2
                //println("New target : " + test.id_graph)
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
          "APRES : " + triplet.srcAttr.toString + " se trouve à un x de " + triplet.srcAttr.health + " vis à vis de  " + triplet.dstAttr.toString)
      //Print distance
      dist2.collect.foreach(println(_))

      graph = graph_modify
    }

  }


}