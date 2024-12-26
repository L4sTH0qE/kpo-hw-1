package kpo.homework.one.model

// Класс Session.
class Session() {
    var id: Int = 0
    var filmId : Int = 0
    var startTime: String = ""
    constructor(id: Int, filmId: Int, startTime: String) : this() {
        this.id = id
        this.filmId = filmId
        this.startTime = startTime
    }

    override fun toString(): String {
        return "Id='$id'; FilmId='$filmId'; StartTime='$startTime'"
    }
}