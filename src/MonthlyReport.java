import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.ArrayList;

class MonthlyReport {
    String[] monthNames = {
        "Январь",
        "Февраль",
        "Март",
        "Апрель",
        "Май",
        "Июнь",
        "Июль",
        "Август",
        "Сентябрь",
        "Октябрь",
        "Ноябрь",
        "Декабрь"
    };
    FileReader fileReader = new FileReader("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
    HashMap<Integer, ArrayList<MonthlyRecord>> reports = new HashMap<>();

    void readAllReport() {
        for (int i = 1; i < 4; i++) {
            List<String> report = fileReader.readFileContents("resources/m.20210" + i + ".csv");
            ArrayList<MonthlyRecord> fileList = new ArrayList<>();

            for (int j = 1; j < report.size(); j++) {
                String[] entrySplit = report.get(j).split(",");

                MonthlyRecord monthlyRecord = new MonthlyRecord(
                    entrySplit[0],
                    Objects.equals(entrySplit[1], "TRUE"),
                    Integer.parseInt(entrySplit[2]),
                    Integer.parseInt(entrySplit[3])
                );

                fileList.add(monthlyRecord);
            }

            reports.put(i, fileList);
        }
    }

    void printAllMonthReports() {
        if (reports.size() == 0) {
            System.out.println("------");
            System.out.println("Отсутствуют месячные отчеты!");
            System.out.println("------");
            return;
        }

        for (int key : reports.keySet()) {
            ArrayList<MonthlyRecord> report = reports.get(key);
            printMonthReport(report, key);
        }
    }

    void printMonthReport(ArrayList<MonthlyRecord> report, int monthNumber) {
        System.out.println("------");
        System.out.println("Месяц - " + monthNames[monthNumber - 1]);
        mostProfitableProduct(report);
        biggestWaste(report);
        System.out.println("------");
    }

    /** Самый прибыльный товар */
    void mostProfitableProduct(ArrayList<MonthlyRecord> report) {
        int maxCost = 0;
        String name = "";

        for (MonthlyRecord record : report) {
            if (!record.isExpense) {
                int cost = record.quantity * record.sumOfOne;

                if (maxCost < cost) {
                    maxCost = cost;
                    name = record.name;
                }
            }
        }

        System.out.println("Самый прибыльный товар - " + name + ": " + maxCost);
    }

    /** Самая большая трата */
    void biggestWaste(ArrayList<MonthlyRecord> report) {
        int maxWaste = 0;
        String name = "";

        for (MonthlyRecord record : report) {
            if (record.isExpense) {
                int sumOfOne = record.sumOfOne * record.quantity;

                if (maxWaste < sumOfOne) {
                    maxWaste = sumOfOne;
                    name = record.name;
                }
            }
        }

        System.out.println("Самая большая трата - " + name + ": " + maxWaste);
    }
}