/*
* Nome: Agostinho Manuel da Silva Ferreira
* Número: 8210590
* Turma: LEI11T1
*
 * Nome: Henrique Miguel Gomes da Silva
 * Número: 8230097
 * Turma: LEI11T1
*/
package Import;

import com.estg.core.ItemType;
import com.estg.io.HTTPProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import coreImpl.AidBoxImpl;
import coreImpl.ContainerImpl;
import coreImpl.GeographicCoordinatesImpl;
import coreImpl.MeasurementImpl;

/**
 * This class represents an API for importing data related to aid boxes and their measurements.
 * It provides methods to retrieve and process data from external sources, specifically from a MongoDB-based endpoint.
 * The class includes functionality to parse JSON responses and convert them into AidBoxImpl and ContainerImpl objects.
 */
public class ImportAPI {

    /**
     * Retrieves an array of AidBoxImpl objects by making an HTTP request to a URL.
     * The method processes the JSON response and creates an array of aid boxes with their associated containers.
     *
     * @return an array of AidBoxImpl objects
     */
    public AidBoxImpl[] getAidBoxArray() {
        HTTPProvider httpProvider = new HTTPProvider();
        //Save the JSON string in the variable
        String jsonResponse = httpProvider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxes");
        //Parse the JSON string to a JSONArray
        JSONArray jsonAidBoxArray = parseJSONArray(jsonResponse); 

        AidBoxImpl[] aidBoxes = new AidBoxImpl[jsonAidBoxArray.size()];
        
        for (int i = 0; i < jsonAidBoxArray.size(); i++) {
            //Get the object at index i from the JSONArray
            JSONObject aidBoxObject = (JSONObject) jsonAidBoxArray.get(i); //
            //Get the JSONArray of containers from the object
            JSONArray jsonContainersArray = (JSONArray) aidBoxObject.get("Contentores");
            ContainerImpl[] containers = new ContainerImpl[jsonContainersArray.size()];
            
            for (int j = 0; j < jsonContainersArray.size(); j++) {
                //Get the object at index j from the JSONArray
                JSONObject containerObject = (JSONObject) jsonContainersArray.get(j);
                //Get the code of the container
                String code = (String) containerObject.get("codigo");
                ItemType type = null;
                //Switch statement to determine the type of the container based on the first character of the code
                switch(code.charAt(0)){
                    case 'N':
                        type = ItemType.NON_PERISHABLE_FOOD;
                        break;
                    case 'V':
                        type = ItemType.CLOTHING;
                        break;
                    case 'M':
                        type = ItemType.MEDICINE;
                        break;
                    case 'P':
                        type = ItemType.PERISHABLE_FOOD;
                        break;
                }
                //Create a new ContainerImpl object with the data from the JSON object
                containers[j] = new ContainerImpl(code.substring(1),(long) containerObject.get("capacidade"), type);
            }
            //Get the measurements for each container
            containers = getMeasurementsInContainer(containers);
            //Create a new AidBoxImpl object with the data from the JSON object
            aidBoxes[i] = new AidBoxImpl((String) aidBoxObject.get("Codigo"), (String) aidBoxObject.get("Zona"), null, 0, 0, new GeographicCoordinatesImpl((double) aidBoxObject.get("Latitude"), (double) aidBoxObject.get("Longitude")), containers, containers.length);
        }
        return aidBoxes;
    }

    /**
     * Retrieves and adds measurements to each container in the provided array.
     * The method makes an HTTP request to a predefined URL and processes the JSON response to extract measurement data.
     *
     * @param containers an array of ContainerImpl objects to be updated with measurements
     * @return an array of ContainerImpl objects with updated measurements
     */
    public ContainerImpl[] getMeasurementsInContainer(ContainerImpl[] containers) {
        HTTPProvider httpProvider = new HTTPProvider();
        //Save the JSON string in the variable
        String jsonResponse = httpProvider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/readings"); //guarda a string de JSON na variavel
        //Parse the JSON string to a JSONArray
        JSONArray jsonMeasurementsArray = parseJSONArray(jsonResponse);
        //Create a DateTimeFormatter object to parse the date and time strings
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        
        for (int i = 0; i < containers.length; i++) {
            for (int j = 0; j < jsonMeasurementsArray.size(); j++) {
                //Get the object at index j from the JSONArray
                JSONObject containerReadingObject = (JSONObject) jsonMeasurementsArray.get(j);
                
                if (containerReadingObject.get("contentor").equals(containers[i].getCode())) {
                    try {
                        //Parse the date and time string to a LocalDateTime object
                        LocalDateTime dateTime = LocalDateTime.parse((String) containerReadingObject.get("data"), formatter);
                        double value = ((long) containerReadingObject.get("valor"));
                        //Add a new measurement to the container
                        containers[i].addMeasurement(new MeasurementImpl(dateTime, value));
                    } catch (Exception e) {
                        System.out.println("Problem occurred: " + e);
                    }                
                }
            }
        }
        return containers;
    }

    /**
     * Retrieves the distance between two aid boxes by making an HTTP request to a predefined URL.
     *
     * @param aidbox1 the first aid box
     * @param aidbox2 the second aid box
     * @return the distance between the two aid boxes
     */
    public double getDistance(AidBoxImpl aidbox1, AidBoxImpl aidbox2) {
        String codeAidbox1 = aidbox1.getCode();
        String codeAidbox2 = aidbox2.getCode();       
        HTTPProvider httpProvider = new HTTPProvider();
        //Save the JSON string in the variable
        String jsonResponse = httpProvider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codeAidbox1 + "&to=" + codeAidbox2);
        //Parse the JSON string to a JSONObject
        JSONObject jsonAidboxInformation = parseJSONObject(jsonResponse);
        //Get the JSONArray of distances from the JSONObject
        JSONArray toArray = (JSONArray) jsonAidboxInformation.get("to");
        //Get the first object from the JSONArray
        JSONObject toObject = (JSONObject) toArray.get(0);

        return (long) toObject.get("distance");  
    }

    /**
     * Retrieves the duration between two aid boxes by making an HTTP request to a predefined URL.
     *
     * @param aidbox1 the first aid box
     * @param aidbox2 the second aid box
     * @return the duration between the two aid boxes
     */
    public double getDuration(AidBoxImpl aidbox1, AidBoxImpl aidbox2) {
        String codeAidbox1 = aidbox1.getCode();
        String codeAidbox2 = aidbox2.getCode();       
        HTTPProvider httpProvider = new HTTPProvider();
        //Save the JSON string in the variable
        String jsonResponse = httpProvider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codeAidbox1 + "&to=" + codeAidbox2);
        //Parse the JSON string to a JSONObject
        JSONObject jsonAidboxInformation = parseJSONObject(jsonResponse);
        //Get the JSONArray of durations from the JSONObject
        JSONArray toArray = (JSONArray) jsonAidboxInformation.get("to");
        //Get the first object from the JSONArray
        JSONObject toObject = (JSONObject) toArray.get(0);
        
        return (long) toObject.get("duration");  
    }

    /**
     * Retrieves the distance from an aid box to the base by making an HTTP request to a predefined URL.
     *
     * @param aidbox1 the aid box
     * @return the distance from the aid box to the base
     */
    public double getDistance(AidBoxImpl aidbox1) {
        String codeAidbox1 = aidbox1.getCode();      
        HTTPProvider httpProvider = new HTTPProvider();
        //Save the JSON string in the variable
        String jsonResponse = httpProvider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codeAidbox1 + "&to=Base");
        //Parse the JSON string to a JSONObject
        JSONObject jsonAidboxInformation = parseJSONObject(jsonResponse);
        //Get the JSONArray of distances from the JSONObject
        JSONArray toArray = (JSONArray) jsonAidboxInformation.get("to");
        //Get the first object from the JSONArray
        JSONObject toObject = (JSONObject) toArray.get(0);
        
        return (long) toObject.get("distance");  
    }

    /**
     * Retrieves the duration from an aid box to the base by making an HTTP request to a predefined URL.
     *
     * @param aidbox1 the aid box
     * @return the duration from the aid box to the base
     */
    public double getDuration(AidBoxImpl aidbox1) {
        String codeAidbox1 = aidbox1.getCode();       
        HTTPProvider httpProvider = new HTTPProvider();
        //Save the JSON string in the variable
        String jsonResponse = httpProvider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codeAidbox1 + "&to=Base");
        //Parse the JSON string to a JSONObject
        JSONObject jsonAidboxInformation = parseJSONObject(jsonResponse);
        //Get the JSONArray of durations from the JSONObject
        JSONArray toArray = (JSONArray) jsonAidboxInformation.get("to");
        //Get the first object from the JSONArray
        JSONObject toObject = (JSONObject) toArray.get(0);
        
        return (long) toObject.get("duration");  
    }

    /**
     * Parses a JSON string and returns a JSONArray object.
     *
     * @param jsonResponse the JSON string to parse
     * @return a JSONArray object, or null if an error occurs
     */
    private JSONArray parseJSONArray(String jsonResponse) {
        //Create a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parse the JSON string to a JSONArray
            return (JSONArray) jsonParser.parse(jsonResponse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a JSON string and returns a JSONObject object.
     *
     * @param jsonResponse the JSON string to parse
     * @return a JSONObject object, or null if an error occurs
     */
    private JSONObject parseJSONObject(String jsonResponse) {
        //Create a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parse the JSON string to a JSONObject
            return (JSONObject) jsonParser.parse(jsonResponse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
