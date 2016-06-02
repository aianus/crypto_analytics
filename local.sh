#!/bin/bash

rm -rf local_out
sbt assembly && \
spark-submit \
    --driver-memory 10g \
    --executor-memory 10g \
    --class com.alexianus.crypto_analytics.VolumeAggregator \
    --master 'local[*]' \
    $(pwd)/target/scala-2.10/crypto_analytics-assembly-1.0.jar \
    $(pwd)/crypto-trade-history/\*/\*/\*/\*/\* \
    $(pwd)/fx-rates/\*/\*/\*/\*/\* \
    $(pwd)/local_out/ \
