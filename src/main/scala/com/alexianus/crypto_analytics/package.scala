package com.alexianus

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

package object crypto_analytics {
  case class Trade(source: String, timestamp: Long, pair: String, size: Double, price: Double, side: String)
  case class Rate(timestamp: Long, pair: String, price: Double)
}
