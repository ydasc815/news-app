# News Today App
### #30DaysOfKotlin
#### Description
This is a simple News app which fetches news data from an API and displays the news information in a form of list. Each of the news articles can now be viewed by clicking on the desired the news item and the news opens up in a new window (frame) inside the app.

#### Brief Overview & Working of the app
Two Activities and Nine Fragments have been used to build up the whole app. News items are stored in a Vertical RecyclerView and each of the news items open up in a new fragment implementing a full screen WebView. The news data is fetched from an API (host : https://newsapi.org/) using Volley library. Refer the following App screens for a better understanding of its structure :
![alt text](https://github.com/ydasc815/news-app/blob/master/app/src/main/res/drawable/app_screens.png?raw=true)

All the categories of news are binded by a Navigation view (of Drawer Layout) which swipes right from left. In the main fragment (top headlines), a Pie Chart has also been implemented to display live COVID-19 Statistics for India along with some numbers denoting these stats. Finally, a drop down menu on the AppBar displays an About App screen.

#### Concepts & Libraries implemented
- Android Navigation Components, for navigating between between screens. 
- RecyclerView, for storing news data in a list efficiently.
- Volley library, for fetching news data from a news API (host : https://newsapi.org/).
- MPAndroidChart library, for displaying Pie Chart data.
- WebView for loading news URL.
