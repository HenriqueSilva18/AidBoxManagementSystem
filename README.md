<h1>Aid Box Management System</h1>

<h2>Description</h2>
This university project is an Aid Box Management System developed in Java. It allows the management of humanitarian aid boxes and their containers, which are distributed across various regions. The system collects data from an external API, tracks the containers' content, and calculates distances between aid boxes for optimized logistics. Each aid box can contain different types of items like perishable food, non-perishable food, clothing, and medicine, with some requiring refrigerated vehicles for transportation. The system supports retrieving real-time measurements from containers, determining optimal routes, and managing vehicle logistics for efficient aid collection and delivery.
<br />

<h2>Technologies Used</h2>
<ul>
    <li>Programming Language: Java</li>
    <li>JSON Data Parsing</li>
    <li>RESTful API Integration</li>
    <li>Object-Oriented Design</li>
</ul>

<h2>Features</h2>
<ul>
    <li><strong>Aid Box Management</strong>: Retrieve, store, and manage information about aid boxes and their containers from an external MongoDB-based API.</li>
    <li><strong>Real-time Measurements</strong>: Track and update container measurements such as weight or volume, with a focus on perishable and non-perishable items.</li>
    <li><strong>Logistical Planning</strong>: Calculate distances and travel durations between aid boxes and the base for optimized route planning.</li>
    <li><strong>Vehicle Management</strong>: Handle refrigerated and non-refrigerated vehicles, each with specific capacities and limitations for the transportation of different item types.</li>
    <li><strong>Route Generation</strong>: Generate optimized routes for vehicle-based collection, considering item types and vehicle capacities.</li>
</ul>

<h2>Project Structure</h2>
<ul>
    <li><strong>coreImpl</strong>: Contains core classes responsible for the representation and handling of AidBox, Container, GeographicCoordinates, Institution, and Measurement entities.
        <ul>
            <li><code>AidBoxImpl.java</code>: Manages the properties and operations related to an aid box.</li>
            <li><code>ContainerImpl.java</code>: Handles the storage and properties of individual containers within aid boxes.</li>
            <li><code>GeographicCoordinatesImpl.java</code>: Represents geographic locations of aid boxes.</li>
            <li><code>InstitutionImpl.java</code>: Manages institutional operations related to aid box collections.</li>
            <li><code>MeasurementImpl.java</code>: Tracks container measurements, such as weight or volume.</li>
        </ul>
    </li>
    <li><strong>Demo</strong>: Contains demonstration classes for various functionalities.
        <ul>
            <li><code>InstitutionDemo.java</code>: Demonstrates institutional operations.</li>
            <li><code>importDemo.java</code>: Demonstrates the import of data from the API.</li>
            <li><code>menuDemo.java</code>: Demonstrates menu-based operations for managing aid box data.</li>
        </ul>
    </li>
    <li><strong>Import</strong>: Contains classes related to the interaction with external APIs.
        <ul>
            <li><code>importAPI.java</code>: Handles API requests for retrieving aid box data, container measurements, and distances between aid boxes.</li>
        </ul>
    </li>
    <li><strong>pickingManagementImpl</strong>: Contains classes related to route planning and vehicle management.
        <ul>
            <li><code>PickingMapImpl.java</code>: Implements the picking map, associating containers with routes.</li>
            <li><code>RouteGeneratorImpl.java</code>: Generates routes for collecting aid from multiple boxes.</li>
            <li><code>RouteValidatorImpl.java</code>: Validates whether a route is feasible based on container and vehicle data.</li>
            <li><code>VehicleImpl.java</code>: Manages vehicle properties, including capacity and item type restrictions.</li>
            <li><code>RefrigeratedVehicle.java</code>: Represents refrigerated vehicles for perishable food transport.</li>
            <li><code>RouteImpl.java</code>: Handles operations related to the delivery routes.</li>
            <li><code>StrategyImpl.java</code>: Implements strategies for optimized route generation and item collection.</li>
            <li><code>ReportImpl.java</code>: Generates reports on routes, vehicle usage, and container status.</li>
        </ul>
    </li>
</ul>
