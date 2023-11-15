package com.example.mediconnectapk

class Upload {
    var name: String
    var imgUrl: String

    constructor() {
        name = ""
        imgUrl = ""
    }

    constructor(name: String, imgUrl: String) {
        if (name.trim { it <= ' ' } == "") {
            this.name = "No Description"
        }
        this.name = name
        this.imgUrl = imgUrl
    }
}