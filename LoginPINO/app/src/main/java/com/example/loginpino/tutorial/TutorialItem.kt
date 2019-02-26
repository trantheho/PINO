package com.example.loginpino.tutorial

class TutorialItem {

    var title: String? = null
    var sub: String? = null
    var image: Int = 0
    var link: String? = null

    constructor(title: String, sub: String, image: Int) {
        this.title = title
        this.sub = sub
        this.image = image
    }

    constructor(title: String, sub: String, image: Int, link: String) {
        this.title = title
        this.sub = sub
        this.image = image
        this.link = link
    }
}