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

        ArrayList<String> errorMonths = new ArrayList<>();

        for (int i = 0; i < yearlyReport.reports.size(); i++) {
            YearlyRecord yearlyRecord = yearlyReport.reports.get(i);
            ArrayList<MonthlyRecord> monthlyRecords = monthlyReport.reports.get(yearlyRecord.month);

            int allMonthIncome = 0;
            int allMonthConsumption = 0;

            for (MonthlyRecord monthlyRecord : monthlyRecords) {
                if (yearlyRecord.isExpense && monthlyRecord.isExpense) {
                    allMonthConsumption += monthlyRecord.quantity * monthlyRecord.sumOfOne;
                }

                if (!yearlyRecord.isExpense && !monthlyRecord.isExpense) {
                    allMonthIncome += monthlyRecord.quantity * monthlyRecord.sumOfOne;
                }
            }

            if (
                (yearlyRecord.isExpense && yearlyRecord.amount != allMonthConsumption) ||
                (!yearlyRecord.isExpense && yearlyRecord.amount != allMonthIncome)
            ) {
                errorMonths.add(monthlyReport.monthNames[yearlyRecord.month - 1]);
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

