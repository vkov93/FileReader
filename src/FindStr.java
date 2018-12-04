import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class FindStr {
    private static int BUFFER_SIZE = 128;

    private static String convert(int line, int pos){
        return "[" + line + "," + pos + "]";
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
        byte[] check = new byte[BUFFER_SIZE];

        ArrayList<String> result = new ArrayList<String>();

        String line;
        String substr = args[1];
        String[] subStrings;
        //String READ_TEXT = "";  //instrument for reading text

        try(FileInputStream fr = new FileInputStream(args[0])){
            for(;;) {

                fr.read(buffer, 0 , BUFFER_SIZE);
                if(Arrays.equals(buffer, check)) break; //when read(buffer) is empty (EOF) - break all
                line = new String(buffer);
                subStrings = line.split("\n"); //split line
                for(int i = 0; i < subStrings.length; i++) {
                    positionsv = 0;  //find all substrings in line
                    while (positionsv != -1) {
                        positionsv = subStrings[i].indexOf(substr, positionsv);
                        if (positionsv != -1) result.add(convert(linecount, positionsv++));
                        else break;
                    }
                    linecount++; //increase line index
                }
                //READ_TEXT += line + '\n';
                buffer = new byte[BUFFER_SIZE]; //recovery buffer
                if(subStrings.length == 1) linecount--;
            }

            //System.out.println(READ_TEXT);
            System.out.print(result); //print ArrayList with indexes
        }catch (IOException e){
            e.printStackTrace();
        }

    } //main
} //class

