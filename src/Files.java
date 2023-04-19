import java.io.*;
import java.util.*;

public class Files {
    public static void main(String[] args) throws IOException {
        // считываю путь к входному файлу из консоли
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите входной файл");
        String filePath = scanner.nextLine();
        scanner.close();

        File file1 = new File(filePath);

        // создаю файл для вывода данных в той же папке где и входной файл
        File file2 = new File(file1.getParent() + "//file2.txt");
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // вывод данных в файл
        PrintStream filePrintStream = new PrintStream(file2);
        filePrintStream.println("1 Итоговый данные по файлу");
        filePrintStream.println("1.1 Полное имя файла: " + file1.getAbsolutePath());
        filePrintStream.println("1.2 Размер файла(байт): " + file1.length());

        // подсчитываю количество символов и слов в файле
        String s;
        String str1="";
        int countWords=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file1))){
           while((s=reader.readLine())!= null){
               String[] array = s.split(" ");
               str1 =str1+s.replace(" ",""); // объединяю строки и удаляю пробелы
               countWords = countWords+ array.length;
           }

            filePrintStream.println("1.4 Общее число символов: "+str1.length());
            filePrintStream.println("1.5 Общее число слов: "+ countWords);
            filePrintStream.println("2. Статистика повторения символов");

            // подсчитываю частоту вхождения каждого символа
            Map<Character, Integer> characterCount = new HashMap<>();
            for (char c : str1.toCharArray()) {
                Integer count = characterCount.get(c);
                characterCount.put(c, count);
                if (count == null) {
                    characterCount.put(c, 1);
                } else {
                    characterCount.put(c, count + 1);
                }
            }
            //делаю сортировку символов по количеству вхождений
            List<Map.Entry<Character,Integer>> list = new ArrayList<>(characterCount.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Character,Integer>>() {
                @Override
                public int compare(Map.Entry<Character, Integer> s1, Map.Entry<Character, Integer> s2) {
                    return s2.getValue() - s1.getValue();
                }
            });
            // прохожу по всем значениям и считаю сколько в % каждого символа
            for(Map.Entry<Character,Integer> entry : list) {
                double percent= (double)entry.getValue()/str1.length()*100;
                filePrintStream.println("Символ: "+entry.getKey() +" число вхождений: "+ entry.getValue()+" в процентах: "+percent);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        filePrintStream.close();
    }
}
