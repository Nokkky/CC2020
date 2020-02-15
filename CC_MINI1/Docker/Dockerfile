# Basic Image
FROM ubuntu:16.04

# Maintainer
MAINTAINER noky2020

# User
USER root

# Environment
WORKDIR /usr/local
ENV HADOOP_HOME /usr/local/hadoop

# Run commands to set up the hadoop system
RUN apt-get update
RUN apt-get install -y wget ssh rsync openjdk-8-jdk pdsh
# RUN apt-get remove -y ssh openssh-server openssh-client
RUN apt-get install -y openssh-server openssh-client

RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
RUN cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
RUN chmod 0600 ~/.ssh/authorized_keys

RUN cd /usr/local/
RUN wget http://mirrors.gigenet.com/apache/hadoop/common/hadoop-3.2.1/hadoop-3.2.1.tar.gz
RUN tar -xzf hadoop-3.2.1.tar.gz
RUN ln -s hadoop-3.2.1 hadoop
RUN cd ${HADOOP_HOME}
RUN mkdir testfile

COPY hadoop-env.sh ${HADOOP_HOME}/etc/hadoop/

COPY testfile.txt ${HADOOP_HOME}/testfile/


RUN ${HADOOP_HOME}/bin/hdfs namenode -format

COPY bootstrap.sh /root/
# Change File Mode For All
RUN chmod a+x /root/bootstrap.sh

CMD [ "/root/bootstrap.sh" ]