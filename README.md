Play Framework + MongoDB
========================

A Playframework Project with a MongoDB theme.

========================
There are some preparation work that needs to be done before you fire up this app.

1. Download and install Play (duh).
2. Download and install MongoDB. How to do this is beyond the scope of this project. 
But you'll want MongoDB running on your localhost using the default port of 27017. You can edit the hostname in 
`conf/accountService.properties`.

Once you do that, simply execute

``$ play run``

Then go to `http://localhost:9000/app/index.html` in your browser. 

You'll see a simple login page. You should create a user first. Then log in using the same username/password. 
Then the app will tell you what your lucky color is. You can now delete your test user and repeat the process.
Isn't that great?!

This PoC integrates AngularJS, MongoDB, and the Play framework. Note that because we're using AngularJS, we're not
using Play's templating language at all. We're just using it as an API server and static file server.
