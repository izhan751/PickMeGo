# PickMeGo

**PickMeGo** is a desktop ride-booking application built with **Java Swing**. Passengers can register, add balance to a wallet, calculate fares for different vehicle types and pre-schedule rides, while drivers can browse and accept pending pre-bookings.

This project was built as a software design term project and demonstrates several classic **design patterns** in a working GUI application.

## Features

### Passenger
- Register and log in as a passenger
- Add funds to an in-app wallet
- Calculate the estimated fare by distance and time
- Choose a vehicle type: **Car**, **Bike**, **Auto-Rickshaw** or **Cargo**
- Pre-schedule a ride with pickup location, drop-off location and pickup time
- A 10% deposit of the estimated fare is deducted at booking time
- Cancel a pending pre-booking and get the deposit refunded

### Driver
- Register and log in as a driver
- View all available (pending) pre-bookings
- Accept a pre-booking

## Fare Calculation

Fare = `base fare + (distance in km x per-km rate) + (time in minutes x per-minute rate)`

| Vehicle | Base fare | Per km | Per minute |
|---------|-----------|--------|------------|
| Bike | Rs 20 | Rs 8 | Rs 1 |
| Auto-Rickshaw | Rs 30 | Rs 10 | Rs 2 |
| Car | Rs 50 | Rs 15 | Rs 3 |
| Cargo | Rs 100 | Rs 20 | Rs 5 |

## Design Patterns Used

| Pattern | Where |
|---------|-------|
| **Strategy** | `FareStrategy`, `FareContext` and the vehicle fare classes (`CarFareStrategy`, `BikeFareStrategy`, `AutoFareStrategy`, `CargoFareStrategy`) |
| **Factory Method** | `RideFactory`, `RegularRideFactory`, `PremiumRideFactory` |
| **Observer** | `RideSubject`, `Observer`, `Ride` (status change notifications) |
| **Singleton** | `SessionManager` (current logged-in user) |

## Tech Stack

- Java 23
- Java Swing (`CardLayout` for screen navigation)
- Maven

## Project Structure

```
PickMeGo/
├── pom.xml
├── resources/                      # Static HTML/CSS login page mockup
└── src/main/java/com/termproject/pickmego/
    ├── PickMeGo.java               # Application entry point (main)
    ├── MainFrame.java              # Main window and panel navigation
    ├── LoginPanel.java
    ├── RegistrationPanel.java
    ├── PassengerPanel.java
    ├── DriverPanel.java
    ├── User.java / PassengerUser.java / DriverUser.java
    ├── UserManager.java            # Registration and authentication
    ├── SessionManager.java         # Singleton session holder
    ├── PreBooking.java / PreBookingManager.java
    ├── Ride.java / PremiumRide.java / *RideFactory.java
    ├── FareStrategy.java / FareContext.java / *FareStrategy.java
    └── Observer.java / RideSubject.java
```

## Getting Started

### Prerequisites

- **JDK 23** (the `pom.xml` is set to `maven.compiler.release` 23)
- **Maven** 3.8 or higher (or an IDE such as NetBeans, IntelliJ IDEA or Eclipse)

### Run from the command line

```bash
git clone https://github.com/<your-username>/PickMeGo.git
cd PickMeGo
mvn compile exec:java -Dexec.mainClass=com.termproject.pickmego.PickMeGo
```

### Run from an IDE

1. Open the project folder (it is a standard Maven project).
2. Run the `PickMeGo` class in `com.termproject.pickmego`.

## How to Use

1. Start the app and click **Don't have an account? Register Here**.
2. Register as a **Passenger** and as a **Driver** (two accounts).
3. Log in as the passenger, click **Add Balance**, then **Pre-Schedule a Ride** and fill in the details.
4. Log out, log in as the driver, click **View Available Pre-Bookings** and accept the ride.

## Known Limitations

- All data (users, balances, bookings) is stored **in memory** and is lost when the app closes.
- Passwords are stored as plain text, so this app is for learning only and is not production-ready.
- The `resources/` folder is a static HTML mockup and is not connected to the Java application.

## Future Improvements

- [ ] Persistent storage (database or file)
- [ ] Password hashing
- [ ] Real-time ride tracking and live ride status
- [ ] Ratings and payment history
- [ ] Unit tests

## License

Add a license of your choice (for example MIT) in a `LICENSE` file.
