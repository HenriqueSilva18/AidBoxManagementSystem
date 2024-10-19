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
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.GeographicCoordinates;
import com.estg.core.ItemType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;

/**
 * This class represents an implementation of the AidBox interface.
 * It contains information about the aid box such as code, zone, reference location, distance, duration, and geographic coordinates.
 * It also contains an array of containers and the number of containers.
 */

public class AidBoxImpl implements AidBox{

    private String code;
    private String zone;
    private String refLocal;
    private double distance;
    private double duration;
    private Container[] containers;
    private int numberOfContainers;
    private GeographicCoordinates geographicCoordinates;

    /**
     * Constructs a new AidBoxImpl with the specified values.
     *
     * @param code The code of the aid box.
     * @param zone The zone of the aid box.
     * @param refLocal The reference location of the aid box.
     * @param distance The distance of the aid box.
     * @param duration The duration of the aid box.
     * @param geographicCoordinates The geographic coordinates of the aid box.
     */
    public AidBoxImpl(String code, String zone, String refLocal, double distance, double duration, GeographicCoordinates geographicCoordinates) {
        this.code = code;
        this.zone = zone;
        this.refLocal = refLocal;
        this.distance = distance;
        this.duration = duration;
        this.geographicCoordinates = geographicCoordinates;
        this.containers = new Container[ItemType.values().length];
        this.numberOfContainers = 0;
    }

    /**
     * Constructs a new AidBoxImpl with the specified values including containers.
     *
     * @param code The code of the aid box.
     * @param zone The zone of the aid box.
     * @param refLocal The reference location of the aid box.
     * @param distance The distance of the aid box.
     * @param duration The duration of the aid box.
     * @param geographicCoordinates The geographic coordinates of the aid box.
     * @param containers The containers of the aid box.
     * @param numberOfContainers The number of containers in the aid box.
     */
    public AidBoxImpl(String code, String zone, String refLocal, double distance, double duration, GeographicCoordinates geographicCoordinates, ContainerImpl[] containers, int numberOfContainers) {
        this.code = code;
        this.zone = zone;
        this.refLocal = refLocal;
        this.distance = distance;
        this.duration = duration;
        this.geographicCoordinates = geographicCoordinates;
        this.containers = containers;
        this.numberOfContainers = numberOfContainers;
    }

    /**
     * Returns the code of the aid box.
     *
     * @return the code of the aid box.
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the zone of the aid box.
     *
     * @return the zone of the aid box.
     */
    @Override
    public String getZone() {
        return zone;
    }

    /**
     * Returns the reference location of the aid box.
     *
     * @return the reference location of the aid box.
     */
    @Override
    public String getRefLocal() {
        return refLocal;
    }

    /**
     * Returns the distance of the aid box compared to another aid box.
     *
     * @param aidbox the other aid box to compare with.
     * @return the distance to the other aid box.
     * @throws AidBoxException if the other aid box is null.
     */
    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        //Check if aidbox is null
        if (aidbox == null) {
            throw new AidBoxException("AidBox does not exist.");
        }

        //Get the distance of the AidBox compared to another AidBox from ImportAPI
        ImportAPI importAPI = new ImportAPI();
        return importAPI.getDistance(this, (AidBoxImpl) aidbox);
    }

    /**
     * Returns the duration of the aid box compared to another aid box.
     *
     * @param aidbox the other aid box to compare with.
     * @return the duration to the other aid box.
     * @throws AidBoxException if the other aid box is null.
     */
    @Override
    public double getDuration(AidBox aidbox) throws AidBoxException {
        //Check if aidbox is null
        if (aidbox == null) {
            throw new AidBoxException("AidBox does not exist.");
        }
        //Get the duration of the AidBox compared to another AidBox from ImportAPI
        ImportAPI importAPI = new ImportAPI();
        return importAPI.getDuration(this, (AidBoxImpl)aidbox);
    }

    /**
     * Returns the geographic coordinates of the aid box.
     *
     * @return the geographic coordinates of the aid box.
     */
    @Override
    public GeographicCoordinates getCoordinates() {
        return geographicCoordinates;
    }

    /**
     * Adds a container to the aid box.
     *
     * @param container the container to be added.
     * @return true if the container was added, false otherwise.
     * @throws ContainerException if the container is null or if there is already a container of the same type.
     */
    @Override
    public boolean addContainer(Container container) throws ContainerException {
        //Check if the container is null
        if (container == null) {
            throw new ContainerException("Container is null."); 
        }

        for (int i = 0; i < numberOfContainers; i++) {
            //Check if the container already exists in the AidBox
            if (containers[i] == container) {
                System.out.println("Container already exists in Aidbox.");
                return false;
            }
            //Check if the AidBox already has a container with the same type
            if (containers[i].getType() == container.getType()) {
                throw new ContainerException("AidBox already have a container with the same type."); 
            }           
        }

        containers[numberOfContainers] = container;
        numberOfContainers++;
        return true;
    }

    /**
     * Returns the container of the specified type.
     *
     * @param it the type of the container.
     * @return the container of the specified type, or null if not found.
     */
    @Override
    public Container getContainer(ItemType it) {
        for (int i = 0; i < numberOfContainers; i++) {
            if (containers[i].getType() == it) {
                return containers[i];
            }
        }
        return null;
    }

    /**
     * Returns the containers of the aid box.
     *
     * @return an array of containers in the aid box.
     */
    @Override
    public Container[] getContainers() {
        return containers;
    }

    /**
     * Lists the containers in the aid box.
     */
    public void listContainers() {
        for (int i = 0; i < numberOfContainers; i++) {
            System.out.println(containers[i].toString());
        }
    }

    /**
     * Returns a string representation of the aid box.
     *
     * @return a string containing the aid box information.
     */
    @Override
    public String toString() {
        String string = "Code: " + code + "\nZone: " + zone + "\nDescription: " + refLocal + "\nGeographic Coordinates: " + geographicCoordinates.toString();

        //Adds possible containers to the string, or no containers if there are none
        if (numberOfContainers != 0) {
            string += "\nContainers: \n";
            for (int i = 0; i < numberOfContainers; i++) {
                string += containers[i].toString() + "\n";
            }
        } else {
            string += "\nNo Containers in this Aidbox.\n";
        }

        return string;
    }

    /**
     * Returns the number of containers in the aid box.
     *
     * @return the number of containers in the aid box.
     */
    public int getNumberOfContainers() { return numberOfContainers; }

    /**
     * Sets the number of containers in the aid box.
     *
     * @param numberOfContainers the new number of containers.
     * @return the updated number of containers in the aid box.
     */
    public int setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
        return this.numberOfContainers;
    }
}
