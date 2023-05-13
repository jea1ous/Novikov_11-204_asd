import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.opencsv.CSVWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем дерево
        RedBlackTree tree = new RedBlackTree();

        // Создаем массив из случайной последовательности 10000 целых чисел
        Random random = new Random();
        int[] arr = new int[10000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt();
        }

        // Открываем файл для записи
        CSVWriter csvWriter = new CSVWriter(new FileWriter("results.csv"), ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

        // Записываем заголовок таблицы
        String[] header = {"Insert Index", "Insert Time (ms)", "Insert Operation Count", "Search Index", "Contains Time (ms)", "Contains Operation Count", "Delete Time (ms)", "Delete Operations Count"};
        csvWriter.writeNext(header);

        // Поэлементно добавляем числа в наше дерево методом insert, замеряя время работы и количество операций для каждого добавления
        long totalTime = 0;
        for (int i = 0; i < arr.length; i++) {
            long startTime = System.nanoTime();
            tree.insert(arr[i]);
            int operationCount = tree.getOperationCount();
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;

            totalTime += timeElapsed;

            // Записываем данные о времени работы и количестве операций в CSV-файл
            String[] row = {Integer.toString(i+1), Long.toString(timeElapsed), Integer.toString(operationCount)};
            csvWriter.writeNext(row);
            tree.resetOperationCount();
        }

        for (int i = 0; i < 100; i++) {
            int randomIndex = random.nextInt(10000);
            int randomValue = arr[randomIndex];
            long startSearch = System.nanoTime();
            tree.contains(randomValue);
            int searchCount = tree.getOperationCount();
            long endSearch = System.nanoTime();
            String[] searchData = {"", "", "", String.valueOf(randomIndex), String.valueOf(endSearch - startSearch), String.valueOf(searchCount)};
            try {
                csvWriter.writeNext(searchData);
                tree.resetOperationCount();
                csvWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csvWriter.flush();

        // Случайным образом выбираем из массива 1000 элементов и удаляем их из структуры
        for (int i = 0; i < 1000; i++) {
            int randomValue = arr[random.nextInt(arr.length)];
            long startTime = System.nanoTime();
            tree.remove(randomValue);
            int operationCount = tree.getOperationCount();
            long endTime = System.nanoTime();
            String[] data = {"", "", "", "", "", "", String.valueOf(endTime - startTime), String.valueOf(operationCount)};
            csvWriter.writeNext(data);
            tree.resetOperationCount();
        }
        csvWriter.flush();

        // Закрываем файл
        csvWriter.close();

        // Выводим общее время работы
        System.out.println("Total insertion time: " + totalTime + " ns");
    }
}