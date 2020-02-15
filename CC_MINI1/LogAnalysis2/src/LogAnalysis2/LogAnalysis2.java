package LogAnalysis2;


        import java.io.IOException;
        import java.util.*;

        import org.apache.hadoop.fs.Path;
        import org.apache.hadoop.conf.*;
        import org.apache.hadoop.io.*;
        import org.apache.hadoop.mapred.*;
        import org.apache.hadoop.mapreduce.Job;
        import org.apache.hadoop.util.*;


public class LogAnalysis2 {


    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

            String line = value.toString();
            String[] log = line.split(" ");
            if (log[0].equals("10.153.239.5")){
                word.set(log[0]);
                output.collect(word,one);
            }

        }
    }
x
    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{

        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException{

            int sum = 0;
            while (values.hasNext()){
                sum += values.next().get();
            }

            output.collect(key, new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception{

        JobConf conf = new JobConf(LogAnalysis2.class);
        conf.setJobName("LogAnalysis2");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        conf.setNumReduceTasks(1);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);

    }
}