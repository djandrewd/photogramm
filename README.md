#Photogramm

Your final task is to implement social network for sharing images (for example like Instagramm).
Main use cases that must be implemented are :

1. User can register and login himself. Password must not be stored as plain text. It is not 
allowed to register two or more users with same nickname. 
2. (Optional) Optionally after user has registered you can send him email for confirmation and 
not allow to login user until he confirm an email.
3. User must be able to logout after he made login.
4. User can upload any kind and any amount of images to the system. Images can be very huge, so 
your task first to resize them in order to spare space and network resources.
5. User must specify name of image. Image can contains any number of special links symbols like 
user references or hash tags. Any user must be able to open account or images with same tags on 
the click.
6. User must be able to comment an image. Optionally he can edit and remove his comment.
7. User can like or dislike any image in the system.
8. User must see images sorted by submit time, latest first.
9. User can open his account, be able to check his subscribers and subscriptions.
10. User can search for any user in the system by nickname.
11. User can open any account of other user and can subscribe or unsubscribe to that user.
12. User must see history of actions, that his subscribers did on his items: who liked or comment
 his image. Any history item in the list must contain image thumbnail.
13. (Optionally) User can check list of recommended users to subscribe.
14. (Optionally) User can send message to any of his subscribers. 

In addition you must provide external API for any component presented in the system
in order to be able to access to your application via third party vendors. 
Each of external service must have at least basic security. 

Each component in the system must be separate maven module, can depend on other modules
but better keep them as independent as possible.
  
## Application big-picture

Application big picture you can find in root of this project under the name of 'Photogramm APP.jpg'.


###Users system 

####Authorization / Authentification component

1. Component should allow user to login, 
logout for user and provide information about this right and granted authorities.
2. Component must provide possibility to register new user in the system.
3. Component must also provide possibility to subscribe and unsubscribe to any user. 
4. Component also must provide possibility to get general information about user.

##### External API

##### User resource (/api/users)

Resource for general CRUD operation for users in the system.

###### Create user(/create/, POST)

PARAMETERS:

1. nickname - REQUIRED, string - user nickname (must be unique).
2. email - REQUIRED, string - user email (can be unique)
3. password - REQUIRED, string.
4. name - REQUIRED, string - user name (must not be null)
5. language - OPTIONAL, string - preferred user language.

RESPONSE:

200 - OK, any other HTTP status codes considers as errors.

###### Get user information (/get/, GET)

PARAMETERS: 

1. nickname - REQUIRED - nickname of the user.

RESPONSE:

```json
{
   "nickname": "cooler",
   "email": "cool@ad.com",
   "name": "Cooler"
}
``` 
###### Get user information (/find/, GET)

Find information about partially provided nickname.

1. nickname - REQUIRED - partitially provided nickname of the user.

###### Remove user information (/remove/, POST)

Remove information about user in the system by provided nickname.

1. nickname - REQUIRED - nickname of the user.

RESPONSE:
 200 - OK

##### Subscribers resource (/api/subscriptions)

###### Subscribe (/subscribe, POST)

PARAMETERS

1. nickname - REQUIRED, string - current user nickname.
2. subscription - REQUIRED, string  - user nickname to subscribe on.

RESPONSE:
 200 - OK

###### Unsubscribe (/unsubscribe, POST)

PARAMETERS

1. nickname - REQUIRED, string - current user nickname.
2. subscription - REQUIRED, string  - user nickname to unsubscribe from.

RESPONSE:
 200 - OK

###### Subscriptions (/subscriptions, GET)

List all nicknames of users on which provided user is subscribed.

PARAMETERS

1. nickname - REQUIRED, string - current user nickname.

RESPONSE:

```json
["test-user1", "test-user2"]
```
###### Subscribers (/subscribers, GET)

List all nicknames of users which subscribed on provided user.

PARAMETERS

1. nickname - REQUIRED, string - current user nickname.

RESPONSE:

```json
["test-user1", "test-user2"]
```

### Photo managing system

This component is responsible for CRUD photo operations. Files won't be stored in database, 
they will be delivered via static files serving system.
Use AWS S3 as static files storage. 


### Photo social component

This component is responsible for social activities for pictures: 
likes, comments and integration with other social media service.


### Static files serving system

Static files component should be responsible for delivering static files to end user. 
Use AWS S3 for managing such kind of images and static files.



 