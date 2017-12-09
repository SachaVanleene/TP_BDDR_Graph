import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD


object TestGraph  {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[*]")
    val sc = new SparkContext(conf)
    //crete all gameEntity ( vertex)
    //Solar
    val solar = new Solar
    solar.setPosition(0, 20, 0)
    //Worgs_riders
    val wr1 = new Worgs_Riders
    wr1.setPosition(105, 35, 0)

    val wr2 = new Worgs_Riders
    wr2.setPosition(100, 32, 0)

    val wr3 = new Worgs_Riders
    wr3.setPosition(100, 30, 0)

    val wr4 = new Worgs_Riders
    wr4.setPosition(108, 22, 0)

    val wr5 = new Worgs_Riders
    wr5.setPosition(110, 20, 0)

    val wr6 = new Worgs_Riders
    wr6.setPosition(110, 18, 0)

    val wr7 = new Worgs_Riders
    wr7.setPosition(100, 11, 0)

    val wr8 = new Worgs_Riders
    wr8.setPosition(111, 10, 0)

    val wr9 = new Worgs_Riders
    wr9.setPosition(110, 8, 0)

    //Barbare_orc
    val bo1 = new Barbares_Orc
    bo1.setPosition(130, 22, 0)

    val bo2 = new Barbares_Orc
    bo2.setPosition(130, 20, 0)

    val bo3 = new Barbares_Orc
    bo3.setPosition(130, 18, 0)

    val bo4 = new Barbares_Orc
    bo4.setPosition(130, 16, 0)

    //Warlord
    val warlord = new Warlord
    warlord.setPosition(140, 19, 0)


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
        Edge(14L, 15L, warlord.position.Distance(solar.position))
       // Edge(15L,1L,solar.position.Distance((wr1.position)))
      ))

    // Define a default user in case there are relationship with missing user
    val defaultEntity = solar
    // Build the initial Graph
    val graph = Graph(VertexEntity, EdgeDistance, defaultEntity)
    //Calculate distance
    val dist: RDD[String] =
      graph.triplets.map(triplet =>
        "AVANT" + triplet.srcAttr.toString + " se trouve à une distance de " + triplet.attr.toString + " vis à vis de  " + triplet.dstAttr.toString)
    //Print distance
    dist.collect.foreach(println(_))
    //aggregatemessageTest
    val attaqueOrder: VertexRDD[(Int,Int,Int)] = graph.aggregateMessages[(Int,Int,Int)]( // attaque, indice mouvement, Mouvement effectif
      triplet => {
        if (triplet.srcAttr.position.Distance((triplet.dstAttr.position)) <= triplet.srcAttr.range) { //if in range
          triplet.sendToDst(triplet.srcAttr.attaque,0,0)
          println(triplet.srcAttr.toString + "attaque" + triplet.dstAttr.toString)
          //println("lol")
        } else {
          triplet.sendToSrc(0,triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._1,triplet.srcAttr.MovingToTarget(triplet.dstAttr.position)._2)
          println(triplet.srcAttr.toString + "moving to " + triplet.dstAttr.toString)
        }
      },
      (a, b) => (a._1+b._1,a._2+b._2,a._3+b._3)
    )

    val newgraph = graph.joinVertices(attaqueOrder) (
      (a,b,c) => {
            b.Update(c._1,c._2,c._3)
      }

    )



    val attaque: VertexRDD[String] =
      attaqueOrder.mapValues {
        a => a.toString
      }

    attaque.collect.foreach(println(_))

    val dist2: RDD[String] =
      newgraph.triplets.map(triplet =>
        "APRES" + triplet.srcAttr.toString + " se trouve à une distance de " + triplet.srcAttr.position.Distance(triplet.dstAttr.position)+ " vis à vis de  " + triplet.dstAttr.toString)
    //Print distance
    dist2.collect.foreach(println(_))
  }
}
