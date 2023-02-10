import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.ArrayList;

class YearlyReport {
    FileReader fileReader = new FileReader("Невозможно прочитать файл с годовым отчётом. Возможно файл не находится в нужной директории.");
    ArrayList<YearlyRecord> reports = new ArrayList<>();

    void readReport() {
        List<String> report = fileReader.readFileContents("resources/y.2021.csv");

        for (int i = 1; i < report.size(); i++) {
            String[] entrySplit = report.get(i).split(",");

            YearlyRecord record = new YearlyRecord(
                Integer.parseInt(entrySplit[0]),
                Integer.parseInt(entrySplit[1]),
                Objects.equals(entrySplit[2], "true")
            );

            reports.add(record);
        }
    }

    /** Прибыль */
    int income() {
        int sumIncome = 0;
        int sumConsumption = 0;

        for (YearlyRecord record : reports) {
            if (record.isExpense) {
                sumConsumption += record.amount;
            } else {
                sumIncome += record.amount;
            }
        }

        return sumIncome - sumConsumption;
    }

    /** Средний расход */
    int averageConsumption() {
        int sum = 0;
        int count = 0;

        for (YearlyRecord record : reports) {
            if (record.isExpense) {
                sum += record.amount;
                count++;
            }
        }

        return sum / count;
    }

    /** Средний доход */
    int averageIncome() {
        int sum = 0;
        int count = 0;

        for (YearlyRecord record : reports) {
            if (!record.isExpense) {
                sum += record.amount;
                count++;
            }
        }

        return sum / count;
    }

    void printReport() {
        if (reports.size() == 0) {
            System.out.println("------");
            System.out.println("Отсутствует годовой отчет!");
            System.out.println("------");
            return;
        }

        System.out.println("------");
        System.out.println("Год - 2021");
        System.out.println("Прибыль - " + income());
        System.out.println("Средний расход - " + averageConsumption());
        System.out.println("Средний доход - " + averageIncome());
        System.out.println("------");
    }
}