# spark-streaming
Spark Streamingのサンプル

## 実行方法
```
$ sbt assembly #コンパイル(依存ライブラリも全部含めたjarを生成する)
$ spark-submit --master local --class Main [実行可能なjarを選択]
```
