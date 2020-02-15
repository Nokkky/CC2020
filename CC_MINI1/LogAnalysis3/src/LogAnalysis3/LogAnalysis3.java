package LogAnalysis3;

        import org.apache.hadoop.io.IntWritable;
        import org.apache.hadoop.io.LongWritable;
        import org.apache.hadoop.mapreduce.Job;

        import java.io.IOException;
        import java.util.*;

        import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.fs.Path;
        import org.apache.hadoop.io.Text;
        import org.apache.hadoop.mapreduce.Mapper;
        import org.apache.hadoop.mapreduce.Reducer;
        import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
        import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
        import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
        import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;


public class LogAnalysis3 {

    public int maxCount = 0;

    // Mapper  get path record.
    // Class Mapper<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
    public static class TokenizerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        private final IntWritable one = new IntWritable(1);
        private Text path = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            String[] log = line.split(" ");
            path.set(log[6]);
            context.write(path,one);
        }
    }

    // Reducer
    public static class TopPathReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        // output path
        private Text path = new Text();
        // output hit number
        private IntWritable topHit = new IntWritable();
        // intermediate HashMap
        private Map<String, Integer> countMap = new HashMap<String, Integer>();
        // record top hit frequency
        private int maxHit = 0;

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            // find the most hit frequency
            if (sum >= maxHit){
                maxHit = sum;
                countMap.put(key.toString(),sum);

            }
        }

        public void cleanup(Context context) throws IOException, InterruptedException {
            // for key stored at countMap, find key with highest hit frequency.
            for (String key: countMap.keySet()){
                // write path with highest hit frequency
                if (countMap.get(key) == maxHit){
                    path.set(key);
                    topHit.set(maxHit);
                    context.write(path,topHit);
                }
            }
        }
    }

    // Mapper 2 get path count <key, value> pair.
    public static class TopPathCount extends Mapper<LongWritable, Text, Text, IntWritable>{

        private Text path = new Text();
        // Mapper 2
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            String[] path_num = line.split(" ");
            path.set(path_num[0]);
            int num = Integer.parseInt(path_num[1]);

            IntWritable count = new IntWritable(num);
            context.write(path,count);

        }
    }
    // Reducer 1
    public static class CountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

        public Text path = new Text();
        private IntWritable pathCount = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            pathCount.set(sum);
            path.set(key.toString());
//            context.write(key, pathCount);
        }

        public void cleanup(Context context) throws IOException, InterruptedException {

            context.write(path, pathCount);

        }
    }


    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "TopPath");
        job.setJarByClass(LogAnalysis3.class);

        // MapReduce chaining

        // First Mapper, get hit count for each path
        Configuration map1Conf = new Configuration(false);
        ChainMapper.addMapper(job, TokenizerMapper.class, Text.class, IntWritable.class, Text.class, IntWritable.class,  map1Conf);
        // First Reducer, get (path, count) (key, value) pair.
        Configuration reduceConf = new Configuration(false);
        ChainReducer.setReducer(job, TopPathReducer.class, Text.class, IntWritable.class, Text.class, IntWritable.class, reduceConf);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // Set reducer number equal to 1
        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}