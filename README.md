
# Taxi!

- [Taxi!](#taxi-)
    + [**   IMPORTANT**](#-----important--)
    + [APK](#apk)
    + [Demo Video](#demo-video)
    + [App description:](#app-description-)
    + [Technical notes:](#technical-notes-)
    + [Some of the used SDKs:](#some-of-the-used-sdks-)
    + [Architecture](#architecture)
  * [Build/Run the app](#build-run-the-app)
  * [Screenshots](#screenshots)
    
<p align="center">
   <img src="https://github.com/islamassi/GetTaxi/blob/master/screenshots/car_move.gif?raw=true" width="350" >
 </p>
<p align="center">
   <img src="https://github.com/islamassi/GetTaxi/blob/master/screenshots/parallel.gif?raw=true" width="350" >
   <img src="https://github.com/islamassi/GetTaxi/blob/master/screenshots/path.gif?raw=true" width="350" >
 </p>


## **IMPORTANT**
* **Please allow location permission so all features in the app will operate correctly**
* The app was built in April 2019
* If I will rebuild the app, I will build it in **Kotlin**.
* If you would like to check a Kotlin project, please navigate to <a href="https://github.com/islamassi/IA_NewsApp">**IA_News**</a>

### APK 
<a href="https://github.com/islamassi/GetTaxi/raw/master/apk/Taxi.apk">**Taxi.apk**</a>

### Demo Video 
<a href="">Click here</a>

###   App description:
* This app consists of two main fragments (screens). **VehicleListFragment** and **VehiclesMapFragment**.
* **VehicleListFragment** which shows a list of available vehicles in Hamburg.
* **VehiclesMapFragment** which shows the requested vehicles in the map.
* The vehicle card shows the **time** and **distance** that is needed by th car to arrive the user. It is also showing where the vehicle is **heading**, its **address**, and an indication weather if it is a **pooling** vehicle.
<p align="center">
  <img src="https://github.com/islamassi/GetTaxi/blob/master/screenshots/10.jpg?raw=true" width="350" >
</p>

* I get the list of vehicles by a GET call to:
https://fake-poi-api.mytaxi.com/?p1Lat={Latitude1}&p1Lon={Longitude1}&p2Lat={Latitude2}&p2Lon={Longit ude2}
* For each vehicle, I initiate a call to Google Directions API to get information regarding the time, distance, path needed for this vehicle to arrive to the user. 
* Requests to Google Directions API are being done in **parallel**.
* When the user selects a vehicle, the map view will open showing all vehicles, the selected vehicle card, and draw a path between the selected vehicle and the user location. The map view will modify the zoom to show the path between the user and the selected vehicle.
* When user is in the **VehiclesMapFragment**, moving the map camera will result in a new API call within the map's camera boundaries. The **VehicleListFragment** will be updated by this call to show the new requested vehicles.
* Clicking on a vehicle in **VehiclesMapFragment** will show the vehicle card and the path between it and the user.
* in **VehicleListFragment**, the user have the ability to sort the list by duration or distance.

**This app was implemented in March 2019**

###   Technical notes:
* I used MVVM architecture with Android Architecture components and Android Jetpack including **ViewModel*** and **LiveData**.
* RxJava is being used to iterate through all the vehicles and request Google Directions API to get info regarding, time, distance, and path between user's location and the vehicle. Every call to Directions API will be merged with vehicle object using RxJava zip method to return a **RideOption** object that contains vehicle info and ride info.
* The user don't have to wait for all these requests to finish. Each successful request will be directly shown in the vehicles list.
* A shared view model is being used by both **VehicleListFragment** and **VehiclesMapFragment** so they will share the same vehicle list. Each of them inherits this shared view model for custom logic regarding that fragment.
* Before initiating a new request, the previous one is being disposed.

### Some of the used SDKs:
- LiveData
- ViewModel
- Databinding
- Retrofit
- Dagger 2
- Rxjava
- Espresso
- Google Maps

### Architecture 
 The app was built with a clean MVVM architecture following Android Architecture components and Android Jetpack.
 This is very important to make the code base more **robust**, **testable**, and **maintainable** in the long run.
 Please have a look to the implementation.

https://developer.android.com/jetpack/docs/guide#best-practices

![alt text](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Build/Run the app
To run the app successfully, you need to add Google Maps API key to the strings.xml file

## Screenshots

<p align="center">
  <img src="https://github.com/islamassi/GetTaxi/blob/master/screenshots/9.jpg?raw=true" width="350" >
  <img src="https://github.com/islamassi/GetTaxi/blob/master/screenshots/5.jpg?raw=true" width="350" >
  <img src="https://github.com/islamassi/GetTaxi/blob/master/screenshots/7.jpg?raw=true" width="350" >
</p>

### Feedback
Your Feedback is highly appreciated.
