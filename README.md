# WeatherForeCast

Weather ForeCast is a android mobile application which helps the user to get information related to weather like temperature, pressure, humidity, etc

The app fetches the weather informationn for every 2 hours

Project uses following conecpts in the code
* MVVM Archiecure
* Live Data
* Data Binding
* WorkManager
* Co-routenes
* Dagger2
* Fused Location Service'
* Location Manager
* Expresso
* Mockito

#### Class Diagram
https://github.com/gkggk/WeatherForeCast/blob/master/Class%20Diagrom.png

### Functionlaity

When the app is opened it check for location permissins. If the permission is not already, it asks for the same. It also parallelly starts the workmanager to run periodically for every 2 hours.

If the permission is enabled, it fetches the current location. For fetch location, it uses Fused Location service if the locaion sevice is enabled. If the Location service is not enabled, it fetchs the location using Location Manager Service.

Once the lcatest location is received, with the help of latitude and logitude values, it fetches the current weather report using the openweathre API. Once the 
weather report is received, it shows the information on the UI. It also stores the same information in local data base to use it in future.

When the app is opened again, it fetches the weather report from the local database and shows it on the UI. If he last fetches information is greater than 2 hours, it 
fetches the latest information from the serwer again.
