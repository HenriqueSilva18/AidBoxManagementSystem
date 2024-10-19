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

import com.estg.core.GeographicCoordinates;

/**
 * This class represents an implementation of the GeographicCoordinates interface.
 * It contains information about the geographic coordinates such as latitude and longitude.
 */
public class GeographicCoordinatesImpl implements GeographicCoordinates {

    private double latitude;
    private double longitude;

    /**
     * Constructs a new GeographicCoordinatesImpl with the specified values.
     *
     * @param latitude The latitude of the geographic coordinates.
     * @param longitude The longitude of the geographic coordinates.
     */
    public GeographicCoordinatesImpl(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns the latitude of the geographic coordinates.
     *
     * @return The latitude of the geographic coordinates.
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the geographic coordinates.
     *
     * @return The longitude of the geographic coordinates.
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns a string representation of the geographic coordinates.
     *
     * @return A string representation of the geographic coordinates.
     */
    @Override
    public String toString(){
        return "Latitude: " + latitude + "\nLongitude: " + longitude;
    }
}
