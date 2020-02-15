# MiniProjet1
All jar files are already stored at our virtual machine. You can upload them again or directly run them at our vm.

## Part3
* Step1 sign in to student@IP_ADDRESS

  ```ssh -i key_student student@IP_ADDRESS```
* Step2 run NGram.jar:
 
    ``` hadoop/bin/hadoop jar NGram.jar NGram input3 output3 2```
* Step3 see result:

  ``` hdfs dfs -cat output3/* ```
* Answer: 
!!	2
D!	1
EL	1
HE	1
LD	1
LL	1
LO	1
OR	1
OW	1
RL	1
WO	1


## Part4 Question1
* Step1 sign in to student@IP_ADDRESS

  ``` ssh -i key_student student@IP_ADDRESS ```
* Step2 run LogAnalysis1.jar:
 
  ``` hadoop/bin/hadoop jar LogAnalysis1.jar  input4 output4```
* Step3 see result:

  ```hdfs dfs -cat output4/*```
* Answer:

  ``` /assets/img/home-logo.png	98744```


## Part4 Question2
* Step1 sign in to student@IP_ADDRESS

  ``` ssh -i key_student student@IP_ADDRESS ```
* Step2 run LogAnalysis2.jar:

  ``` hadoop/bin/hadoop jar LogAnalysis2.jar input4 output4_2```
* Step3 see result:

  ```hdfs dfs -cat output4_2/*```
* Answer:

  ```10.153.239.5	547 ```

## Part4 Question3
* Step one run LogAnalysis3.jar:

  ``` hadoop/bin/hadoop jar LogAnalysis3.jar input4 output4_3```
* Step two see result:

  ``` hdfs dfs -cat output4_3/*```
* Answer:

  ``` /assets/css/combined.css	117348 ```

## Part4 Question3
* Step one run LogAnalysis4.jar:

  ``` hadoop/bin/hadoop jar LogAnalysis4.jar input4 output4_4```
* Step two see result:

  ``` hdfs dfs -cat output4_4/*```
* Answer:

  ``` 10.216.113.172	158614 ```

