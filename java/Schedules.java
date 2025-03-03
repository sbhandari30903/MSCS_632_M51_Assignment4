import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Schedules class manages employee shift scheduling for a business
 * Handles employee management, shift assignments, and schedule display
 */

public class Schedules {
    // Collection to store all employees
    static ArrayList<Employee> employees = new ArrayList<>();
    // Three-level map to store the schedule:
    // First level: Day -> Map of shifts
    // Second level: Shift -> List of employee names
    // Third level: List containing employee names assigned to that day and shift
    static Map<String, Map<String, List<String>>> schedule = new HashMap<>();
    // Constants for days of the week and shift types
    static String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
    static String[] shifts = {"MORNING", "AFTERNOON", "EVENING"};
       /**
     * Static initializer block
     * Creates empty schedule structure with all days and shifts
     */
    static {
        // Initialize the schedule map with empty shift assignments for each day
        for (String day : days) {
            schedule.put(day, new HashMap<>());
            for (String shift : shifts) {
                schedule.get(day).put(shift, new ArrayList<>());
            }
        }
    }

    /**
     * Adds an employee to the employees list if not already present
     * @param employee The employee to be added
     */
    public static void addEmployToList(Employee employee) {
        // Check for duplicate employees based on name
        boolean isDuplicate = false;
        for (Employee emp : employees) {
            if (emp.getName().equals(employee.getName())) {
                isDuplicate = true;
                break;
            }
        }
        // Add only if not a duplicate
        if (!isDuplicate) {
            employees.add(employee);
        }
    }

    /**
     * Collects employee details and shift preferences through user input
     * @param scanner Scanner object for input
     * @param employee Employee object to store details
     */
    public static void getDetails(Scanner scanner) {
        Employee employee = new Employee();
        // Get employee name
        System.out.println("Enter your name:");
        String name = scanner.nextLine().toUpperCase();
        while (name.isEmpty()) {
            System.out.println("Name cannot be empty.\nEnter your name:");
            name = scanner.nextLine().toUpperCase();
        }
        employee.setName(name);
        // Get preferred shift for each day of the week
        for (String day : days) {
            System.out.println("Preferred shift for " + day + ":");
            String in = scanner.nextLine().toUpperCase();
            if (in.isEmpty()) {
                in = "notavailable"; // Default value for empty input
            }
            employee.setPreferredShift(day, in);
        }

        employee.setDaysWorked(0);
        addEmployToList(employee);
    }

    /**
     * Displays the current schedule in a formatted table
     * Shows assignments for each day and shift
     */
    public static void display() {
        
        if (employees.size() > 0){
            System.out.println("....................Schedule....................");
            for (String day : days) {
                System.out.println("================================================");
                for (String shift : shifts) {
                    String tmp = "";
                    for (String employeeName : schedule.get(day).get(shift)) {
                        if (schedule.get(day).get(shift).size() > 1 && schedule.get(day).get(shift).indexOf(employeeName) != schedule.get(day).get(shift).size() - 1)
                            tmp += employeeName + " & ";
                        else
                            tmp += employeeName;
                    }
                    if((day + " " + shift).length() < 16)
                        System.out.println(day + " " + shift + "\t\t::\t" + tmp);
                    else
                        System.out.println(day + " " + shift + "\t::\t" + tmp);   
                }
            }
            }
        else{
            System.out.println("No employees to display.");
        }
    }

    /**
     * Checks if the input string is a valid day or shift
     * @param input The input string to be checked
     * @return The valid day or shift string, or "na" if invalid
     */
    public static String shiftValid(String input) {
        for (String shift : shifts) {
            if (shift.equalsIgnoreCase(input) || shift.contains(input)) {
                return shift;
            }
        }
        return "na";
    }

    /**
     * Attempts to add employee to next available shift if preferred shift is full
     * @param employee Employee to be assigned
     * @param day Current day being processed
     * @param shiftString Current shift being processed
     */
    public static void addToNextAvailableShift(Employee employee, String day, String shiftString) {
        boolean isShiftAdded = false;
        int dayIndex = Arrays.asList(days).indexOf(day);
        for (int i = dayIndex; i < days.length; i++) {
            int nextShiftIndex = Arrays.asList(shifts).indexOf(shiftString) + 1;
            if (nextShiftIndex < shifts.length) {
                for (int j = nextShiftIndex; j < shifts.length; j++) {
                    if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !checkIfEmpWorkedThatDay(employee, days[i])) {
                        schedule.get(days[i]).get(shifts[j]).add(employee.getName());
                        employee.setDaysWorked(employee.getDaysWorked() + 1);
                        isShiftAdded = true;
                        break;
                    }
                }
                if (!isShiftAdded) {
                    for (int j = 0; j < nextShiftIndex; j++) {
                        if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !checkIfEmpWorkedThatDay(employee, days[i])) {
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
                        if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !checkIfEmpWorkedThatDay(employee, days[i])) {
                            schedule.get(days[i]).get(shifts[j]).add(employee.getName());
                            employee.setDaysWorked(employee.getDaysWorked() + 1);
                            isShiftAdded = true;
                            break;
                        }
                    }
                    if(!isShiftAdded){
                        for (int j = 0; j < nextShiftIndex; j++) {
                            if (schedule.get(days[i]).get(shifts[j]).size() < 2 && employee.getDaysWorked() < 5 && !checkIfEmpWorkedThatDay(employee, days[i])) {
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

    /**
     * Creates the work schedule based on employee preferences
     * Follows business rules:
     * 1. Maximum 5 days per employee
     * 2. Maximum 2 employees per shift
     * 3. Tries to accommodate preferred shifts
     */
    public static void setSchedule() {
        // Assign employees to shifts based on preferences
        for (Employee employee : employees) {
            for (String day : days) {
                int daysWorked = employee.getDaysWorked();
                String employeePreferredShift = employee.getPreferredShift(day);
                String shiftString = shiftValid(employeePreferredShift);
                if (!shiftString.equals("na")){
                    int totalEmpinShift = schedule.get(day).get(shiftString).size();
                    if (daysWorked < 5){
                        if (totalEmpinShift < 2 && !checkIfEmpWorkedThatDay(employee, day)) {
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
        // Assign remaining shifts to employees
        for (String day: schedule.keySet()){
            for (String shift: schedule.get(day).keySet()){
                if (schedule.get(day).get(shift).size() < 2){
                    for (Employee employee: employees){
                        if (employee.getDaysWorked() < 5 && !checkIfEmpWorkedThatDay(employee, day)){
                            schedule.get(day).get(shift).add(employee.getName());
                            employee.setDaysWorked(employee.getDaysWorked() + 1);
                        }
                    }
                }
            }
        }

    }

    /*
     * Checks if employee worked on a particular day
     * @param employee Employee to be checked
     */
    public static boolean checkIfEmpWorkedThatDay(Employee employee, String day){
        for (String shift: shifts){
            if (schedule.get(day).get(shift).contains(employee.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Main program entry point
     * Provides menu interface for:
     * 1. Adding employees manually
     * 2. Loading test data
     * 3. Generating and displaying schedule
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isTestLoaded = false;
        while (true) {
            System.out.println("1. Add Employee");
            System.out.println("2. Load Random Employees");
            System.out.println("3. Exit");
            String choice = scanner.nextLine();
            //scanner.nextLine();
            if ("1. Add Employee".toUpperCase().contains(choice.toUpperCase())) {
                getDetails(scanner);
                System.out.println("Employee added successfully.\n");
            } 
            else if ("2. Load Random Employees".toUpperCase().contains(choice.toUpperCase())) {
                if (!isTestLoaded){
                    test.loadRandomEmps();
                    System.out.println("Test employees loaded successfully.\n");
                    isTestLoaded = true;
                }
                else{
                    System.out.println("Test employees already loaded. Please add employees manually.\n");
                }    
            }
            else
                break;
        }
        System.out.println("Setting schedule...\n");
        setSchedule();
        display();
        scanner.close();
    }

}

