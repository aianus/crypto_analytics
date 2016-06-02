package com.alexianus.crypto_analytics

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.collection.mutable.ListBuffer

trait TradeAnalysis extends DelayedInit {
  /** The command line arguments passed to the application's `main` method.
    */
  protected def args: Array[String] = _args
  private var _args: Array[String] = _

  private val initCode = new ListBuffer[() => Unit]
  /** The init hook. This saves all initialization code for execution within `main`.
    *  This method is normally never called directly from user code.
    *  Instead it is called as compiler-generated code for those classes and objects
    *  (but not traits) that inherit from the `DelayedInit` trait and that do not
    *  themselves define a `delayedInit` method.
    *  @param body the initialization code to be stored for later execution
    */
  @deprecated("the delayedInit mechanism will disappear", "2.11.0")
  override def delayedInit(body: => Unit) {
    initCode += (() => body)
  }

  protected def trades: RDD[Trade] = _trades
  private var _trades: RDD[Trade] = _
  protected def rates: RDD[Rate] = _rates
  private var _rates: RDD[Rate] = _

  /** The main method.
    *  This stores all arguments so that they can be retrieved with `args`, creates relevant
    *  and then executes all initialization code segments in the order in which
    *  they were passed to `delayedInit`.
    *  @param args the arguments passed to the main method
    */
  def main(args: Array[String]) = {
    this._args = args

    val conf = new SparkConf()
      .setAppName("Trade Analysis ")
      .set("spark.speculation", "true")
      .set("spark.speculation.interval", "1000ms")

    val sc = new SparkContext(conf)
    sc.hadoopConfiguration.set("mapreduce.input.fileinputformat.input.dir.recursive","true")

    val tradePath = java.net.URLDecoder.decode(args(0), "UTF-8")
    val fxPath = java.net.URLDecoder.decode(args(1), "UTF-8")

    _trades = sc.textFile(tradePath).map{ t =>
      object StringToDouble extends CustomSerializer[Double](format => ({ case JString(x) => x.toDouble }, { case x: Double => JString(x.toString) }))
      implicit val formats = DefaultFormats + StringToDouble
      parse(t).extract[Trade]
    }
    _rates  = sc.textFile(fxPath).map{ r =>
      object StringToDouble extends CustomSerializer[Double](format => ({ case JString(x) => x.toDouble }, { case x: Double => JString(x.toString) }))
      implicit val formats = DefaultFormats + StringToDouble
      parse(r).extract[Rate]
    }

    for (proc <- initCode) proc()
  }
}
