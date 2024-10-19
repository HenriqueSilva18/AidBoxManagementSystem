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

import Extra.RefrigeratedVehicle;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteValidator;
import coreImpl.AidBoxImpl;
import coreImpl.ContainerImpl;

/**
 * Implementation of the RouteValidator interface.
 */
public class RouteValidatorImpl implements RouteValidator {

    /**
     * Validates a given route and aid box to determine if the route can be taken.
     *
     * @param route the route to be validated
     * @param aidBox the aid box to be used in the route
     * @return {@code true} if the route is valid, {@code false} otherwise
     * @throws ClassCastException if the route or aid box cannot be cast to their implementation classes
     */
    @Override
    public boolean validate(Route route, AidBox aidBox) {
        AidBoxImpl aidBox1 = (AidBoxImpl) aidBox;
        RouteImpl route1 = (RouteImpl) route;

        if (route1 == null || aidBox1 == null) {
            return false;
        }

        // Check if the vehicle is a refrigerated vehicle and if the route exceeds its maximum distance
        if (route1.getVehicle() instanceof RefrigeratedVehicle && route1.getTotalDistance() > ((RefrigeratedVehicle) route1.getVehicle()).getMaxMeters()) {
            return false;
        }

        boolean check = false; // Flag to indicate if any container matches the vehicle's supply type
        boolean returnCheck = false; // Flag to indicate if a valid container was found

        // Iterate through the containers to find a match with the vehicle's supply type
        for (int i = 0; i < aidBox1.getNumberOfContainers(); i++) {
            if (aidBox1.getContainers()[i].getType() == route1.getVehicle().getSupplyType()) {
                check = true;
            }
            if (check) {
                returnCheck = true;
            }
        }
        if (!returnCheck) {
            return false;
        }

        // Check the measurements of the containers
        for (int i = 0; i < aidBox1.getNumberOfContainers(); i++) {
            for (int j = 0; j < aidBox1.getContainers()[i].getMeasurements().length; j++) {
                if (aidBox1.getContainers()[i].getMeasurements()[j] == null) {
                    continue;
                }
                if (aidBox1.getContainers()[i].getType() == route1.getVehicle().getSupplyType()) {
                    if (aidBox1.getContainers()[i].getMeasurements()[j].getValue() <= 0) {
                        return false;
                    }
                }
            }
        }

        double totalContainerCapacity = 0;
        // Calculate the total capacity of containers matching the vehicle's supply type
        for (Container container : aidBox1.getContainers()) {
            if (container.getType() == route1.getVehicle().getSupplyType()) {
                totalContainerCapacity += container.getCapacity();
            }
        }
        VehicleImpl vehicleTemp = (VehicleImpl) route1.getVehicle();

        // Check if the vehicle is enabled, return false if is disabled
        if (!vehicleTemp.isEnabled()) return false;

        if (vehicleTemp.getMaxCapacity() < vehicleTemp.getCapacity() + totalContainerCapacity) {
            return false;
        }

        // Set the capacity of the relevant containers to 0
        for (int i = 0; i < aidBox1.getNumberOfContainers(); i++) {
            if (aidBox1.getContainers()[i].getType() == route1.getVehicle().getSupplyType()) {
                ((ContainerImpl) aidBox1.getContainers()[i]).setCapacity(0);
            }
        }

        // Add the total container capacity to the vehicle's capacity
        vehicleTemp.addCapacity(totalContainerCapacity);
        return true;
    }
}

