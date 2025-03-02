import java.util.Map;
import java.util.HashMap;

public class Employee {

    private String name;
    private int daysWorked;
    private Map<String, String> preferredShift = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getPreferredShift(String day){
        return preferredShift.getOrDefault(day, "Not Found");
    }

    public void setPreferredShift(String day, String shift){
        preferredShift.put(day.toUpperCase(), shift.toUpperCase());
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

}
