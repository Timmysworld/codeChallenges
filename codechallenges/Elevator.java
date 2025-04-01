package com.codechallenges;

import java.util.Random;

public class Elevator {
    private int currentFloor;
    private int totalFloors;
    private int basementFloors;
    private int currentOccupancy;
    private int maxOccupancy;
    private int elevatorSpeed;
    private int waitingTime;
    private Random random;

    public Elevator(int totalFloors, int maxOccupancy, int elevatorSpeed){
        this.currentFloor = 1; // Start at groud Floor
        this.totalFloors = totalFloors;
        this.basementFloors = -2; // Assuming 2 basement floors
        this.currentOccupancy = 0;
        this.maxOccupancy = maxOccupancy;
        this.elevatorSpeed = elevatorSpeed;
        this.waitingTime = 0; // Initial waiting time
        this.random = new Random();
    }
    
    public void requestElevator(int floor){ 
        if(floor < basementFloors || floor > totalFloors){
            System.out.println("Invalid Floor request, we only have "+ totalFloors);
            return;
        }

        if(currentFloor == floor){
            System.out.println("Elevator is already on the requested floor, please enter");
            return;
        }
        waitingTime = Math.abs(currentFloor - floor) * elevatorSpeed; // Calculate waiting time based on distance
        System.out.println("Elevator is moving to floor " + floor + " wating time: " + waitingTime + " seconds");
        currentFloor = floor;
    }

    public void enterElevator(int people){
        currentOccupancy += people;
        System.out.println("People entered the elevator, current occupancy: " + currentOccupancy);

        if(currentOccupancy > maxOccupancy){
            System.out.println("Warning: Elevator is over capacity!");
            elevatorSpeed -= 2; // Decrease speed due to overcapacity
            System.out.println("Elevator speed dereased to: " + elevatorSpeed);

              //Random Malfunction
            if(random.nextInt(100)< 20 ){
                System.out.println("Disaster! Elevator is malfunctioning");
                System.out.println("Elevator is stuck on floor " + currentFloor);
                System.out.println("Please call for help");
                return;
            }

        }
    }

    public void exitElevator(int people){
        currentOccupancy -= people;
        System.out.println("People exited the elevator, current occupancy: " + currentOccupancy);

        if(currentOccupancy <= maxOccupancy){
            elevatorSpeed = 5; // Increase speed due to undercapacity
            System.out.println("Elevator speed increased to: " + elevatorSpeed);
        }
    }

    public void goToFloor(int floor){
        if (floor == basementFloors) {
            System.out.println("You are going to the parking level");
        } 
        
        if(floor < totalFloors && floor >= 0){
            System.out.println("Elevator is going to floor " + floor);
            currentFloor = floor;

               //Random Malfunction
               if(random.nextInt(100)< 20 ){
                System.out.println("Disaster! Elevator is malfunctioning");
                System.out.println("Elevator is stuck on floor " + currentFloor);
                System.out.println("Please call for help");
                return;
            }
        } else {
            System.out.println("Invalid floor request");
        }
      

    }
    public void simulateRandomRequests() {
        Thread randomRequestThread = new Thread(() -> {
            while (true) {
                try {
                    // Generate a random floor request
                    int randomFloor = random.nextInt(totalFloors - basementFloors) + basementFloors;
                    System.out.println("\nRandom request for floor: " + randomFloor);
                    requestElevator(randomFloor);

                    // Wait for a random interval (1 to 5 seconds)
                    Thread.sleep((random.nextInt(5) + 1) * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Random request simulation interrupted.");
                    break;
                }
            }
        });

        randomRequestThread.start();
    }

    public static void main(String[] args) {
        Elevator elevator = new Elevator(10, 5, 5); // 10 floors, max 5 people, speed 5

        // Start simulating random elevator requests
        elevator.simulateRandomRequests();

        // Simulate manual interactions
        elevator.enterElevator(3); // 3 people enter
        elevator.goToFloor(5);

        elevator.enterElevator(4); // 4 more people enter (over capacity)
        elevator.goToFloor(7);

        elevator.exitElevator(5); // 5 people exit
        elevator.goToFloor(1);
    }
}
//TODO: Ideas
// 1. Add a feature to track the total number of requests made to the elevator.
// 2. Implement a priority system for requests based on the time of day (e.g., more requests during peak hours).
// 3. Add a feature to log the history of elevator movements and requests.
// 4. Implement a maintenance mode where the elevator can be taken out of service for repairs.
// 5. Add a feature to simulate different elevator speeds based on the number of people inside.
