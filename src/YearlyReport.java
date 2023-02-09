import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.ArrayList;

class YearlyReport {
    FileReader fileReader = new FileReader("Невозможно прочитать файл с годовым отчётом. Возможно файл не находится в нужной директории.");
    ArrayList<HashMap<String, String>> reports = new ArrayList<>();

    void readReport() {
        List<String> report = fileReader.readFileContents("resources/y.2021.csv");

        for (int i = 1; i < report.size(); i++) {
            HashMap<String, String> entry = new HashMap<>();
            String[] entrySplit = report.get(i).split(",");

            entry.put("month", entrySplit[0]);
            entry.put("amount", entrySplit[1]);
            entry.put("isExpense", entrySplit[2]);

            reports.add(entry);
        }
    }

    /** Прибыль */
    int income() {
        int sumIncome = 0;
        int sumConsumption = 0;

        for (HashMap<String, String> report : reports) {
            if (report.get("isExpense") == "true") {
                sumConsumption += Integer.parseInt(report.get("amount"));
            } else {
                sumIncome += Integer.parseInt(report.get("amount"));
            }
        }

        return sumIncome - sumConsumption;
    }

    /** Средний расход */
    int averageConsumption() {
        int sum = 0;

        for (HashMap<String, String> report : reports) {
            if (Objects.equals(report.get("isExpense"), "true")) {
                sum += Integer.parseInt(report.get("amount"));
            }
        }

        return sum / reports.size();
    }

    /** Средний доход */
    int averageIncome() {
        int sum = 0;

        for (HashMap<String, String> report : reports) {
            if (Objects.equals(report.get("isExpense"), "false")) {
                sum += Integer.parseInt(report.get("amount"));
            }
        }

        return sum / reports.size();
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

    HashMap<Integer, HashMap<String, Integer>> convertYearReport() {
        HashMap<Integer, HashMap<String, Integer>> monthDataFromYearReport = new HashMap<>();

        for (HashMap<String, String> month : reports) {
            HashMap<String, Integer> monthData = new HashMap<>();
            int monthNumber = Integer.parseInt(month.get("month"));

            if (monthDataFromYearReport.containsKey(monthNumber)) {
                monthData = monthDataFromYearReport.get(monthNumber);
            } else {
                monthDataFromYearReport.put(monthNumber, monthData);
            }

            if (Objects.equals(month.get("isExpense"), "false")) {
                monthData.put("income", Integer.parseInt(month.get("amount")));
            } else {
                monthData.put("consumption", Integer.parseInt(month.get("amount")));
            }
        }

        return monthDataFromYearReport;
    }
}