package jp.microad.track.streaming

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils

object Main {

  def main(args: Array[String]): Unit = {

    // scはspark-shellを起動時に生成されるSparkContext(入力データ)のこと.
    // Spark Streamingでは入力ストリームデータを定義する必要がある
    // 今回は10秒毎にバッチ処理
    // spark-shellの場合, SparkContextはsc変数で予め生成されている
    val sparkConf = new SparkConf()
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    // DStreamの定義
    // StorageLevelによって，ストリームデータの冗長化を行うが，今回は必要ないので，最も低いレベルを設定
    val lines = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_AND_DISK_SER)

    // スペース区切りで分割
    val words = lines.flatMap(_.split(" "))

    // 単語毎にグルーピング
    val pairs = words.map((_, 1))
    val wordCounts = pairs.reduceByKey(_+_)

    // 結果の出力
    wordCounts.print()

    // ストリーム処理の開始
    ssc.start()
    ssc.awaitTermination()
  }
}

class HelloActor extends Actor {

  override def receive: Receive = {
    case _ => println("hello")
  }
}

