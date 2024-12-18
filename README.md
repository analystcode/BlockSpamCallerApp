Android app that can block spam calls and allow users to manually block sales calls involves using Android's TelecomManager and CallScreeningService. 
Here's an outline of the app's main components: 
Key Features
1. Spam Call Blocking:
        Use a database (e.g., a public spam-call list or user-defined list).
        Match incoming calls against the database and block if necessary.
2. Manual Blocking:
        Provide an interface for users to manually add numbers to the block list.
3. Persistent Storage:
        Store user preferences and the blocked numbers using a local database (e.g., Room database).
Implementation
Below is an example of how you might structure the app:

1. Android Manifest
    Update the AndroidManifest.xml to request permissions and declare services.
2. Call Screening Service
    Create a service to handle call screening.
3. User Interface
   Create an activity for the user to view and manage blocked numbers.
4. Storage for Blocked Numbers
  Use Room Database to store blocked numbers.
    Entity:
    DAO:
    Database:
5. Add/Remove Blocked Numbers
   Use the UI to interact with the database.
Notes
    1. Permissions: Starting with Android 10, call screening services have strict limitations. Ensure the app uses the required permissions and is set as the default call screening app.
    2. Database Management: Use Room or another database for scalability.
    3. UI: Design the app with user-friendly layouts and error handling.
    4. Testing: Test on various Android versions and devices.
==========================================================================
To run and test the spam call blocker app, you need to follow these steps:
===========================================================================
1. Set Up Your Development Environment
    Prerequisites:
    Android Studio: Download and install Android Studio.
    JDK: Ensure Java Development Kit (JDK) is installed.
    Android Device/Emulator: A physical Android device or emulator running Android 9 (API 28) or above (preferably Android 10+).

2. Build the App
    Steps:
    Create a New Android Studio Project:
        Open Android Studio and create a new project.
        Select Empty Activity as the template.
        Add Dependencies: Add the following dependencies to your build.gradle (app-level) file for Room database support:
        gradle
        Copy code
        implementation "androidx.room:room-runtime:2.5.2"
        annotationProcessor "androidx.room:room-compiler:2.5.2"
        implementation "androidx.appcompat:appcompat:1.7.0"
        Sync the project to download dependencies.

Implement the Code:

Add the CallBlockingService, MainActivity, BlockedNumber entity, BlockedNumberDao, and AppDatabase files to your project.
Use XML files for layouts (activity_main.xml, activity_block_number.xml) to design the UI.
Update AndroidManifest.xml with permissions and service declarations.
Compile the App: Build the project by clicking Build > Build APK or Run in Android Studio.

3. Set the App as Default Call Screening App
Install the app on a physical device or emulator.
Open Settings > Apps > Default Apps > Call Screening App (may vary by manufacturer).
Select your app as the default call screening app.

4. Testing
Option 1: Physical Device
    Install the APK on your Android phone using USB Debugging or ADB:
    bash# adb install path/to/app.apk
Test incoming calls:
    Use another phone to call the test device.
    If the incoming number matches a spam number (hardcoded for testing or stored in the database), the app should reject it.
    Manually add numbers to the block list and test if they are correctly rejected.
Option 2: Emulator
    Use an emulator that supports incoming calls:
    In Android Studio, create a Virtual Device with an API level that supports telephony features.
    Use the Emulator Control in Android Studio to simulate an incoming call:
    Open Tools > Device Manager > Emulator > Phone Controls.
    Set a phone number and click "Call."
5. Debugging and Logs
    Use Logcat in Android Studio to monitor app behavior and debug issues.
    Add logging statements in your code:
    java
    Log.d("CallBlockingService", "Incoming call from: " + phoneNumber);
    Filter logs for tags like CallBlockingService to view your custom logs.
6. Testing Blocked Numbers
   Hardcoded Numbers: Add test numbers to the isSpamNumber() function for initial testing:
          java
          private boolean isSpamNumber(String phoneNumber) {
              return phoneNumber.equals("+1234567890"); // Example spam number
          }
   Database Integration:
    Add numbers to the database using the app UI.
    Verify the app rejects calls from those numbers.
   Manual Block Testing:
    Use the UI to manually add a phone number to the block list.
    Test calls from that number to confirm they are rejected.
7. Testing Scenarios
    Scenario 1: Blocking Known Spam Numbers
    Add a known spam number to your hardcoded list or database.
    Call the device from that number and confirm it is rejected without notification.
    Scenario 2: Adding Numbers via UI
    Manually input numbers through the app’s UI.
    Check that calls from these numbers are rejected.
    Scenario 3: Allowing Non-Spam Calls
    Test calls from numbers not in the block list.
    Confirm they are allowed to ring as normal.
    Scenario 4: Edge Cases
    Test international numbers, withheld numbers, and invalid formats.
8. Deployment
    Once tested, the app can be published for personal use or shared with others by generating a signed APK:

In Android Studio, go to Build > Generate Signed APK.
Create a keystore or use an existing one.
Follow the steps to generate the APK for distribution.

1. Open the Project in Android Studio
Ensure the project compiles and runs successfully in debug mode before proceeding.
2. Configure Signing Key
    If you don’t already have a signing key, you need to create one:
    Go to Build > Generate Signed Bundle/APK.
    In the dialog that appears:
    Choose APK and click Next.
    If you don’t have an existing key:
    Click Create new....
    Fill out the required fields:
    Key store path: Browse to a location on your computer to save the key file.
    Key store password: Enter a password (you’ll need this later).
    Key alias: A name for your key.
    Key password: Enter a password for the key.
    Validity (years): Recommended: 25+ years.
    Fill in your details like name, organization, and country.
    Click OK to save the key.
    Important: Keep the keystore file and passwords secure. You’ll need them for updates.

3. Generate the Signed APK
      Go to Build > Generate Signed Bundle/APK again.
      Select APK and click Next.
      Fill in the details:
      Key store path: Locate the .jks file you created.
      Key store password and Key password: Enter the passwords.
      Key alias: Select the alias you created.
      Choose the Build Variants:
      Select Release for a production-ready APK.
      Click Finish.

4. Locate the Signed APK
      Once the build process completes, Android Studio will show the location of the signed APK.
      Typically, it’s in the app/release/ folder of your project directory.

5. Verify the Signed APK
      To confirm the APK is signed:

Open a terminal and run the following command using the Java Keytool:
bash# jarsigner -verify -verbose -certs path-to-apk/app-release.apk

6. Test the Signed APK
Transfer the signed APK to your Android device and install it.
Test the app to ensure it behaves as expected.

7. (Optional) Optimize with ZIP Alignment
Use the zipalign tool to optimize the APK:

bash# zipalign -v 4 path-to-apk/app-release.apk path-to-apk/app-release-aligned.apk

8. Distribute the APK
Now that you have the signed APK, you can:

Share it directly with others for installation.
Upload it to the Play Store for public distribution.

If you want to generate a signed APK online for free, you can use free online CI/CD tools or APK generation services. 
While there aren't many direct "upload code and get signed APK" websites, you can set up free services like GitHub Actions, Bitrise, or Appetize.io for this purpose. 






