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
import com.estg.core.exceptions.PickingMapException;
import com.estg.pickingManagement.*;
import coreImpl.AidBoxImpl;
import coreImpl.InstitutionImpl;

import java.time.LocalDateTime;

/**
 * This class implements the RouteGenerator interface, providing functionality
 * to generate routes based on a given strategy, validate the routes, and create
 * a report with details of the generated routes.
 */
public class RouteGeneratorImpl implements RouteGenerator {

    /**
     * Generates routes for an institution using the specified strategy and route validator,
     * and creates a report with details of the generated routes.
     *
     * @param institution The institution for which to generate routes.
     * @param strategy The strategy to use for generating routes.
     * @param routeValidator The validator to use for validating routes.
     * @param report The report to update with route details.
     * @return An array of generated routes.
     * @throws PickingMapException If there is an error generating the routes.
     */
    @Override
    public Route[] generateRoutes(Institution institution, Strategy strategy, RouteValidator routeValidator, Report report) throws PickingMapException {

        ReportImpl reportImpl = (ReportImpl) report;

        int numberOfUsedVehicles = 0;
        int numberOfPickedContainers = 0;
        double totalDistance = 0;
        double totalDuration = 0;
        int numberOfNonPickedContainers = 0;
        int notUsedVehicles = 0;

        Route[] routes = strategy.generate(institution, routeValidator);

        for (int i = 0; i < routes.length; i++) {
            Route route = routes[i];
            Vehicle vehicle = route.getVehicle();
            AidBox[] aidBoxes = route.getRoute();
            numberOfUsedVehicles++;
            numberOfPickedContainers += aidBoxes.length;
            if (aidBoxes[i] != null) {
                numberOfNonPickedContainers += ((AidBoxImpl) aidBoxes[i]).getNumberOfContainers() - 1;
            }
            totalDistance += route.getTotalDistance();
            totalDuration += route.getTotalDuration();
        }

        reportImpl.setDate(LocalDateTime.now());
        reportImpl.setNumberOfPickedContainers(numberOfPickedContainers);
        reportImpl.setNumberOfNonPickedContainers(numberOfNonPickedContainers);
        reportImpl.setTotalDistance(totalDistance);
        reportImpl.setTotalDuration(totalDuration);
        reportImpl.setNumberOfUsedVehicles(numberOfUsedVehicles);
        notUsedVehicles = ((InstitutionImpl) institution).getNumberOfVehicles() - numberOfUsedVehicles;
        reportImpl.setNumberOfNotUsedVehicles(notUsedVehicles);

        try {
            ((InstitutionImpl) institution).addReport(report);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return routes;
    }
}
