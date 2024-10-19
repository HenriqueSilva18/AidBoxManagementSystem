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

import com.estg.core.ItemType;
import com.estg.pickingManagement.Vehicle;

/**
 * This class represents an implementation of the Vehicle interface.
 * It contains information about a vehicle's capacity, supply type, and operational status.
 * The class provides methods to manipulate and retrieve this information.
 */
public class VehicleImpl implements Vehicle{
    
    private double maxCapacity;
    private double capacity;
    private ItemType supplyType;
    private boolean enabled;

    /**
     * Constructs a VehicleImpl object with the specified maximum capacity, supply type, and enabled status.
     *
     * @param maxCapacity the maximum capacity of the vehicle
     * @param supplyType the type of supply the vehicle carries
     * @param enabled the operational status of the vehicle
     */
    public VehicleImpl(double maxCapacity, ItemType supplyType, boolean enabled) {
        this.maxCapacity = maxCapacity;
        this.supplyType = supplyType;
        this.enabled = enabled;
        capacity = 0;
    }

    /**
     * Constructs a VehicleImpl object with the specified maximum capacity and enabled status.
     * The supply type is not specified in this constructor.
     *
     * @param maxCapacity the maximum capacity of the vehicle
     * @param enabled the operational status of the vehicle
     */
    public VehicleImpl(double maxCapacity, boolean enabled) {
        this.maxCapacity = maxCapacity;
        this.enabled = true;
        capacity = 0;
    }

    /**
     * Retrieves the supply type of the vehicle.
     *
     * @return the supply type of the vehicle
     */
    @Override
    public ItemType getSupplyType() {
        return supplyType;
    }

    /**
     * Sets the supply type of the vehicle.
     *
     * @param supplyType the new supply type of the vehicle
     */
    public void setSupplyType(ItemType supplyType) {
        this.supplyType = supplyType;
    }

    /**
     * Retrieves the maximum capacity of the vehicle.
     *
     * @return the maximum capacity of the vehicle
     */
    @Override
    public double getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Retrieves the current capacity of the vehicle.
     *
     * @return the current capacity of the vehicle
     */
    public double getCapacity() {
        return capacity;
    }

    /**
     * Adds to the current capacity of the vehicle.
     *
     * @param capacity the amount to add to the current capacity
     */
    public void addCapacity(double capacity) {
        this.capacity += capacity;
    }

    /**
     * Checks if the vehicle is enabled.
     *
     * @return {@code true} if the vehicle is enabled, {@code false} otherwise
     */
    public boolean isEnabled() { return enabled; }

    /**
     * Sets the enabled status of the vehicle.
     *
     * @param enabled the new enabled status of the vehicle
     */
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    /**
     * Returns a string representation of the vehicle.
     *
     * @return a string representation of the vehicle
     */
    @Override
    public String toString() {
        return "Vehicle Max Capacity: " + maxCapacity + ", Actual Capacity: " + capacity + ", Supply Type: " + supplyType + ", Enabled: " + enabled;
    }
}
