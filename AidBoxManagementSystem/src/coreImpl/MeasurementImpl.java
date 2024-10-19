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

import com.estg.core.Measurement;
import java.time.LocalDateTime;

/**
 * This class represents an implementation of the Measurement interface.
 * It contains information about the measurement such as date and value.
 */

public class MeasurementImpl implements Measurement{

    private LocalDateTime date;
    private double value;

    /**
     * Constructs a new MeasurementImpl with the specified values.
     *
     * @param date The date of the measurement.
     * @param value The value of the measurement.
     */
    public MeasurementImpl(LocalDateTime date, double value) {
        this.date = date;
        this.value = value;
    }

    /**
     * Returns the date of the measurement.
     *
     * @return The date of the measurement.
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns the value of the measurement.
     *
     * @return The value of the measurement.
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * Returns a string representation of the measurement.
     *
     * @return A string representation of the measurement.
     */
    @Override
    public String toString(){
        return "Date: " + date + "\nValue: " + value;
    }
    
}
