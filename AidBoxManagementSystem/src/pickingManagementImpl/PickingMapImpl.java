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

import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Route;

import java.time.LocalDateTime;

/**
 * This class represents an implementation of the PickingMap interface.
 * It contains information about the picking map such as the date and the routes.
 * It also contains methods to manipulate and retrieve this information.
 */
public class PickingMapImpl implements PickingMap {

    private LocalDateTime date;
    private Route[] routes;
    private int numberOfRoutes;

    /**
     * Constructs a new PickingMapImpl with the specified date.
     *
     * @param date The date of the picking map.
     */
    public PickingMapImpl(LocalDateTime date) {
        this.date = date;
        this.routes = new Route[10];
        this.numberOfRoutes = 0;
    }

    /**
     * Constructs a new PickingMapImpl with the specified date and routes.
     *
     * @param date The date of the picking map.
     * @param routes The routes of the picking map.
     */
    public PickingMapImpl(LocalDateTime date, Route[] routes) {
        this.date = date;
        this.routes = routes;
        for (int i = 0; i < routes.length; i++) {
            if (routes[i] != null) {
                this.numberOfRoutes++;
            }
        }
    }

    /**
     * Returns the date of the picking map.
     *
     * @return The date of the picking map.
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date of the picking map.
     *
     * @param date The new date of the picking map.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Returns the routes of the picking map.
     *
     * @return An array of routes.
     */
    @Override
    public Route[] getRoutes() {
        return routes;
    }

    /**
     * Sets the routes of the picking map.
     *
     * @param routes The new routes of the picking map.
     */
    public void setRoutes(Route[] routes) {
        this.routes = routes;
    }
}
