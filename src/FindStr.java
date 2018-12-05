import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class FindStr {

    private static int BUFFER_SIZE = 128;
    private static int prevline = -1;
    private static int index = 0;
    private static ArrayList<String> result = new ArrayList<String>();

    private static String convert(int line, int pos){ return "[" + line + "," + pos + "]"; }
    //method for find and write substrings indexes

    private static int findSubStrIndexes(String[] splitStrings, String substr, int linecount){
        int positionsv;
        //prevline = linecount;
        for(String str : splitStrings) {
            linecount++;
            if(prevline == linecount) index++;
            else index = 0;

            positionsv = 0;  //find all substrings in line
            while (positionsv != -1) {
                positionsv = str.indexOf(substr, positionsv);
                if (positionsv != -1) {
                    result.add(convert(linecount, positionsv + index * BUFFER_SIZE));
                    positionsv++;
                }
                else break;
            }
            prevline = linecount;
            if(splitStrings.length > 1) linecount++;

        }
        return linecount;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Неверное число аргументов! " +
                    "\nИспользование: file_name substr;" +
                    "\nПример: examplefile.txt bananas - " +
                    "результат - массив индексов всех вхождений bananas в файле" +
                    "\n в формате \"Вхождение на строке \" + line + \" на позиции \" + pos");
            return;
        }
        //init (maybe drop this into initialisation block???)
        int positionsv;
        int linecount = 0;
        byte[] buffer = new byte[BUFFER_SIZE];

        String line;
        String substr = args[1];
        String[] splitStrings;
        String READ_TEXT = "";

        try(FileInputStream fr = new FileInputStream(args[0])){
            while(fr.read(buffer, 0 , BUFFER_SIZE) != -1) {
                line = new String(buffer);
                splitStrings = line.split("\n");

                linecount = FindStr.findSubStrIndexes(splitStrings, substr,linecount - 1);

                READ_TEXT += line;
                buffer = new byte[BUFFER_SIZE]; //recovery buffer for next line

            }
            System.out.println(READ_TEXT);
            System.out.print(result); //print ArrayList with indexes
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println();


    } //main
} //class

