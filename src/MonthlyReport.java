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
    HashMap<String, ArrayList<HashMap<String, String>>> reports = new HashMap<>();

    void readAllReport() {
        for (int i = 1; i < 4; i++) {
            List<String> report = fileReader.readFileContents("resources/m.20210" + i + ".csv");
            ArrayList<HashMap<String, String>> fileList = new ArrayList<>();

            for (int j = 1; j < report.size(); j++) {
                HashMap<String, String> entry = new HashMap<>();
                String[] entrySplit = report.get(j).split(",");

                entry.put("name", entrySplit[0]);
                entry.put("isExpense", entrySplit[1]);
                entry.put("quantity", entrySplit[2]);
                entry.put("sumOfOne", entrySplit[3]);

                fileList.add(entry);
            }

            reports.put("0" + i, fileList);
        }
    }

    void printAllMonthReports() {
        if (reports.size() == 0) {
            System.out.println("------");
            System.out.println("Отсутствуют месячные отчеты!");
            System.out.println("------");
            return;
        }

        for (String key : reports.keySet()) {
            ArrayList<HashMap<String, String>> report = reports.get(key);
            printMonthReport(report, key);
        }
    }

    void printMonthReport(ArrayList<HashMap<String, String>> report, String monthNumber) {
        System.out.println("------");
        System.out.println("Месяц - " + monthNames[Integer.parseInt(monthNumber) - 1]);
        mostProfitableProduct(report);
        biggestWaste(report);
        System.out.println("------");
    }

    /** Самый прибыльный товар */
    void mostProfitableProduct(ArrayList<HashMap<String, String>> report) {
        int maxCost = 0;
        String name = "";

        for (HashMap<String, String> entry : report) {
            if (Objects.equals(entry.get("isExpense"), "FALSE")) {
                int quantity = Integer.parseInt(entry.get("quantity"));
                int sumOfOne = Integer.parseInt(entry.get("sumOfOne"));
                int cost = quantity * sumOfOne;

                if (maxCost < cost) {
                    maxCost = cost;
                    name = entry.get("name");
                }
            }
        }

        System.out.println("Самый прибыльный товар - " + name + ": " + maxCost);
    }

    /** Самая большая трата */
    void biggestWaste(ArrayList<HashMap<String, String>> report) {
        int maxWaste = 0;
        String name = "";

        for (HashMap<String, String> entry : report) {
            if (Objects.equals(entry.get("isExpense"), "TRUE")) {
                int sumOfOne = Integer.parseInt(entry.get("sumOfOne"));

                if (maxWaste < sumOfOne) {
                    maxWaste = sumOfOne;
                    name = entry.get("name");
                }
            }
        }

        System.out.println("Самая большая трата - " + name + ": " + maxWaste);
    }

    HashMap<Integer, HashMap<String, Integer>> convertMonthReports() {
        HashMap<Integer, HashMap<String, Integer>> convertMonthReports = new HashMap<>();

        for (String key : reports.keySet()) {
            HashMap<String, Integer> entryData = new HashMap<>();
            int month = Integer.parseInt(key);
            int fullIncome = 0;
            int fullConsumption = 0;

            for (HashMap<String, String> entry : reports.get(key)) {
                if (Objects.equals(entry.get("isExpense"), "TRUE")) {
                    fullConsumption += Integer.parseInt(entry.get("sumOfOne")) * Integer.parseInt(entry.get("quantity"));
                } else {
                    fullIncome += Integer.parseInt(entry.get("sumOfOne")) * Integer.parseInt(entry.get("quantity"));
                }
            }

            entryData.put("fullIncome", fullIncome);
            entryData.put("fullConsumption", fullConsumption);

            convertMonthReports.put(month, entryData);
        }

        return convertMonthReports;
    }
}