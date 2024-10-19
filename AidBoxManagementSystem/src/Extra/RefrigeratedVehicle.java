/*
 * Nome: Agostinho Manuel da Silva Ferreira
 * Número: 8210590
 * Turma: LEI11T1
 *
 * Nome: Henrique Miguel Gomes da Silva
 * Número: 8230097
 * Turma: LEI11T1
 */

package Extra;

import com.estg.core.ItemType;
import pickingManagementImpl.VehicleImpl;

/**
 * This class represents a refrigerated vehicle, which is a specific type of vehicle that
 * carries perishable food and has a maximum travel distance limitation.
 * It extends the VehicleImpl class to include additional properties specific to refrigerated vehicles.
 */
public class RefrigeratedVehicle extends VehicleImpl {

    private double maxMeters;

    /**
     * Constructs a RefrigeratedVehicle object with the specified maximum capacity, enabled status, and maximum meters.
     * The supply type is set to PERISHABLE_FOOD by default.
     *
     * @param maxCapacity the maximum capacity of the vehicle
     * @param enabled the operational status of the vehicle
     * @param maxMeters the maximum distance the vehicle can travel
     */
    public RefrigeratedVehicle(double maxCapacity, boolean enabled, double maxMeters) {
        super(maxCapacity, true);
        super.setSupplyType(ItemType.PERISHABLE_FOOD);
        this.maxMeters = maxMeters;
    }

    /**
     * Retrieves the maximum distance the vehicle can travel with perishable food.
     *
     * @return the maximum distance the vehicle can travel with perishable food
     */
    public double getMaxMeters() { return maxMeters; }
}
