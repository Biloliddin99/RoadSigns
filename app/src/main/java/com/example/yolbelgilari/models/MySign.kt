package com.example.yolbelgilari.models

import java.io.Serializable

class MySign:Serializable {
    var id:Int? = null
    var name:String? = null
    var about:String? = null
    var type:Int? = null
    var liked:Int? = null
    var photoPath:String? = null

    constructor(
        id: Int?,
        name: String?,
        about: String?,
        type: Int?,
        liked: Int?,
        photoPath: String?
    ) {
        this.id = id
        this.name = name
        this.about = about
        this.type = type
        this.liked = liked
        this.photoPath = photoPath
    }

    constructor(name: String?, about: String?, type: Int?, liked: Int?, photoPath: String?) {
        this.name = name
        this.about = about
        this.type = type
        this.liked = liked
        this.photoPath = photoPath
    }

    override fun toString(): String {
        return "MySign(id=$id, name=$name, about=$about, type=$type, liked=$liked, photoPath=$photoPath)"
    }


}