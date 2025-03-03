# MSCS_632_M51_Assignment4

# Employee Shift Scheduling System

This repository contains two implementations of an employee shift scheduling system - one in Java and one in Python. The system helps manage employee work schedules while following specific business rules and constraints.

## Features

- Employee management and shift preference collection
- Automated schedule generation following business rules
- Schedule display in a formatted table
- Support for manual employee entry and test data loading

## Business Rules

1. Maximum 5 working days per employee
2. Maximum 2 employees per shift
3. Three shifts per day: Morning, Afternoon, Evening
4. Attempts to accommodate employee shift preferences
5. No employee works multiple shifts in one day

## Implementations

### Java Implementation (`java/Schedules.java`)

```java
// Key classes:
- Schedules.java: Main scheduling logic
- Employee.java: Employee data model
```

**Running the Java version:**
```bash
cd java
javac Schedules.java
java Schedules
```

### Python Implementation (`python/schedules.py`)

```python
# Single file implementation with functions:
- collect_employee_preferences()
- assign_shifts()
- display_schedule()
```

**Running the Python version:**
```bash
cd python
python schedules.py
```

## Usage

Both implementations provide a menu-driven interface with options to:

1. Add Employee: Manually enter employee details and preferences
2. Load Random Employees: Load pre-defined test data
3. Exit: Generate and display the schedule

### Example Output

```
....................Schedule....................
================================================
MONDAY MORNING         ::      ALICE & BOB
MONDAY AFTERNOON      ::      CHARLIE & DAVID
MONDAY EVENING        ::      EVE & FRANK
================================================
// ... continues for each day
```

## Key Differences

| Feature | Java | Python |
|---------|------|--------|
| Data Structure | Three-level Map | Nested Dictionaries |
| Class Structure | Multiple Classes | Single File |
| Memory Usage | Static Collections | Function-based |
| Test Data | Separate Class | Inline Dictionary |

## Requirements

### Java Version
- Java 8 or higher
- Java Development Kit (JDK)

### Python Version
- Python 3.6 or higher
- No additional dependencies
