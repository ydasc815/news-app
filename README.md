# News Today App
### #30DaysOfKotlin
#### Description
This is a simple News app which fetches news data from an API and displays the news information in a form of list. Each of the news articles can now be viewed by clicking on the desired the news item and the news opens up in a new window (frame) inside the app.

#### Brief Overview & Working of the app
As soon as the user opens up the app, he is presented with a splash screen lasting for 2.5 seconds on the screen after which he is redirected to the Main Activity page of the app. Here, by default the Main Activity inflates the Top Headlines Fragment into the View which displays two main things : COVID-19 India Statistics Pie Chart and some numbers denoting these stats followed by the top headlines of the day which are fitted inside a Recycler View. 
This app also included a Navigation View (also knows a Nav Drawer Layout) which accounts for various categories of News which includes, but is not limited to Health and Science. All other categories apart from the Top Headlines show the user only the News articles list framed into "match_parent" X "match_parent" (to root view) attributes of the recycler view.
