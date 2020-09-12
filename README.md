# PennAppHack name of the app to come
Created by Beatrice Casey and Jack Otto
9/11/20-9/13/20 for the PennApps Hackathon

We created the app within android studio to make it easier for college students to find recipes that they can actually make given the equipment at their disposal.

When first entering the app, a new user is asked to create a new account and sign up. After they have submitted their username/password, the app directs the user to a page
  of preferences. This is when the new user can specify the restraints on their given cooking environment; first they specify the maximum amount of time they have to 
  cook (in minutes), next they will specify the price range that they are willing/able to cook in ranging from low to high, and lastly we take into account the users physical
  cooking environment, for now we only have two options as that is the normal life for college students, they choose between a dorm room set-up and a full kitchen.

To store the preferences and login credentials for the new user, we have been using Back4App to store all the created data and hold them in specific charts so that we may access
  them later on for giving the user the right feed.

The first page that opens once the app finally gets passed the set-up phase is the user's feed page. This page contains an endless (limited by the amount of posts to the app already) stream of recipes that fit all of the preferences for the user. So for example had the user entered {60 minutes, $$, Full-Kitchen} their feed would show only food than can be made in 60 minutes or less, is within a moderate or cheap price range and can be made using a full-kitchen. 

Should the user like one of the recipes they see on their feed, all they have to do is click on that recipe and it will bring them to a full screen view of the recipe with the 
  necessary ingredients and the steps to make the recipe, and a like button that depending on if you want to save the dish to make it can be clicked on to store in your personal
  page. On that page it will also display an overall rating of the dish based on the comments and individual rating of those who have tried to make the recipe, with the comments 
  of the dish listed below the instructions to make the dish. Lastly the expanded window also has a place for you to create your own comment on the dish and give it a rating of   its own.

Now of course if you are going to be viewing other peoples creations, shouldn't they be able to view yours! Our second tab on the main screen allows the user to create their own
  dish including the recipe's name, the ingredients and steps just as if you were reading then as stated above a picture of the dish and then the time range and price range it     took to make this dish. The app will automatically assume that given your preference on access to a kitchen, was what you had to cook the dish. 
  Finally as mentioned above, when the user creates their own dish and posts it to the app for everyone it is automatically added to their personal page under their creations, separate from your liked dishes. 

And what if you are really craving something specific and just need the recipe to make it? We of course included a search page! To use the page all a user must do is enter the
  name of the recipe you are looking for and hit enter (for now we do not account for spelling mistakes or partial recipe titles, it must match exactly). Once the search is complete
  the page will come up with a list of recipes matching recipe title you entered. From there the page acts as the original home screen, keep scrolling for as long as you want
  and then once you come upon a recipe you like, give it a click and go to work!

Finally we have the users personal page. On this page we display the users profile name and then two separate threads. One being their personal creations and the other being their
  saved recipes. This way they can always go back to anything they have made or that they like without having to go and search for their own creations. Lastly the personal page
  also contains the setting icon which when clicked on will take the user to the settings page.
Within the settings page the user has the ability to change almost everything about them on the app. They can change their username, password and all their preferences. We made
  this possible because everyone always has fluctuating situations and this allows for the most versatile user experience. Of course if the user changes/updates their preferences
  the application will change and adapt to their new preferences, completely changing their home thread of recipes but never changing their personal page. Lastly, the user has the 
  option to log out of the application with the click of a button.

We hope you like using our app as much as we enjoyed creating it!
