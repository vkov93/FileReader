import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FindStr {
    private static int count;
    private static final int BUFFER_SIZE = 8192;
    private static String[] splitStrings;
    private static ArrayList<Entry> result = new ArrayList<>();

    //simple class for save entry point
    private static class Entry{
        private int line;
        private int pos;

        private Entry(int line, int pos){
            this.line = line;
            this.pos = pos;
        }

        int getLine(){
            return line;
        }
        int getPos(){
            return  pos;
        }
        @Override
        public String toString() {
            return "[" + line + "," + pos + "]";
        }
    }

    //method for find and write substrings indexes
    private static Entry findSubStrings(Entry current, String substring){
        int line = current.getLine();
        int pos = current.getPos();

        for(String str : splitStrings){
            //find all substring indexes
            while (pos != -1) {
                pos = str.indexOf(substring, pos);
                if (pos != -1) {
                    result.add(new Entry(line, pos + count * BUFFER_SIZE));
                    pos++;
                }
                else {
                    pos = 0;
                    break;
                }
            }
            if(splitStrings.length != 1) {
                pos = 0;
                line++;
                count = 0;
            }
            else count++;

        }

        current = new Entry(line, pos);
        return current;
    }


    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Неверное число аргументов! " +
                    "\nИспользование: file_name substr;" +
                    "\nПример: examplefile.txt bananas - " +
                    "результат - массив индексов всех вхождений bananas в файле" +
                    "\n в формате \"[line\" , \"pos]");
            return;
        }
        //init (maybe drop this into initialisation block???)
        byte[] buffer = new byte[BUFFER_SIZE];

        String symbols;
        String substring = args[1];

        Entry current = new Entry(0, 0);

        try(FileInputStream fr = new FileInputStream(args[0])){
            while(fr.read(buffer, 0 , BUFFER_SIZE) != -1) {
                symbols = new String(buffer);
                splitStrings = symbols.split("\n");
                current = findSubStrings(current, substring);

                buffer = new byte[BUFFER_SIZE]; //recovery buffer for next symbols

                System.out.print(symbols); //check text
            }
            System.out.print("\n" + result);
        }catch (IOException e){
            e.printStackTrace();
        }
    } //main
} //class

