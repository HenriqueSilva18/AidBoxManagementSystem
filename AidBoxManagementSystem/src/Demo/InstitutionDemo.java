/*
 * Nome: Agostinho Manuel da Silva Ferreira
 * Número: 8210590
 * Turma: LEI11T1
 *
 * Nome: Henrique Miguel Gomes da Silva
 * Número: 8230097
 * Turma: LEI11T1
 */

package Demo;

import Extra.RefrigeratedVehicle;
import Import.ImportAPI;
import com.estg.core.ItemType;
import com.estg.core.exceptions.PickingMapException;

import com.estg.pickingManagement.Route;
import pickingManagementImpl.*;
import coreImpl.InstitutionImpl;

import java.time.LocalDateTime;

public class InstitutionDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PickingMapException {
        
        ImportAPI importAPI = new ImportAPI();
        VehicleImpl v1 = new VehicleImpl(2300, ItemType.CLOTHING, true);
        VehicleImpl v2 = new VehicleImpl(1000, ItemType.NON_PERISHABLE_FOOD, true);
        VehicleImpl v3 = new VehicleImpl(1200, ItemType.MEDICINE, true);
        RefrigeratedVehicle v4 = new RefrigeratedVehicle(10000, true, 15000);
        RefrigeratedVehicle v5 = new RefrigeratedVehicle(10000, true, 20000);

        VehicleImpl[] vehicles = new VehicleImpl[5];
        vehicles[0] = v1;
        vehicles[1] = v2;
        vehicles[2] = v3;
        vehicles[3] = v4;
        vehicles[4] = v5;

        InstitutionImpl i1 = new InstitutionImpl("Instituicao 1", importAPI.getAidBoxArray(), vehicles);
        StrategyImpl strategy = new StrategyImpl();
        RouteValidatorImpl validator = new RouteValidatorImpl();

        ReportImpl report = new ReportImpl();

        RouteGeneratorImpl routeGenerator = new RouteGeneratorImpl();


        PickingMapImpl pm = new PickingMapImpl(LocalDateTime.now());
        pm.setRoutes(routeGenerator.generateRoutes(i1, strategy, validator, report));

        Route[] routeAA = strategy.generate(i1, validator);
        RouteImpl[] route2 = new RouteImpl[routeAA.length];
        for (int i = 0; i < routeAA.length; i++) {
            route2[i] = (RouteImpl) routeAA[i];
        }


        System.out.println(routeAA.length);
        for (int i = 0; i < routeAA.length; i++) {
            System.out.println(routeAA[i].getVehicle().toString());
            //System.out.println(routeAA[i].getTotalDistance());
        }
    }
    
}
