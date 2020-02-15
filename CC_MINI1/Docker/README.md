## Hadoop Docker image

# First step build docker image

## Build the image

```
docker build -t cc2020/hadoop2 .
```


# Check whether the image is created or not by listing the images

```
docker images
```


## Second step start docker container

# Run the built hadoop docker image

```
docker run -i -t cc2020/hadoop2 /root/bootstrap.sh -bash
```

## Test

# Test with wordcount example:

```
cd ${HADOOP_HOME}

# Create hdfs input directory
bin/hdfs dfs -mkdir /input

# Update input file to hdfs
bin/hdfs dfs -put testfile/testfile.txt /input

# Run wordcount example
bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar wordcount /input /output

# See output
bin/hdfs dfs -cat /output/*
```