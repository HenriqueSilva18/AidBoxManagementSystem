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

import com.estg.core.Container;
import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDate;

/**
 * This class represents an implementation of the Container interface.
 * It contains information about the container such as code, total capacity, current capacity, type, and measurements.
 * It also contains methods to manipulate and retrieve this information.
 */
public class ContainerImpl implements Container{

    private static final int MEASUREMENT_INIT = 10;
    private String code;
    private double totalCapacity;
    private double capacity;
    private ItemType type;
    private Measurement[] measurements;
    private int numberOfMeasurements;

    /**
     * Constructs a new ContainerImpl with the specified values.
     *
     * @param code The code of the container.
     * @param totalCapacity The total capacity of the container.
     * @param type The type of the container.
     */
    public ContainerImpl(String code, double totalCapacity, ItemType type) {
        this.code = code;
        this.totalCapacity = totalCapacity;
        this.type = type;
        this.measurements = new Measurement[MEASUREMENT_INIT];
        this.numberOfMeasurements = 0;
        this.capacity = getFirstCapacity();
    }

    /**
     * Returns the code of the container.
     *
     * @return The code of the container.
     */
    @Override
    public String getCode() {
        switch(type){
            case PERISHABLE_FOOD:
                return "P" + code;
            case NON_PERISHABLE_FOOD:
                return "N" + code;
            case CLOTHING:
                return "V" + code;
            default:
                return "M" + code;
        }
    }

    /**
     * Returns the current capacity of the container.
     *
     * @return The current capacity of the container.
     */
    @Override
    public double getCapacity() {
        return capacity;
    }

    /**
     * Returns the first capacity of the container.
     *
     * @return The first capacity of the container.
     */
    public double getFirstCapacity(){
        if (numberOfMeasurements == 0){
            return 0;
        }
        return measurements[numberOfMeasurements - 1].getValue();
    }

    /**
     * Returns the type of the container.
     *
     * @return The type of the container.
     */
    @Override
    public ItemType getType() {
        return type;
    }

    /**
     * Returns all measurements of the container.
     *
     * @return An array of all measurements of the container.
     */
    @Override
    public Measurement[] getMeasurements() {
        return measurements;
    }

    /**
     * Returns measurements of the container for a specific date.
     *
     * @param ld The specific date for which measurements are to be returned.
     * @return An array of measurements for the specific date.
     */
    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        int count = 0;
        for(int i = 0; i < numberOfMeasurements; i++){
            if(measurements[i].getDate().toLocalDate().equals(ld)){
                count++;
            }           
        }
        
        Measurement[] filteredMeasurements = new Measurement[count];
        
        int index = 0;
        for(int i = 0; i < numberOfMeasurements; i++){
            if(measurements[i].getDate().toLocalDate().equals(ld)){
                filteredMeasurements[index++] = measurements[i];
            }           
        }
        
        return filteredMeasurements;
    }

    /**
     * Adds a measurement to the container.
     *
     * @param measurement The measurement to be added.
     * @return true if the measurement was added successfully, false otherwise.
     * @throws MeasurementException If the measurement is null, the value is less than zero, the date is before the existing last measurement date, or the measurement capacity is higher than the container's maximum capacity.
     */
    @Override
    public boolean addMeasurement(Measurement measurement) throws MeasurementException {
        //Check if the measurement is null
        if (measurement == null) {
            throw new MeasurementException("Measurement is null.");
        }

        //Check if the value of the measurement is less than zero
        if (measurement.getValue() < 0) {
            throw new MeasurementException("Value is less than zero.");
        }

        //Check if the date of the measurement is before the existing last measurement date
        if (numberOfMeasurements > 0 && measurement.getDate().isBefore(measurements[numberOfMeasurements - 1].getDate())) {
            throw new MeasurementException("Date is before the existing last measurement date.");
        }

        for(int i = 0; i < numberOfMeasurements; i++){
            //Check if the measurement already exists for a given date but with different values
            if (measurement.getDate().equals(measurements[i].getDate()) && measurement.getValue() != measurements[i].getValue()) {
                throw new MeasurementException("For a given date the measurement already exists but the values are different.");
            }
        }

        //Check if the measurements array is full, if so, expand it
        if (measurements.length == numberOfMeasurements) {
            expandArray();
        }

        //Check if the value of the measurement is higher than the total capacity of the container
        if (measurement.getValue() > totalCapacity) {
            System.out.println(this.code);
            throw new MeasurementException("Measurement capacity is higher than Container maximum capacity.");
        }

        measurements[numberOfMeasurements] = measurement;
        numberOfMeasurements++;
        capacity = getFirstCapacity();
        return true;
    }

    /**
     * Expands the measurements array by a constant factor.
     * This method is called when the measurements array is full and needs to accommodate more measurements.
     */

    private void expandArray(){
        Measurement[] expandedArray = new Measurement[this.measurements.length + MEASUREMENT_INIT];
        
        for (int i = 0; i < measurements.length; i++) {
            expandedArray[i] = measurements[i];
        }
        
        measurements = expandedArray;
    }

    /**
     * Prints all measurements of the container.
     */
    public void listMeasurements(){
        for (int i = 0; i < numberOfMeasurements; i++) {
            System.out.println("Date: " + measurements[i].getDate() + "\nValue: " + measurements[i].getValue());     
        }
    }

    /**
     * Returns a string representation of the container.
     *
     * @return A string representation of the container.
     */
    @Override
    public String toString(){
        String string = "Code: " + code + "\nTotal Capacity: " + totalCapacity +"\nCapacity: " + capacity + "\nContainer Type: " + type;
        
        if (numberOfMeasurements != 0) {
            string += "\nMeasurements: \n";
            for (int i = 0; i < numberOfMeasurements; i++) {
                string += measurements[i].toString() + "\n";
            }
        } else {
            string += "\nNo Measurements in this container.\n";
        }

        return string;
    }

    /**
     * Returns the number of measurements in the container.
     *
     * @return The number of measurements in the container.
     */
    public double getNumberOfMeasurements() {
        return numberOfMeasurements;
    }

    /**
     * Sets the current capacity of the container.
     *
     * @param capacity The new current capacity of the container.
     */
    public void setCapacity(double capacity){
        this.capacity = capacity;
    }
}
