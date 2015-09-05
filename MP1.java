import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now", " "};
    List< String > stopWordsList = Arrays.asList( stopWordsArray );
    Set<String> stopWordsSet = new HashSet<String>(stopWordsList);

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }


    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }


    public String[] process() throws Exception {
        String[] ret = new String[20];
       
        //TODO

        // obtain the set of indices
        Integer[] indicees = getIndexes();

        // load the text file of titles
        String[] titles = new String[50000];
        int j = 0;
        Scanner fileReader = new Scanner(new File(inputFileName));
        while (fileReader.hasNext()){
            titles[j++] = fileReader.nextLine();
        }

        // set up a way to store word popularity information
        Map<String, Integer> wordCountMap = new HashMap<String, Integer>();

        // loop through the indexed titles
        String singleTitle;
        String [] titleTokens;
        for(Integer thisTitle : indicees)
        {
            singleTitle = titles[thisTitle];
        // trim whitespace and lower the case of the title
            singleTitle.trim();
            singleTitle.toLowerCase();
        // separate it into words
            titleTokens = singleTitle.split("[\\t\\,;\\.\\?!\\-:@\\[\\]\\(\\)\\{\\}_\\*/\\s]");
        // loop through the words
            for(String token : titleTokens)
            {
                // if the word is not a stop word, update its popularity
                if (stopWordsSet.contains(token)) continue;
                if (wordCountMap.containsKey(token))
                {
                    int tempCount = wordCountMap.get(token);
                    wordCountMap.put(token, tempCount+1);
                }
                else
                {
                    wordCountMap.put(token, 1);
                }
            }
        }

        // place the top 20 words into the 'ret' array
         Set< String > wordsFound = wordCountMap.keySet();

        List<String> wordsFoundList = new ArrayList<String>(wordCountMap.keySet());
        List<Integer> wordsFoundCounts = new ArrayList<Integer>(wordCountMap.values());
        Collections.sort(wordsFoundList);
        Collections.sort(wordsFoundCounts);

        LinkedHashMap sortedWordsFound = new LinkedHashMap();
        Iterator valueIt = wordsFoundCounts.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = wordsFoundList.iterator();
            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = wordCountMap.get(key);
                Integer comp2 = val.toString();
                if (comp1.equals(comp2)){
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put((String)key, (Double)val);
                    break;
                }

       }

   }

        TreeSet< String > sortedWords = new TreeSet< String >(wordsFound);
        System.out.println( "\nMap contains:\nKey\t\tValue" );


        for ( String key : sortedWords )
        System.out.printf( "%-10s%10s\n", key, wordCountMap.get( key ) );
        // create HashMap to store String keys and Integer value
        System.out.printf(
         "\nsize: %d\nisEmpty: %b\n", wordCountMap.size(), wordCountMap.isEmpty() );

        for (int i = 0; i < 20; i++)
        {

        }

        return ret;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
