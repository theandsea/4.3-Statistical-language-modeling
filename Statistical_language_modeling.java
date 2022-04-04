import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Statistical_language_modeling {
    public static void main(String[] args) throws Exception {

        // 4.3(a)
        System.out.println();
        System.out.println();
        System.out.println("===================4.3(a)===================");
        String str = path_str("hw4_vocab.txt");
        str_words(str);
        str = path_str("hw4_unigram.txt");
        str_proba_word(str);

        // 4.3(b)
        System.out.println();
        System.out.println();
        System.out.println("===================4.3(b)===================");
        str = path_str("hw4_bigram.txt");
        str_proba_wordword(str);

        // 4.3(c)
        System.out.println();
        System.out.println();
        System.out.println("===================4.3(c)===================");
        str_calculatejoint_word("The stock market fell by one hundred points last week.");
        str_calculatejoint_wordword("The stock market fell by one hundred points last week.");

        // 4.3(d)
        System.out.println();
        System.out.println();
        System.out.println("===================4.3(d)===================");
        double[] a = str_calculatejoint_word("The sixteen officials sold fire insurance.");
        double[] b = str_calculatejoint_wordword("The sixteen officials sold fire insurance.");

        // 4.3(e)
        System.out.println();
        System.out.println();
        System.out.println("===================4.3(e)===================");
        str_calculatejoint_mix(a, b);


        System.out.println();
        System.out.println();
        System.out.println("===================4.4(a)===================");

    }


    public static String[] words = null;

    public static String[] str_words(String str) {
        words = str.split("\r\n");
        return words;
    }

    public static double[] word_proba = null;
    public static int[] word_count = null;

    public static double[] str_proba_word(String str) {
        String[] numstr = str.split("\r\n");
        word_count = new int[numstr.length];
        word_proba = new double[numstr.length];

        // count
        long sum = 0;
        for (int i = 0, il = numstr.length; i < il; i++) {
            word_count[i] = Integer.parseInt(numstr[i]);
            sum += word_count[i];
        }

        // probability
        double sumdouble = (double) sum;
        for (int i = 0, il = numstr.length; i < il; i++) {
            word_proba[i] = word_count[i] / sumdouble;
        }

        for (int i = 0, il = words.length; i < il; i++) {
            if (words[i].charAt(0) == 'M') {
                System.out.println(words[i] + "\t\t\t" + word_proba[i]);
            }
        }

        return word_proba;
    }


    public static double[][] word_word_proba = null;

    public static double[][] str_proba_wordword(String str) {
        String[] statement = str.split("\r\n");
        word_word_proba = new double[words.length][words.length];
        for (int i = 0, il = statement.length; i < il; i++) {
            String[] tristate = statement[i].split("\t");
            // 0 followed 1.   // 1-indexed so to -1 !!!
            int word0index = Integer.parseInt(tristate[0]) - 1;
            int word1index = Integer.parseInt(tristate[1]) - 1;
            int followcount = Integer.parseInt(tristate[2]);
            word_word_proba[word0index][word1index] = followcount / (double) word_count[word0index];
            /*if(words[word0index].equals("SIXTEEN") ||words[word0index].equals("SOLD")) // for check
                System.out.println(words[word0index]+"  "+words[word1index]);*/
            //System.out.println(words[word0index]+"  "+words[word1index]+"\t"+followcount+"\t"+word_word_proba[word0index][word1index]);
        }

        // printout
        int index = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("THE")) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            // extract it, and sort
            double[] follow_proba = word_word_proba[index].clone();
            int[] follow_index = new int[follow_proba.length];
            for (int i = 0, il = follow_proba.length; i < il; i++) {
                follow_index[i] = i;
                //System.out.println(i+"  "+words[i]+"\t\t"+follow_proba[i]);
            }

            // sort
            int index_temp = 0;
            double proba_temp = 0;
            int l = follow_proba.length;
            for (int i = 0; i < l - 1; i++) {
                for (int j = i + 1; j < l; j++) {
                    if (follow_proba[i] < follow_proba[j]) { // swap
                        proba_temp = follow_proba[i];
                        follow_proba[i] = follow_proba[j];
                        follow_proba[j] = proba_temp;
                        index_temp = follow_index[i];
                        follow_index[i] = follow_index[j];
                        follow_index[j] = index_temp;
                    }
                }
            }
            // printout
            for (int i = 0; i < 15; i++) {
                System.out.println(words[follow_index[i]] + "\t\t" + follow_proba[i]);
            }

        }

        return word_word_proba;
    }


    public static double[] str_calculatejoint_word(String str) throws Exception {
        System.out.println("==================unigram sentence probability========================");
        String sentence = str.toUpperCase();
        if (str.charAt(sentence.length() - 1) == '.') {
            sentence = sentence.substring(0, sentence.length() - 1);
        }
        System.out.println(sentence);
        String[] sentenceword = sentence.split(" ");
        double product = 1;
        double thisproba = 0;
        double[] prob = new double[sentenceword.length + 1];
        for (int i = 0; i < sentenceword.length; i++) {
            System.out.print(sentenceword[i] + "\t\t");
            thisproba = 0;
            for (int j = 0; j < words.length; j++) {
                if (sentenceword[i].equals(words[j])) {
                    thisproba = word_proba[j];
                    break;
                }
            }
            if (thisproba == 0) {
                throw new Exception("error not find___" + sentenceword[i]);
            } else {
                product *= thisproba;
                prob[i] = thisproba;
            }
            System.out.println(thisproba);
        }

        System.out.println("the probability for the sentence in unigram  \"" + str + "\"  is");
        System.out.println(Math.log(product));
        prob[prob.length - 1] = product;
        return prob;
    }


    public static double[] str_calculatejoint_wordword(String str) throws Exception {
        System.out.println("==================bigram sentence probability========================");
        String sentence = str.toUpperCase();
        if (str.charAt(sentence.length() - 1) == '.') {
            sentence = sentence.substring(0, sentence.length() - 1);
        }
        System.out.println(sentence);
        String[] sentenceword = sentence.split(" ");

        // special for <S>
        int index_start = -1;
        for (int j = 0; j < words.length; j++) {
            if ("<s>".equals(words[j])) {
                index_start = j;
                break;
            }
        }
        if (index_start == -1) {
            throw new Exception("error not find___<S>");
        }

        // find all the index
        int[] index = new int[sentenceword.length];
        for (int i = 0; i < sentenceword.length; i++) {
            index[i] = -1;
            for (int j = 0; j < words.length; j++) {
                if (sentenceword[i].equals(words[j])) {
                    index[i] = j;
                    break;
                }
            }
            if (index[i] == -1) {
                throw new Exception("error not find___" + sentenceword[i]);
            }
            System.out.println(sentenceword[i] + "\t\t" + index[i]);
        }


        // calculate
        double product = 1;
        double[] proba = new double[sentenceword.length + 1];
        double thisproba = 0;
        for (int i = 0; i < sentenceword.length; i++) {
            if (i == 0) { // word|<s>
                thisproba = word_word_proba[index_start][index[i]];
                System.out.printf("Pb(%s|%s)=", words[index[i]], words[index_start]);
                System.out.println(thisproba);
            } else {
                thisproba = word_word_proba[index[i - 1]][index[i]];
                System.out.printf("Pb(%s|%s)=", words[index[i]], words[index[i - 1]]);
                System.out.println(thisproba);
            }
            product *= thisproba;
            proba[i] = thisproba;
        }

        System.out.println("the probability for the sentence in bigram  \"" + str + "\"  is");
        System.out.println(Math.log(product));
        proba[proba.length - 1] = product;
        return proba;
    }


    public static double[][] str_calculatejoint_mix(double[] a, double[] b) throws IOException {
        System.out.println("==================plot for sentence probability in mixture model========================");

        int l = a.length - 1; // the last one is product
        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        double dat = 0.01;
        for (double lamda = dat; lamda < 1.0; lamda += dat) {
            double proba_mix = 0;
            double product = 1;
            for (int i = 0; i < l; i++) {
                proba_mix = lamda * a[i] + (1 - lamda) * b[i];
                product *= proba_mix;
            }
            System.out.println(lamda + "___" + Math.log(product));
            x.add(lamda);
            y.add(Math.log(product));
        }

        double[][] xy = new double[2][x.size()];
        for (int i = 0, il = x.size(); i < il; i++) {
            xy[0][i] = x.get(i);
            xy[1][i] = y.get(i);
        }

        // plot
        plot.xy_draw_plot(xy, "plot for sentence probability in mixture model", "lamda", "Lm");


        return xy;
    }


    public static String path_str(String path) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String s = null;
        while ((s = bf.readLine()) != null) {//
            buffer.append(s.trim() + "\r\n");
        }

        return buffer.toString();
    }
}
