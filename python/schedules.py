import random


def check_if_shifts_are_valid(check_shift):
    valid_shifts = ["morning", "afternoon", "evening"]
    for shift in valid_shifts:
        if check_shift in shift:
            return shift
    return "NA"

# Step 1: Input and Storage
def collect_employee_preferences():
    employees = {}
    days_of_week = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    shifts = ["morning", "afternoon", "evening"]
    lazy_load = False
    while  True:
        choice = input(f"1. Add Employee\n2. Load Random Employees\n3. Exit\n")
        if choice == "1":
            name = input("Enter employee name:")
            preferences = {}
            for day in days_of_week:
                pref = input((f"Enter {name}'s preferred shift for {day}:"))    
                pref = check_if_shifts_are_valid(pref)
                if pref != "NA":    
                    preferences[day] = pref
            employees[name] = preferences

        elif choice == "2":
            if not lazy_load:
                print("Loading random employees...")
                lazy_load = True
                employees_tmp = {
                    "Alice": {"Monday": "morning", "Tuesday": "afternoon", "Wednesday": "evening", "Thursday": "morning", "Friday": "afternoon"},
                    "Bob": {"Monday": "afternoon", "Tuesday": "morning", "Wednesday": "morning", "Thursday": "afternoon", "Friday": "evening"},
                    "Charlie": {"Monday": "evening", "Tuesday": "evening", "Wednesday": "morning", "Thursday": "afternoon", "Friday": "morning"},
                    "David": {"Monday": "morning", "Tuesday": "morning", "Wednesday": "morning", "Thursday": "morning", "Friday": "morning"},
                    "Eve": {"Monday": "evening", "Tuesday": "evening", "Wednesday": "evening", "Thursday": "evening", "Friday": "evening"},
                    "Frank": {"Monday": "morning", "Tuesday": "morning", "Wednesday": "morning", "Thursday": "morning", "Friday": "morning"},
                    "Grace": {"Monday": "afternoon", "Tuesday": "afternoon", "Wednesday": "afternoon", "Thursday": "afternoon", "Friday": "afternoon"},
                    "Heidi": {"Monday": "evening", "Tuesday": "evening", "Wednesday": "evening", "Thursday": "evening", "Friday": "evening"},
                    "Ivan": {"Monday": "morning", "Tuesday": "morning", "Wednesday": "morning", "Thursday": "morning", "Friday": "morning"},
                    "Jenny": {"Monday": "afternoon", "Tuesday": "afternoon", "Wednesday": "afternoon", "Thursday": "afternoon", "Friday": "afternoon"}
                }
                for name, preferences in employees_tmp.items():
                    employees[name] = preferences
            else:
                print("Test employees already loaded. Please add employees manually.")
        else:
            break   
    return employees, days_of_week, shifts

# Step 2: Scheduling Logic
def assign_shifts(employees, days_of_week, shifts):
    # Initialize the schedule and employee days worked
    schedule = {day: {shift: [] for shift in shifts} for day in days_of_week}
    employee_days_worked = {employee: 0 for employee in employees}

    # Assign shifts
    for day in days_of_week:
        for shift in shifts:
            # Assign employees based on their preferences
            for employee, preferences in employees.items():
                if (
                    employee_days_worked[employee] < 5  # Employee hasn't worked 5 days yet
                    and day in preferences  # Employee has a preference for this day
                    and shift in preferences[day]  # Employee prefers this shift
                    and len(schedule[day][shift]) < 2  # Shift has fewer than 2 employees
                    and employee not in schedule[day][shift]  # Employee not already assigned to this shift
                ):
                    schedule[day][shift].append(employee)
                    employee_days_worked[employee] += 1

            # Ensure at least 2 employees per shift
            while len(schedule[day][shift]) < 2:
                # Find employees who:
                # 1. Have not worked 5 days yet
                # 2. Are not already assigned to any shift on the same day
                available_employees = [
                    emp for emp, days in employee_days_worked.items()
                    if days < 5  # Employee hasn't worked 5 days yet
                    and emp not in [emp for s in schedule[day].values() for emp in s]  # Not assigned to any shift on the same day
                ]

                if not available_employees:
                    break  # No more employees available to assign

                # Randomly assign an available employee to the shift
                chosen_employee = random.choice(available_employees)
                schedule[day][shift].append(chosen_employee)
                employee_days_worked[chosen_employee] += 1

    return schedule

# Step 3: Shift Conflicts (Handled in the scheduling logic)

# Step 4: Output
def display_schedule(schedule, days_of_week, shifts):
    print("\n....................Schedule....................")
    for day in days_of_week:
        print(f"================================================")
        for shift in shifts:
            if f"{day.upper()} {shift.upper()}".__len__() < 16:
                print(f"{day.upper()} {shift.upper()}\t\t::\t {' & '.join(schedule[day][shift]).upper()}")
            else:
                print(f"{day.upper()} {shift.upper()}\t::\t {' & '.join(schedule[day][shift]).upper()}")

# Main function
def main():
    employees, days_of_week, shifts = collect_employee_preferences()
    print("Setting schedule...")
    schedule = assign_shifts(employees, days_of_week, shifts)
    display_schedule(schedule, days_of_week, shifts)

if __name__ == "__main__":
    main()