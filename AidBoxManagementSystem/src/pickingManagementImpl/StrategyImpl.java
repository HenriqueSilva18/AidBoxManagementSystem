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

import com.estg.core.AidBox;
import com.estg.core.Institution;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteValidator;
import com.estg.pickingManagement.Strategy;
import com.estg.pickingManagement.exceptions.RouteException;
import coreImpl.InstitutionImpl;

/**
 * This class represents an implementation of the Strategy interface.
 * It generates delivery routes for a given institution using the provided route validator.
 * The class ensures that the routes are valid according to the business rules defined in the RouteValidator.
 * It iterates over the institution's aid boxes and vehicles to construct routes,
 * validating each step and adjusting routes as necessary.
 */
public class StrategyImpl implements Strategy {

    /**
     * Generates routes for the given institution using the provided route validator.
     * This method iterates through the institution's vehicles and aid boxes to create valid delivery routes.
     * It checks each aid box to see if it can be added to the current route based on the vehicle's supply type
     * and the route validator's rules. If an aid box is added but the route becomes invalid, the aid box is removed.
     * Finally, the method returns an array of all valid routes generated.
     *
     * @param institution the institution for which routes are to be generated
     * @param routeValidator the validator to validate the generated routes
     * @return an array of valid routes
     * @throws IllegalArgumentException if the institution or routeValidator is null
     */
    @Override
    public Route[] generate(Institution institution, RouteValidator routeValidator) {
        if (institution == null || routeValidator == null) {
            throw new IllegalArgumentException("Institution and RouteValidator cannot be null.");
        }

        AidBox[] aidBoxes = institution.getAidBoxes();
        InstitutionImpl inst1 = (InstitutionImpl) institution;
        Route[] routes = new Route[inst1.getNumberOfVehicles()];

        int indexOfRoute = 0;
        int indexOfVehicle = 0;

        while (indexOfVehicle < inst1.getNumberOfVehicles() && indexOfRoute < routes.length) {
            RouteImpl routeTemp = new RouteImpl(inst1.getVehicles()[indexOfVehicle]);
            boolean vehicleLoadedSomething = false;

            // Iterate over the AidBoxes
            for (int j = 0; j < aidBoxes.length; j++) {
                if (aidBoxes[j].getContainer(routeTemp.getVehicle().getSupplyType()) != null) {
                    // Try to add AidBox
                    try {
                        routeTemp.addAidBox(aidBoxes[j]);
                        vehicleLoadedSomething = true;
                    } catch (RouteException e) { // Skip if not added
                        System.out.println("\nProblem occurred: " + e + "\nAidBox Code: " + aidBoxes[j].getCode());
                        continue;
                    }

                    // If the route isn't valid with the current AidBox, skip
                    if (!routeValidator.validate(routeTemp, aidBoxes[j])) {
                        routeTemp.removeLastAidBox(); // Remove the last AidBox added
                        continue;
                    }
                }
            }

            // Check if the vehicle has loaded something or not
            if (vehicleLoadedSomething && ((VehicleImpl) routeTemp.getVehicle()).getCapacity() > 0) {
                routes[indexOfRoute] = routeTemp;
                indexOfRoute++;
            }
            indexOfVehicle++;
        }

        // Resize the routes array to the actual number of routes
        Route[] resizedRoutes = new Route[indexOfRoute];
        for (int i = 0; i < indexOfRoute; i++) {
            resizedRoutes[i] = routes[i];
        }
        return resizedRoutes;
    }
}
