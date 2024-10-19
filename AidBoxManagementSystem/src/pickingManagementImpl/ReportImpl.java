/*
 * Nome: Agostinho Manuel da Silva Ferreira
 * Número: 8210590
 * Turma: LEI11T1
 *
 * Nome: Henrique Miguel Gomes da Silva
 * Número: 8230097
 * Turma: LEI11T1
 */

package pickingManagementImpl;

import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Report;
import java.time.LocalDateTime;

/**
 * This class represents an implementation of the Report interface.
 * It contains information about the report such as the number of used vehicles,
 * the number of picked containers, total distance, total duration, the number
 * of non-picked containers, the number of not used vehicles, and the date.
 * It also contains methods to manipulate and retrieve this information.
 */
public class ReportImpl implements Report{

    private int numberOfUsedVehicles;
    private int numberOfPickedContainers;
    private double totalDistance;
    private double totalDuration;
    private int numberOfNonPickedContainers;
    private int numberOfNotUsedVehicles;
    private LocalDateTime date;

    /**
     * Constructs a new ReportImpl with the specified values.
     *
     * @param numberOfUsedVehicles The number of used vehicles.
     * @param numberOfPickedContainers The number of picked containers.
     * @param totalDistance The total distance covered.
     * @param totalDuration The total duration of the operation.
     * @param numberOfNonPickedContainers The number of non-picked containers.
     * @param numberOfNotUsedVehicles The number of not used vehicles.
     * @param date The date of the report.
     */
    public ReportImpl(int numberOfUsedVehicles, int numberOfPickedContainers, double totalDistance, double totalDuration, int numberOfNonPickedContainers, int numberOfNotUsedVehicles, LocalDateTime date) {
        this.numberOfUsedVehicles = numberOfUsedVehicles;
        this.numberOfPickedContainers = numberOfPickedContainers;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.numberOfNonPickedContainers = numberOfNonPickedContainers;
        this.numberOfNotUsedVehicles = numberOfNotUsedVehicles;
        this.date = date;
    }

    /**
     * Constructs a new ReportImpl with default values.
     */
    public ReportImpl(){
        this.numberOfUsedVehicles = 0;
        this.numberOfPickedContainers = 0;
        this.totalDistance = 0;
        this.totalDuration = 0;
        this.numberOfNonPickedContainers = 0;
        this.numberOfNotUsedVehicles = 0;
        this.date = null;
    }

    /**
     * Returns the number of used vehicles.
     *
     * @return The number of used vehicles.
     */
    @Override
    public int getUsedVehicles() {
        return numberOfUsedVehicles;
    }

    /**
     * Returns the number of picked containers.
     *
     * @return The number of picked containers.
     */
    @Override
    public int getPickedContainers() {
        return numberOfPickedContainers;
    }

    /**
     * Returns the total distance covered.
     *
     * @return The total distance covered.
     */
    @Override
    public double getTotalDistance() {
        return totalDistance;
    }

    /**
     * Returns the total duration of the operation.
     *
     * @return The total duration of the operation.
     */
    @Override
    public double getTotalDuration() {
        return totalDuration;
    }

    /**
     * Returns the number of non-picked containers.
     *
     * @return The number of non-picked containers.
     */
    @Override
    public int getNonPickedContainers() {
        return numberOfNonPickedContainers;
    }

    /**
     * Returns the number of not used vehicles.
     *
     * @return The number of not used vehicles.
     */
    @Override
    public int getNotUsedVehicles() {
        return numberOfNotUsedVehicles;
    }

    /**
     * Returns the date of the report.
     *
     * @return The date of the report.
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the number of used vehicles.
     *
     * @param numberOfUsedVehicles The new number of used vehicles.
     */
    public void setNumberOfUsedVehicles(int numberOfUsedVehicles) {
        this.numberOfUsedVehicles = numberOfUsedVehicles;
    }

    /**
     * Sets the number of picked containers.
     *
     * @param numberOfPickedContainers The new number of picked containers.
     */
    public void setNumberOfPickedContainers(int numberOfPickedContainers) {
        this.numberOfPickedContainers = numberOfPickedContainers;
    }

    /**
     * Sets the total distance covered.
     *
     * @param totalDistance The new total distance.
     */
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Sets the total duration of the operation.
     *
     * @param totalDuration The new total duration.
     */
    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    /**
     * Sets the number of non-picked containers.
     *
     * @param numberOfNonPickedContainers The new number of non-picked containers.
     */
    public void setNumberOfNonPickedContainers(int numberOfNonPickedContainers) {
        this.numberOfNonPickedContainers = numberOfNonPickedContainers;
    }

    /**
     * Sets the number of not used vehicles.
     *
     * @param numberOfNotUsedVehicles The new number of not used vehicles.
     */
    public void setNumberOfNotUsedVehicles(int numberOfNotUsedVehicles) {
        this.numberOfNotUsedVehicles = numberOfNotUsedVehicles;
    }

    /**
     * Sets the date of the report.
     *
     * @param date The new date.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Returns a string representation of the report.
     *
     * @return A string representation of the report.
     */
    @Override
    public String toString() {
        return "Date: " + date + "\n" +
                "Nº of used vehicles: " + numberOfUsedVehicles + "\n" +
                "Nº of non used vehicles: " + numberOfNotUsedVehicles + "\n" +
                "Nº of picked containers: " + numberOfPickedContainers + "\n" +
                "Nº of non picked containers: " + numberOfNonPickedContainers + "\n" +
                "Total distance: " + totalDistance + "\n" +
                "Total duration: " + totalDuration;
    }
}
