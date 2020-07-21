# CircularSwipeableCards
A list of cards arranged in such a way that they can be swiped left or right in circular loop fashion.

The app is built following the MVVM design pattern, and used Android Architecture components like, ViewModel and LiveData. There are no test cases written for this app, and so no TDD approach has been followed while developing the app.

**Installation instructions:**

For trying out this app, you just have to download the apk file located at "/apk file" folder inside this project, can be downloaded [here](https://github.com/Divya0319/CircularSwipeableCards/blob/master/apk%20file/app-debug.apk)
And after downloading it on your phone, simply install it by finding it in your phone from File Manager. The file name will be "app-debug" with extension as .apk.

**Working Steps:**
The app works in the following way:
1. First, on launching the app from Phone's launcher, it makes a network request to a specified url to fetch the data.
2. Then, it displays the received data in the app in the form of left-right swipable cards.
3. While swiping left, or right, if the user reaches the end of the card list, the cards begin from the beginning on further swiping.
4. In case, some error occurs while making network request in the beginning, it is shown that some error is occured while fetching data, and a retry option is given to  try to make the network request again.
5. Their is a progress indicator shown at the bottom of the screen, showing how many more cards are available to check out.
5. Also, at which number of card you are currently standing, is shown on each card.
