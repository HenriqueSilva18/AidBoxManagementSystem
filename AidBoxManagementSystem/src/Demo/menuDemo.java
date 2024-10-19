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
import com.estg.pickingManagement.Vehicle;
import pickingManagementImpl.*;
import coreImpl.InstitutionImpl;

import java.time.LocalDateTime;
import java.util.Scanner;

public class menuDemo {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        ImportAPI importAPI = new ImportAPI();
        Vehicle[] vehicles = new Vehicle[5];
        InstitutionImpl inst1 = new InstitutionImpl("Instituicao 1", importAPI.getAidBoxArray() , vehicles);

        int option;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("1 - Add Vehicle");
            System.out.println("2 - Generate PickingMap");
            System.out.println("3 - Print Last Report");
            System.out.println("4 - Exit");
            System.out.print("Option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Maximum Capacity: ");
                    double capacity = scanner.nextDouble();
                    System.out.println("Type of Supply: ");
                    int option2;
                    do {
                        System.out.println("1 - CLOTHING");
                        System.out.println("2 - MEDICINE");
                        System.out.println("3 - PERISHABLE FOOD");
                        System.out.println("4 - NON PERISHABLE FOOD");
                        System.out.print("Option: ");
                        option2 = scanner.nextInt();
                    } while (option2 < 1 || option2 > 4);

                    switch (option2) {
                        case 1:
                            try{
                                inst1.addVehicle(new VehicleImpl(capacity, ItemType.CLOTHING, true));
                            } catch (Exception e) {
                                System.out.println("Problem occurred!" + e);
                            }
                            break;
                        case 2:
                            try{
                                inst1.addVehicle(new VehicleImpl(capacity, ItemType.MEDICINE, true));
                            } catch (Exception e) {
                                System.out.println("Problem occurred!" + e);
                            }
                            break;
                        case 3:
                            System.out.println("Max Meters of the Refrigerated Vehicle: ");
                            double maxMeters = scanner.nextDouble();
                            try{
                                inst1.addVehicle(new RefrigeratedVehicle(capacity, true, maxMeters));
                            } catch (Exception e) {
                                System.out.println("Problem occurred!" + e);
                            }
                            break;
                        case 4:
                            try{
                                inst1.addVehicle(new VehicleImpl(capacity, ItemType.NON_PERISHABLE_FOOD, true));
                            } catch (Exception e) {
                                System.out.println("Problem occurred!" + e);
                            }
                            break;
                    }
                    System.out.println("Vehicle added successfully!");
                    break;
                case 2:
                    if (inst1.getNumberOfVehicles() == 0) {
                        System.out.println("No vehicles available in the institution!");
                        break;
                    }
                    PickingMapImpl pm = new PickingMapImpl(LocalDateTime.now());

                    try{
                        pm.setRoutes(new RouteGeneratorImpl().generateRoutes(inst1, new StrategyImpl(), new RouteValidatorImpl(), new ReportImpl()));
                    } catch(Exception e) {
                        System.out.println("Problem occurred!" + e);
                    }
                    System.out.println("PickingMap generated successfully!");
                    break;
                case 3:
                    if (inst1.getLastReport() == null) {
                        System.out.println("No report available!");
                        break;
                    }
                    System.out.println(inst1.getLastReport().toString());
                    System.out.println("Vehicles Used: ");
                    for(int i = 0; i < inst1.getNumberOfVehicles(); i++){
                        System.out.println(inst1.getVehicles()[i].toString());
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != 4);
    }

}
