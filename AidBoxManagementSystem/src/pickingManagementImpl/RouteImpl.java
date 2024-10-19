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

import Import.ImportAPI;
import com.estg.core.AidBox;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import coreImpl.AidBoxImpl;
import coreImpl.ContainerImpl;

/**
 * This class implements the Route interface, providing functionality to manage
 * a route composed of multiple AidBoxes and a Vehicle.
 */
public class RouteImpl implements Route {

    private static final int EXPAND_SIZE = 4;
    private static int INITIAL_AIDBOX_SIZE = 2;
    private AidBox[] aidboxes;
    private int numberOfAidboxes;
    private Vehicle vehicle;

    /**
     * Constructs a RouteImpl with a specified vehicle.
     * Initializes the aidboxes array with an initial size.
     *
     * @param vehicle The vehicle associated with this route.
     */
    public RouteImpl(Vehicle vehicle) {
        this.vehicle = vehicle;
        aidboxes = new AidBox[INITIAL_AIDBOX_SIZE];
        numberOfAidboxes = 0;
    }

    /**
     * Constructs a RouteImpl with a default initial aidboxes size.
     */
    public RouteImpl() {
        aidboxes = new AidBox[10];
        numberOfAidboxes = 0;
    }

    /**
     * Removes the last AidBox from the route.
     */
    public void removeLastAidBox() {
        aidboxes[numberOfAidboxes - 1] = null;
        numberOfAidboxes--;
    }

    /**
     * Expands the aidboxes array when it reaches its capacity.
     */
    private void expandAidBoxes() {
        AidBox[] newAidBoxes = new AidBox[aidboxes.length + EXPAND_SIZE];
        for (int i = 0; i < numberOfAidboxes; i++) {
            newAidBoxes[i] = aidboxes[i];
        }
        aidboxes = newAidBoxes;
    }

    /**
     * Searches for a specific AidBox in the route.
     *
     * @param aidbox The AidBox to search for.
     * @return The index of the AidBox if found, otherwise -1.
     */
    private int searchAidBox(AidBox aidbox) {
        for (int i = 0; i < numberOfAidboxes; i++) {
            if (aidboxes[i] == aidbox) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds an AidBox to the route.
     *
     * @param aidbox The AidBox to add.
     * @throws RouteException if the AidBox is null, already in the route, or not compatible with the vehicle.
     */
    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("Aid Box is null.");
        }

        if (searchAidBox(aidbox) != -1) {
            throw new RouteException("Aid Box is already in the route.");
        }

        if (numberOfAidboxes == aidboxes.length) {
            expandAidBoxes();
        }

        if (aidbox.getContainer(vehicle.getSupplyType()) == null) {
            throw new RouteException("Aid Box is not compatible with the Vehicle of the route.");
        } else {
            aidboxes[numberOfAidboxes++] = aidbox;
        }
    }

    /**
     * Removes a specified AidBox from the route.
     *
     * @param aidbox The AidBox to remove.
     * @return The removed AidBox.
     * @throws RouteException if the AidBox is null or not in the route.
     */
    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {

        int indexRemovedAidBox = searchAidBox(aidbox);

        if (aidbox == null) {
            throw new RouteException("Aid Box is null.");
        }

        if (indexRemovedAidBox == -1) {
            throw new RouteException("AidBox is not in this route.");
        }

        aidboxes[indexRemovedAidBox] = null;

        for (int i = indexRemovedAidBox; i < numberOfAidboxes - 1; i++) {
            aidboxes[i] = aidboxes[i + 1];
        }
        aidboxes[numberOfAidboxes--] = null;
        return aidbox;
    }

    /**
     * Checks if the route contains a specified AidBox.
     *
     * @param aidbox The AidBox to check.
     * @return True if the route contains the AidBox, otherwise false.
     */
    @Override
    public boolean containsAidBox(AidBox aidbox) {
        return searchAidBox(aidbox) != -1;
    }

    /**
     * Replaces an existing AidBox in the route with a new one.
     *
     * @param old The existing AidBox to replace.
     * @param replacement The new AidBox to replace the old one.
     * @throws RouteException if any AidBox is null, not in the route, or not compatible with the vehicle.
     */
    @Override
    public void replaceAidBox(AidBox old, AidBox replacement) throws RouteException {
        if (old == null || replacement == null) {
            throw new RouteException("Aid Box is null.");
        }

        int indexOld = searchAidBox(old);

        if (indexOld == -1) {
            throw new RouteException("Aid Box to be replaced is not in the route.");
        }

        if (searchAidBox(replacement) != -1) {
            throw new RouteException("Aid Box to insert is already in the route.");
        }
        
        if (replacement.getContainer(vehicle.getSupplyType()) == null) {
            throw new RouteException("Aid Box is not compatible with the Vehicle of the route.");
        }

        aidboxes[indexOld] = replacement;
    }

    /**
     * Inserts a new AidBox after a specified AidBox in the route.
     *
     * @param after The existing AidBox after which to insert the new AidBox.
     * @param toInsert The new AidBox to insert.
     * @throws RouteException if any AidBox is null, not in the route, already in the route, or not compatible with the vehicle.
     */
    @Override
    public void insertAfter(AidBox after, AidBox toInsert) throws RouteException {

        if (after == null || toInsert == null) {
            throw new RouteException("Aid Box is null.");
        }

        if (containsAidBox(toInsert)) {
            throw new RouteException("Aid Box to insert is already in the route.");
        }

        if (!containsAidBox(after)) {
            throw new RouteException("Aid Box to insert before is not in the route.");
        }

        int indexAfter = searchAidBox(after);

        if (toInsert.getContainer(vehicle.getSupplyType()) == null) {
            throw new RouteException("Aid Box is not compatible with the Vehicle of the route.");
        }

        if (aidboxes.length == numberOfAidboxes) {
            expandAidBoxes();
        }

        for (int i = numberOfAidboxes; i > indexAfter + 1; i--) {
            aidboxes[i] = aidboxes[i - 1];
        }

        aidboxes[searchAidBox(after) + 1] = toInsert;
        numberOfAidboxes += 1;
    }

    /**
     * Gets the current route as an array of AidBoxes.
     *
     * @return An array of AidBoxes in the route.
     */
    @Override
    public AidBox[] getRoute() {
        return aidboxes;
    }

    /**
     * Gets the current aidboxes as an array of AidBoxImpl.
     *
     * @return An array of AidBoxImpls in the route.
     */
    public AidBoxImpl[] getAidBoxes() {
        AidBoxImpl[] aidBoxImpls = new AidBoxImpl[numberOfAidboxes];
        for (int i = 0; i < numberOfAidboxes; i++) {
            aidBoxImpls[i] = (AidBoxImpl) aidboxes[i];
        }
        return aidBoxImpls;
    }

    /**
     * Gets the vehicle associated with this route.
     *
     * @return The vehicle of the route.
     */
    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Gets the total distance of the route.
     *
     * @return The total distance of the route.
     */
    @Override
    public double getTotalDistance() {
        double totalDistance = 0;
        ImportAPI importAPI = new ImportAPI();

        for (int i = 0; i < numberOfAidboxes - 1; i++) {
            //Get the distance of the first AidBox compared to the institution from ImportAPI
            totalDistance += importAPI.getDistance((AidBoxImpl)aidboxes[0]);
            //Get the distance of the AidBox compared to the next AidBox from ImportAPI
            totalDistance += importAPI.getDistance((AidBoxImpl)aidboxes[i], (AidBoxImpl)aidboxes[i + 1]);
        }
        return totalDistance;
    }

    /**
     * Gets the total duration of the route.
     *
     * @return The total duration of the route.
     */
    @Override
    public double getTotalDuration() {
        double totalDuration = 0;
        ImportAPI importAPI = new ImportAPI();
        
        for (int i = 0; i < numberOfAidboxes - 1; i++) {
            //Get the distance of the first AidBox compared to the institution from ImportAPI
            totalDuration += importAPI.getDuration((AidBoxImpl)aidboxes[0]);
            //Get the distance of the AidBox compared to the next AidBox from ImportAPI
            totalDuration += importAPI.getDuration((AidBoxImpl)aidboxes[i], (AidBoxImpl)aidboxes[i + 1]);
        }
        return totalDuration;
    }

    /**
     * Gets the total weight of all containers in the route.
     *
     * @return The total weight of all containers in the route.
     */
    public double getTotalWeight(){
        double totalWeight = 0;
        for (int i = 0; i < numberOfAidboxes; i++) {
            for (int j = 0; j < aidboxes[i].getContainers().length; j++) {
                if (aidboxes[i].getContainers()[j] == null) {
                    continue;
                }
                totalWeight += ((ContainerImpl) aidboxes[i].getContainers()[j]).getFirstCapacity();
            }
        }
        return totalWeight;
    }

    public int getNumberOfAidboxes() {
        return numberOfAidboxes;
    }
}
