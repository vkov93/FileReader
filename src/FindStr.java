import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class FindStr {
    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("Неверное число аргументов! " +
                    "\nИспользование: file_name substr;" +
                    "\nПример: examplefile.txt bananas - " +
                    "результат - массив индексов всех вхождений bananas в файле" +
                    "\n в формате \"Вхождение на строке \" + line + \" на позиции \" + pos");
            return;
        }
        File file = new File(args[0]);
        String substr = args[1];
        Scanner sc = null;
        int linecount = 0;
        int i = 0;

        class Info{
            int pos;
            int line;
            Info(int pos, int line){
                this.pos = pos;
                this.line = line;
            }

            @Override
            public String toString() {
                String str = "Вхождение на строке " + line + " на позиции " + pos;
                return str;
            }
        }

        ArrayList<Info> result = new ArrayList<Info>();

        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
                if(line.indexOf(substr) != -1){
                    result.add(new Info(line.indexOf(substr), linecount));
                }
                linecount++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            sc.close();

            System.out.println(result);
        }
    }

}
