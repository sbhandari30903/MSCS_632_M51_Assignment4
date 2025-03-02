import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Schedules {

    static ArrayList<Employee> employees = new ArrayList<>();
    static Map<String, Map<String, List<String>>> schedule = new HashMap<>();
    static String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
    static String[] shifts = {"MORNING", "AFTERNOON", "EVENING"};

    static {
        for (String day : days) {
            schedule.put(day, new HashMap<>());
            for (String shift : shifts) {
                schedule.get(day).put(shift, new ArrayList<>());
            }
        }
    }

    public static void getDetails(Scanner scanner, Employee employee) {
        System.out.println("Enter your name:");
        String name = scanner.nextLine().toUpperCase();
        while (name.isEmpty()) {
            System.out.println("Name cannot be empty.\nEnter your name:");
            name = scanner.nextLine().toUpperCase();
        }
        employee.setName(name);

        for (String day : days) {
            System.out.println("Preferred shift for " + day + ":");
            String in = scanner.nextLine().toUpperCase();
            if (in.isEmpty()) {
                in = "notavailable";
            }
            employee.setPreferredShift(day, in);
        }

        employee.setDaysWorked(0);
        employees.add(employee);
    }

    public static void display() {
        // String header = "\t\t";
        // for (String day : days) {
        //     if (day.length() < 8) {
        //         header += day + "\t\t";
        //     } else {
        //         header += day + "\t";
        //     }
        // }
        // System.out.println(header);
        // System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        // List<String> shiftStr = new ArrayList<>();

        // for (String shift : shifts) {
        //     String tmp = "";
        //     if (shift.length() < 8) 
        //         tmp += shift + "\t\t";
        //     else 
        //         tmp += shift + "\t";
        //     for (String day : days) {
        //         String tmpEmp = "";
        //         for (String employeeName : schedule.get(day).get(shift)) {
        //             if (schedule.get(day).get(shift).size() > 1)
        //                 tmpEmp += employeeName + "&";
        //             else
        //                 tmpEmp += employeeName;

        //         }
        //         if (tmpEmp.length() < 8) 
        //             tmp += tmpEmp + "\t\t";
        //         else
        //             tmp += tmpEmp+"\t";
        //     }
        //     shiftStr.add(tmp);
        // }

        // for (String str : shiftStr) {
        //     System.out.println(str+"\n");
        // }
        System.out.println("Schedule in different format.");
        for (String day : days) {
            for (String shift : shifts) {
                String tmp = "";
                for (String employeeName : schedule.get(day).get(shift)) {
                    if (schedule.get(day).get(shift).size() > 1 && schedule.get(day).get(shift).indexOf(employeeName) != schedule.get(day).get(shift).size() - 1)
                        tmp += employeeName + " & ";
                    else
                        tmp += employeeName;
                }
                System.out.println(day + " " + shift + " :: " + tmp);   
            }
        }
    }


    public static String shiftValid(String input) {
        for (String shift : shifts) {
            if (shift.equalsIgnoreCase(input) || shift.contains(input)) {
                return shift;
            }
        }
        return "na";
    }

    public static void addToNextAvailableShift(Employee employee, String day, String shiftString) {
        boolean isShiftAdded = false;
        int dayIndex = Arrays.asList(days).indexOf(day);
        for (int i = dayIndex; i < days.length; i++) {
            int nextShiftIndex = Arrays.asList(shifts).indexOf(shiftString) + 1;
            if (nextShiftIndex < shifts.length) {
                for (int j = nextShiftIndex; j < shifts.length; j++) {
                    if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !schedule.get(days[i]).get(shifts[j]).contains(employee.getName())) {
                        schedule.get(days[i]).get(shifts[j]).add(employee.getName());
                        employee.setDaysWorked(employee.getDaysWorked() + 1);
                        isShiftAdded = true;
                        break;
                    }
                }
                if (!isShiftAdded) {
                    for (int j = 0; j < nextShiftIndex; j++) {
                        if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !schedule.get(days[i]).get(shifts[j]).contains(employee.getName())) {
                            schedule.get(days[i]).get(shifts[j]).add(employee.getName());
                            employee.setDaysWorked(employee.getDaysWorked() + 1);
                            isShiftAdded = true;
                            break;
                        }
                    }
                }
            }
        }
        if (!isShiftAdded) {
            for (int i = 0; i < dayIndex; i++) {
                int nextShiftIndex = Arrays.asList(shifts).indexOf(shiftString) + 1;
                if (nextShiftIndex < shifts.length) {
                    for (int j = nextShiftIndex; j < shifts.length; j++) {
                        if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !schedule.get(days[i]).get(shifts[j]).contains(employee.getName())) {
                            schedule.get(days[i]).get(shifts[j]).add(employee.getName());
                            employee.setDaysWorked(employee.getDaysWorked() + 1);
                            isShiftAdded = true;
                            break;
                        }
                    }
                    if(!isShiftAdded){
                        for (int j = 0; j < nextShiftIndex; j++) {
                            if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !schedule.get(days[i]).get(shifts[j]).contains(employee.getName())) {
                                schedule.get(days[i]).get(shifts[j]).add(employee.getName());
                                employee.setDaysWorked(employee.getDaysWorked() + 1);
                                isShiftAdded = true;
                                break;
                            }
                        }
                    }
                }
        
            }
            
        }
    }

    public static void setSchedule() {
        for (Employee employee : employees) {
            for (String day : days) {
                int daysWorked = employee.getDaysWorked();
                String employeePreferredShift = employee.getPreferredShift(day);
                String shiftString = shiftValid(employeePreferredShift);
                if (!shiftString.equals("na")){
                    int totalEmpinShift = schedule.get(day).get(shiftString).size();
                    if (daysWorked < 5){
                        if (totalEmpinShift < 2) {
                            schedule.get(day).get(shiftString).add(employee.getName());
                            employee.setDaysWorked(employee.getDaysWorked() + 1);
                        }
                        else{
                            addToNextAvailableShift(employee, day, shiftString);
                        }
                    }
                }
                
            }
        }
        for (String day: schedule.keySet()){
            for (String shift: schedule.get(day).keySet()){
                if (schedule.get(day).get(shift).size() < 2){
                    for (Employee employee: employees){
                        if (employee.getDaysWorked() < 5 && !schedule.get(day).get(shift).contains(employee.getName())){
                            schedule.get(day).get(shift).add(employee.getName());
                            employee.setDaysWorked(employee.getDaysWorked() + 1);
                        }
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee employee = new Employee();
        getDetails(scanner, employee);
        test.loadRandomEmps(employees);
        setSchedule();
        display();
    }
}