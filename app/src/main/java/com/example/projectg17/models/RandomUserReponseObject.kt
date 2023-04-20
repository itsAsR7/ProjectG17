package com.example.projectg17.models

// Response from API
data class ParkByStateReponseObject(
    val data:List<ParkData>
) {}

// Objects for results array
data class ParkData(
    var id:String,
    val fullName:String,
    val addresses:List<AddressObj>,
    val description: String,
    val url: String,
    val latitude:String,
    val longitude: String,
    val images: List<ImagesObj>
)
{}
// objects for the Name
data class ImagesObj(
    val url:String,
) {}

data class AddressObj(
    val line2:String,
    val city:String,
    val stateCode:String,
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

data class ParksObj(
    val name:String,
    val designation:String
) {}

data class Itinerary(
    val name: String,
    val address: String,
    val date: String,
    val notes: String,
    val id: String
) {}
