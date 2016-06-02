package com.alexianus.crypto_analytics

object VolumeAggregator extends TradeAnalysis {
  trades.map(t => (t.pair, t.size)).reduceByKey(_ + _).coalesce(1).saveAsTextFile(args(2))
}
