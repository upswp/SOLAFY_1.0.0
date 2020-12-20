import firebase from "firebase/app";

// Add the Firebase services that you want to use
import "firebase/auth";
import "firebase/database";

var firebaseConfig = {
  apiKey: "AIzaSyDjpkF6LK39WKjzmVbRVpGBYG_92dJ5xQQ",
  authDomain: "solafy-460e6.firebaseapp.com",
  databaseURL: "https://solafy-460e6-default-rtdb.firebaseio.com",
  projectId: "solafy-460e6",
  storageBucket: "solafy-460e6.appspot.com",
  messagingSenderId: "356259085686",
  appId: "1:356259085686:web:237d0fcdf9ded5446fce57",
  measurementId: "G-9V0GRD7SF0"
};
// Initialize Firebase
let firebaseApp = firebase.initializeApp(firebaseConfig);
let firebaseAuth = firebaseApp.auth();
let firebaseDb = firebaseApp.database();

export { firebaseAuth, firebaseDb };
