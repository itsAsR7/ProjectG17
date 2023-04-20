package com.example.projectg17.models

// Response from API
data class RandomUserReponseObject(
    val results:List<PersonData>
) {}

// Objects for results array
data class PersonData(
    val name:NameObj,
    val location: LocationObj,
    val picture: PictureObj
)
{}
// objects for the Name
data class NameObj(
    val first:String,
    val last:String
) {}

// Objects for the Location
data class LocationObj(
    val coordinates:CoordinatesObj
) {}
data class CoordinatesObj(
    val latitude:String,
    val longitude:String
) {}

// Objects for the picture
data class PictureObj(
    val thumbnail:String
) {}


