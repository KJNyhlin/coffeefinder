package com.example.coffeefinder


class CoffeePlace(
    var name: String,
                  //var image: Drawable,
    var location: String,
    var latitude: String,
    var longitude: String,
    var address: String,
    var comment: String) {

    constructor() : this("", "", "", "", "", "")
}