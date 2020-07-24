# CircularSwipeableCards
A list of cards arranged in such a way that they can be swiped left or right in circular loop fashion.

The app is built following the MVVM design pattern, and used Android Architecture components like, ViewModel and LiveData. There are no test cases written for this app, and so no TDD approach has been followed while developing the app.


**Installation instructions:**

For trying out this app, you just have to download the apk file located at "/apk file" folder inside this project, can be downloaded [here](https://github.com/Divya0319/CircularSwipeableCards/blob/master/apk%20file/app-debug.apk)
And after downloading it on your phone, simply install it by finding it in your phone from File Manager. The file name will be "app-debug" with extension as .apk.


**Deployment to PlayStore**

For deploying this project to PlayStore, a Google Play Developer Account is required, more info about it [here](https://play.google.com/apps/publish)
* Once you have created a Google Play Developer account, you will get access to your Google Play Console linked to your Google account.
* Then, you can create a new application from your Google Play Console, and follow the steps described [here](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwi4_on-uuDqAhVQ7nMBHU9sDYcQFjAHegQIDBAF&url=https%3A%2F%2Fsupport.google.com%2Fgoogleplay%2Fandroid-developer%2Fanswer%2F113469%3Fhl%3Den&usg=AOvVaw1TW8Da5QiQxxszBFT1qbdv)
* While deployment, you are given two options, deploy a signed apk of the app, or upload a .aab(Android App Bundle) file generated from Android Studio.
* You can choose either of them, but Google recommends uploading the .aab file, as it optimises the apk size considerably, so that user doesn't have to download large apks, and can download only parts of apk required depending on their device configuration.



**Working Steps:**

The app works in the following way:
* First, on launching the app from Phone's launcher, it makes a network request to a specified url to fetch the data.
* Then, it displays the received data in the app in the form of left-right swipable cards.
* While swiping left, or right, if the user reaches the end of the card list, the cards starts from the beginning on further swiping.
* In case, some error occurs while making network request in the beginning, it is shown that some error is occured while fetching data, and a retry option is given to  try to make the network request again.
* Their is a progress indicator shown at the bottom of the screen, showing how many more cards are available to check out.
* Also, at which number of card you are currently standing, is shown on each card.
