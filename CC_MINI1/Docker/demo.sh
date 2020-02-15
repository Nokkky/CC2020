cd ${HADOOP_HOME}
bin/hdfs dfs -mkdir /input
bin/hdfs dfs -put testfile/testfile.txt /input
bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar wordcount /input /output
bin/hdfs dfs -cat /output/*