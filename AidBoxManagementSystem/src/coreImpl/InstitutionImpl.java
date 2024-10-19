/*
 * Nome: Agostinho Manuel da Silva Ferreira
 * Número: 8210590
 * Turma: LEI11T1
 *
 * Nome: Henrique Miguel Gomes da Silva
 * Número: 8230097
 * Turma: LEI11T1
 */

package coreImpl;

import Import.ImportAPI;
import com.estg.core.*;
import com.estg.core.exceptions.*;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import pickingManagementImpl.VehicleImpl;


import java.time.LocalDateTime;

/**
 * This class represents an implementation of the Institution interface.
 * It contains information about the institution such as name, aid boxes, vehicles, picking maps, distance, and reports.
 * It also contains methods to manipulate and retrieve this information.
 */

public class InstitutionImpl implements Institution {

    private static final int EXPAND_SIZE = 5;
    private String name;
    private int numberOfAidBoxes = 0;
    private AidBox[] aidBoxes;
    private int numberOfVehicles;
    private Vehicle[] vehicles;
    private int numberOfPickingMaps;
    private PickingMap[] pickingMaps;
    private double distance;
    private int numberOfReports;
    private Report[] reports;

    /**
     * Constructs a new InstitutionImpl with the specified values.
     *
     * @param name The name of the institution.
     * @param vehicles The vehicles of the institution.
     * @param pickingMaps The picking maps of the institution.
     * @param distance The distance of the institution.
     */

    public InstitutionImpl(String name, Vehicle[] vehicles, PickingMap[] pickingMaps, double distance) {
        this.name = name;
        aidBoxes = new AidBox[5];
        this.vehicles = vehicles;
        this.pickingMaps = pickingMaps;
        this.distance = distance;
        reports = new Report[5];
    }

    /**
     * Constructs a new InstitutionImpl with the specified values.
     *
     * @param name The name of the institution.
     * @param aidBoxes The aid boxes of the institution.
     * @param vehicles The vehicles of the institution.
     */
    public InstitutionImpl(String name, AidBox[] aidBoxes, Vehicle[] vehicles) {
        this.name = name;
        this.aidBoxes = aidBoxes;
        this.vehicles = vehicles;
        reports = new Report[5];
    }

    /**
     * Returns the name of the institution.
     *
     * @return The name of the institution.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Adds an aid box to the institution.
     *
     * @param aidBox The aid box to be added.
     * @return true if the aid box was added successfully, false otherwise.
     * @throws AidBoxException if the aid box is null or contains duplicate containers of a certain waste type.
     */
    @Override
    public boolean addAidBox(AidBox aidBox) throws AidBoxException {
        // Check if the AidBox is null
        if (aidBox == null) {
            throw new AidBoxException("AidBox is null");
        }
        AidBoxImpl aidBox1 = (AidBoxImpl) aidBox;

        // Check if the AidBox contains duplicate containers of a certain waste type
        if (aidBox1.getNumberOfContainers() != 0) {
            Container[] containers = aidBox1.getContainers();
            for (int i = 0; i < containers.length; i++) {
                for (int j = i + 1; j < containers.length; j++) {
                    if (containers[i].getType().equals(containers[j].getType())) {
                        throw new AidBoxException("The Aid Box contains duplicate containers of a certain waste type");
                    }
                }
            }
        }

        // Check if the AidBox is already in the institution
        for (AidBox existingAidBox : aidBoxes) {
            if (existingAidBox == null) {
                break;
            }
            if (existingAidBox.equals(aidBox1)) {
                return false;
            }
        }

        // Expand the AidBox array if it is full
        if (aidBoxes.length == numberOfAidBoxes) {
            expandAidBoxArray();
        }

        aidBoxes[numberOfAidBoxes++] = aidBox1;
        return true;
    }

    /**
     * Adds a measurement to a container.
     *
     * @param measurement The measurement to be added.
     * @param container The container to which the measurement will be added.
     * @return true if the measurement was added successfully, false otherwise.
     * @throws ContainerException if the container is null.
     * @throws MeasurementException if the measurement value is out of range.
     */
    @Override
    public boolean addMeasurement(Measurement measurement, Container container) throws ContainerException, MeasurementException {
        // Check if the container is null
        if (container == null) {
            throw new ContainerException("Container is null");
        }

        // Check if the measurement value is less than 0 or greater than the container's capacity
        if (measurement.getValue() < 0 || measurement.getValue() > ((ContainerImpl) container).getFirstCapacity()) {
            throw new MeasurementException("Measurement is out of range.");
        }

        // Check if the measurement date is already in the container
        for (int i = 0; i < container.getMeasurements().length; i++) {
            if (container.getMeasurements()[i].getDate().equals(measurement.getDate())) {
                return false;
            }
        }

        container.addMeasurement(measurement);
        return true;
    }

    /**
     * Returns the aid boxes of the institution.
     *
     * @return An array of aid boxes.
     */
    @Override
    public AidBox[] getAidBoxes() {
        return aidBoxes;
    }

    /**
     * Returns the container of an aid box with a certain item type.
     *
     * @param aidBox The aid box to search in.
     * @param itemType The item type of the container.
     * @return The container with the specified item type.
     * @throws ContainerException if the aid box is null or if the container with the given item type doesn't exist.
     */
    @Override
    public Container getContainer(AidBox aidBox, ItemType itemType) throws ContainerException {

        // Check if the AidBox is null
        if (aidBox == null) {
            throw new ContainerException("AidBox is null");
        }

        // Check if the container with the given item type exists, if so, return it
        for (Container container : aidBox.getContainers()) {
            if (container.getType().equals(itemType)) {
                return container;
            }
        }
        throw new ContainerException("Container with the given item type doesn't exist");
    }

    /**
     * Returns the vehicles of the institution.
     *
     * @return An array of vehicles.
     */
    @Override
    public Vehicle[] getVehicles() {
        return vehicles;
    }

    /**
     * Expands the vehicle array by a fixed size.
     */
    public void expandVehicleArray() {
        Vehicle[] expandedArray = new Vehicle[vehicles.length + EXPAND_SIZE];

        for (int i = 0; i < vehicles.length; i++) {
            expandedArray[i] = vehicles[i];
        }

        vehicles = expandedArray;
    }

    /**
     * Adds a vehicle to the institution.
     *
     * @param vehicle The vehicle to be added.
     * @return true if the vehicle was added successfully, false otherwise.
     * @throws VehicleException if the vehicle is null.
     */
    @Override
    public boolean addVehicle(Vehicle vehicle) throws VehicleException {
        // Check if the vehicle is null
        if (vehicle == null) {
            throw new VehicleException("Vehicle is null");
        }

        // Check if the vehicle is already in the institution
        for (Vehicle existingVehicle : vehicles) {
            if (existingVehicle == null) {
                continue;
            }
            if (existingVehicle.equals(vehicle)) {
                return false;
            }
        }

        // Expand the vehicle array if it is full
        if (vehicles.length == numberOfVehicles) {
            expandVehicleArray();
        }

        vehicles[numberOfVehicles++] = vehicle;

        return true;
    }

    /**
     * Disables a vehicle.
     *
     * @param vehicle The vehicle to be disabled.
     * @throws VehicleException if the vehicle is null or already disabled.
     */
    @Override
    public void disableVehicle(Vehicle vehicle) throws VehicleException {

        VehicleImpl vehicle1 = (VehicleImpl) vehicle;

        // Check if the vehicle is null
        if (vehicle == null) {
            throw new VehicleException("Vehicle is null");
        }

        // Check if the vehicle is already disabled
        if (!vehicle1.isEnabled()) {
            throw new VehicleException("Vehicle is already disabled");
        }

        vehicle1.setEnabled(false);
    }

    /**
     * Enables a vehicle.
     *
     * @param vehicle The vehicle to be enabled.
     * @throws VehicleException if the vehicle is null or already enabled.
     */
    @Override
    public void enableVehicle(Vehicle vehicle) throws VehicleException {

        VehicleImpl vehicle1 = (VehicleImpl) vehicle;

        // Check if the vehicle is null
        if (vehicle == null) {
            throw new VehicleException("Vehicle is null");
        }

        // Check if the vehicle is already enabled
        if (vehicle1.isEnabled()) {
            throw new VehicleException("Vehicle is already enabled");
        }
        vehicle1.setEnabled(true);
    }

    /**
     * Returns the picking maps of the institution.
     *
     * @return An array of picking maps.
     */
    @Override
    public PickingMap[] getPickingMaps() {
        return pickingMaps;
    }

    /**
     * Returns the picking maps of the institution within a certain time frame.
     *
     * @param from The start date and time.
     * @param to The end date and time.
     * @return An array of picking maps within the specified time frame.
     */
    @Override
    public PickingMap[] getPickingMaps(LocalDateTime from, LocalDateTime to) {
        for (PickingMap pickingMap : pickingMaps) {
            // Check if the picking map date is within the time frame
            if ((from.isEqual(pickingMap.getDate()) || from.isBefore(pickingMap.getDate())) &&
                    (to.isEqual(pickingMap.getDate()) || to.isAfter(pickingMap.getDate()))) {
                return new PickingMap[]{pickingMap};
            } // REFAZER
        }
        return new PickingMap[0];
    }

    /**
     * Returns the most recent picking map of the institution.
     *
     * @return The most recent picking map.
     * @throws PickingMapException if there are no picking maps in the institution.
     */
    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        // Check if there are any picking maps in the institution
        if (pickingMaps.length > 0) {
            return pickingMaps[pickingMaps.length - 1];
        } else {
            throw new PickingMapException("No picking maps in the institution");
        }
    }

    /**
     * Adds a picking map to the institution.
     *
     * @param pickingMap The picking map to be added.
     * @return true if the picking map was added successfully, false otherwise.
     * @throws PickingMapException if the picking map is null.
     */
    @Override
    public boolean addPickingMap(PickingMap pickingMap) throws PickingMapException {
        // Check if the picking map is null
        if (pickingMap == null) {
            throw new PickingMapException("PickingMap is null");
        }

        for (PickingMap existingPickingMap : pickingMaps) {
            // Check if the picking map is already in the institution
            if (existingPickingMap.equals(pickingMap)) {
                return false;
            }
        }
        pickingMaps[numberOfPickingMaps++] = pickingMap;
        return true;
    }

    /**
     * Returns the distance of the institution to another aid box.
     *
     * @param aidBox The aid box to measure the distance to.
     * @return The distance to the specified aid box.
     * @throws AidBoxException if the aid box is null.
     */
    @Override
    public double getDistance(AidBox aidBox) throws AidBoxException {
        // Check if the AidBox is null
        if (aidBox == null) {
            throw new AidBoxException("AidBox is null");
        }

        // Import the distance from the API
        ImportAPI importAPI = new ImportAPI();
        return importAPI.getDuration((AidBoxImpl)aidBox);
    }

    /**
     * Expands the aid box array by a fixed size.
     */
    private void expandAidBoxArray() {
        AidBox[] expandedArray = new AidBox[this.aidBoxes.length + EXPAND_SIZE];

        for (int i = 0; i < aidBoxes.length; i++) {
            expandedArray[i] = aidBoxes[i];
        }

        aidBoxes = expandedArray;
    }

    /**
     * Returns the number of picking maps of the institution.
     *
     * @return The number of picking maps.
     */
    public int getNumberOfPickingMaps() {
        return numberOfPickingMaps;
    }

    /**
     * Returns the number of vehicles of the institution.
     *
     * @return The number of vehicles.
     */
    public int getNumberOfVehicles() {
        int nVehicles = 0;
        for (Vehicle vehicle : vehicles){
            if (vehicle != null) {
                nVehicles++;
            }
        }
        numberOfVehicles = nVehicles;
        return numberOfVehicles;
    }

    /**
     * Returns the number of aid boxes of the institution.
     *
     * @return The number of aid boxes.
     */
    public int getNumberOfAidBoxes() {
        return numberOfAidBoxes;
    }

    /**
     * Returns the number of reports of the institution.
     *
     * @return The number of reports.
     */
    public int getNumberOfReports() {
        return numberOfReports;
    }

    /**
     * Expands the report array by a fixed size.
     */
    private void expandReportArray() {
        Report[] expandedArray = new Report[reports.length + EXPAND_SIZE];
        for (int i = 0; i < reports.length; i++) {
            expandedArray[i] = reports[i];
        }
        reports = expandedArray;
    }

    /**
     * Adds a report to the institution.
     *
     * @param report The report to be added.
     * @throws IllegalArgumentException if the report is null.
     */
    public void addReport(Report report) {
        // Check if the report is null
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null.");
        }

        // Expand the report array if it is full
        if (numberOfReports == reports.length) {
            expandReportArray();
        }

        reports[numberOfReports++] = report;
    }

    public Report getLastReport() {
        if (numberOfReports == 0) {
            return null;
        }
        return reports[numberOfReports - 1];
    }
}
