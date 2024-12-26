package kpo.homework.one.model

// Класс Movie.
class Movie() {
    var id: Int = 0
    var title: String = ""
    var runtime: Int = 0
    constructor(id: Int, title: String, runtime: Int) : this() {
        this.id = id
        this.title = title
        this.runtime = runtime
    }

    override fun toString(): String {
        return "Id='$id'; Title='$title'; Runtime='$runtime minutes'"
    }
}