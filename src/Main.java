import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        while (true) {
            printMenu();

            int menuInput = scanner.nextInt();

            switch (menuInput) {
                case 1:
                    monthlyReport.readAllReport();
                    break;
                case 2:
                    yearlyReport.readReport();
                    break;
                case 3:
                    dataReconciliation(monthlyReport, yearlyReport);
                    break;
                case 4:
                    monthlyReport.printAllMonthReports();
                    break;
                case 5:
                    yearlyReport.printReport();
                    break;
                case 6:
                    System.out.println("------");
                    System.out.println("Пока!");
                    System.out.println("------");
                    scanner.close();
                    return;

                default:
                    System.out.println("------");
                    System.out.println("Такой команды нет!");
                    System.out.println("------");
            }
        }
    }

    static void printMenu() {
        System.out.println("Меню:");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("6 - Выход");
        System.out.println("------");
    }

    static void dataReconciliation(MonthlyReport monthlyReport, YearlyReport yearlyReport) {
        if (monthlyReport.reports.size() == 0) {
            System.out.println("------");
            System.out.println("Отсутствуют месячные отчеты!");
            System.out.println("------");
            return;
        }

        if (yearlyReport.reports.size() == 0) {
            System.out.println("------");
            System.out.println("Отсутствует годовой отчет!");
            System.out.println("------");
            return;
        }

        HashMap<Integer, HashMap<String, Integer>> convertYearReport = yearlyReport.convertYearReport();
        HashMap<Integer, HashMap<String, Integer>> convertMonthReports = monthlyReport.convertMonthReports();
        ArrayList<String> errorMonths = new ArrayList<>();

        for (int i = 1; i <= convertMonthReports.size(); i++) {
            HashMap<String, Integer> monthData = convertMonthReports.get(i);
            HashMap<String, Integer> yearData = convertYearReport.get(i);

            if (
                !Objects.equals(monthData.get("fullIncome"), yearData.get("income")) ||
                !Objects.equals(monthData.get("fullConsumption"), yearData.get("consumption"))
            ) {
                errorMonths.add(monthlyReport.monthNames[i - 1]);
            }
        }

        if (errorMonths.isEmpty()) {
            System.out.println("------");
            System.out.println("Сверка прошла успешно!");
            System.out.println("------");
        } else {
            System.out.println("------");
            System.out.println("Сверка прошла с ошибками! Ошибки в месяцах:");

            for (String month : errorMonths) {
                System.out.println(month);
            }
            System.out.println("------");
        }
    }
}

