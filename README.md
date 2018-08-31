# spark-test
Apache Sparkのサンプル

## Usage
- `sbt assembly`
- 別シェルで`$ nc -lk 9999`を実行してあ立ち上げておく
- `spark-submit --master local[*] --class Main spark/target/scala-2.11/spark_1.0.0.jar`でSparkを起動する
- ncから文字列を送信する
