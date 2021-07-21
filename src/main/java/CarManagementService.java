import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.ULongSeqHelper;
import org.springframework.core.Ordered;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

enum VehicleType {
    Sedan, Hatchback, SUV;
}

class Branch {

    private String branchName;
    List<Vehicle> branchVehicles = new ArrayList<>();
    Map<Enum, Integer> vehicleTypePrices = new HashMap<>();

    public Branch(String branchName) {
        this.branchName  = branchName;
        vehicleTypePrices.put(VehicleType.Sedan, 0);
        vehicleTypePrices.put(VehicleType.Hatchback, 0);
        vehicleTypePrices.put(VehicleType.SUV, 0);
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<Vehicle> getBranchVehicles() {
        return branchVehicles;
    }

    public void setBranchVehicles(List<Vehicle> branchVehicles) {
        this.branchVehicles = branchVehicles;
    }

    public Map<Enum, Integer> getVehicleTypePrices() {
        return vehicleTypePrices;
    }

    public void setVehicleTypePrices(Map<Enum, Integer> vehicleTypePrices) {
        this.vehicleTypePrices = vehicleTypePrices;
    }
}

class Strategy {

    private static SubStrategy instance;
    private Strategy(){};

    public static SubStrategy getInstance() {
        if(instance == null){
            instance = new PriceStrategy();
        }
        return instance;
    }
}

interface SubStrategy {
    public int selectVehicle(Vehicle one, Vehicle two);
}

class PriceStrategy implements SubStrategy {

    public int selectVehicle(Vehicle one, Vehicle two) {
        return one.getPrice() - two.getPrice();
    }
}

class Vehicle implements Comparable<Vehicle> {

    private String vehicleId;
    private Enum vehicleType;
    private Integer price;
    private String branchName;
    private Date startTime;
    private Date endTime;
    private boolean allocated;

    // for simplicity putting the date in this format
    public Vehicle(String vehicleId, Enum vehicleType, Integer price, String branchName, Date start, Date end) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.price = price;
        this.branchName = branchName;
        this.startTime = start;
        this.endTime = end;
    }

    public int compareTo(Vehicle o) {
        return Strategy.getInstance().selectVehicle(this, o);
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Enum getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Enum vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }
}

class Solution{

    public static void main(String[] args) throws ParseException {

        CarManagementService service = new CarManagementService();

        service.addBranch("Vasanth Vihar");
        service.addBranch("Cyber City");
        service.allocatePrice("Vasanth Vihar", VehicleType.Sedan, 100);
        service.allocatePrice("Vasanth Vihar", VehicleType.Hatchback, 80);
        service.allocatePrice("Cyber City", VehicleType.Sedan, 200);
        service.allocatePrice("Cyber City", VehicleType.Hatchback, 50);
        service.addVehicle("DL 01 MR 9310", VehicleType.Sedan, "Vasanth Vihar");
        service.addVehicle("DL 01 MR 9311", VehicleType.Sedan, "Cyber City");
        service.addVehicle("DL 01 MR 9312", VehicleType.Hatchback, "Cyber City");
        service.bookVehicle(VehicleType.Sedan, "29-02-2020 10:00 AM", "29-02-2020 01:00 PM");
        service.bookVehicle(VehicleType.Sedan, "29-02-2020 02:00 PM", "29-02-2020 03:00 PM");
        service.bookVehicle(VehicleType.Sedan, "29-02-2020 02:00 PM", "29-02-2020 03:00 PM");
        service.bookVehicle(VehicleType.Sedan, "29-02-2020 02:00 PM", "29-02-2020 03:00 PM");
    }
}


public class CarManagementService {

    Map<Enum, PriorityQueue<Vehicle>> vehicleTypeMap;
    Map<String, Branch> branchMap;
    SimpleDateFormat formatter;

    public CarManagementService() {
        vehicleTypeMap = new HashMap<>();
        branchMap = new HashMap<>();
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
    }

    public void addBranch(String branchName) {

        if(branchMap.containsKey(branchName)) {
            System.out.println("Already a branch.");
        }else {
            branchMap.put(branchName, new Branch(branchName));
        }
    }

    public void allocatePrice(String branchName, Enum vehicleType, Integer price) {

        if(!branchMap.containsKey(branchName)) {
            System.out.println("Not a branch.");
        }else {

            branchMap.get(branchName).getVehicleTypePrices().put(vehicleType, price);
        }

    }

    public void addVehicle(String vehicleId, Enum vehicleType, String branchName) throws ParseException {

        Date startTime = formatter.parse("01-01-1901 00:00 AM");
        Date endTime = formatter.parse("01-01-1901 00:00 AM");

        if(!branchMap.containsKey(branchName)) {
            System.out.println("Not a branch");
        }else {

            int price = branchMap.get(branchName).getVehicleTypePrices().get(vehicleType);
            Vehicle branchVehicle = new Vehicle(vehicleId, vehicleType, price, branchName, startTime, endTime);

            if(!vehicleTypeMap.containsKey(vehicleType)){
                PriorityQueue<Vehicle> queue = new PriorityQueue<>();
                vehicleTypeMap.put(vehicleType, queue);
            }

            vehicleTypeMap.get(vehicleType).add(branchVehicle);
            branchMap.get(branchName).getBranchVehicles().add(branchVehicle);
        }
    }

    public void bookVehicle(Enum vehicleType, String start, String end) throws ParseException {

        Date startTime = formatter.parse(start);
        Date endTime = formatter.parse(end);

        if(!vehicleTypeMap.containsKey(vehicleType)) {
            System.out.println("No vehicle available");
        }else {

            List<Vehicle> inUseVehicles = new ArrayList<>();
            PriorityQueue<Vehicle> vehicleQueue = vehicleTypeMap.get(vehicleType);
            Boolean vehicleAllocated = false;

            while(!vehicleQueue.isEmpty()) {
                Vehicle vehicle = vehicleQueue.poll();

                if(vehicle.getEndTime().before(startTime)) {
                    setVehicle(vehicle, startTime, endTime);
                    inUseVehicles.add(vehicle);
                    vehicleAllocated = true;
                    System.out.println("vehicle allocated " + vehicle.getVehicleId() + " " + vehicle.getVehicleType() + " " + vehicle.getBranchName());
                    break;
                }else {
                    inUseVehicles.add(vehicle);
                }
            }

            if(vehicleAllocated == false) {
                System.out.println("Vehicle did not get allocated " + vehicleType);
            }

            vehicleQueue.addAll(inUseVehicles);
        }
    }

    private void setVehicle(Vehicle vehicle, Date start, Date end) {
        vehicle.setStartTime(start);
        vehicle.setEndTime(end);
        vehicle.setAllocated(true);
    }

    public Map<Enum, PriorityQueue<Vehicle>> getVehicleTypeMap() {
        return vehicleTypeMap;
    }

    public void setVehicleTypeMap(Map<Enum, PriorityQueue<Vehicle>> vehicleTypeMap) {
        this.vehicleTypeMap = vehicleTypeMap;
    }

    public Map<String, Branch> getBranchMap() {
        return branchMap;
    }

    public void setBranchMap(Map<String, Branch> branchMap) {
        this.branchMap = branchMap;
    }
}
