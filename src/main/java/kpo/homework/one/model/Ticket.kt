package kpo.homework.one.model

// Класс Ticket.
class Ticket() {
    var sessionId : Int = 0
    var row: Int = 0
    var number: Int = 0
    var status: TicketStatus = TicketStatus.Free
    constructor(sessionId: Int, row: Int, number: Int, status: TicketStatus) : this() {
        this.sessionId = sessionId
        this.row = row
        this.number = number
        this.status = status
    }

    override fun toString(): String {
        return "SessionId='$sessionId'; Row='$row'; Number='$number'; Status='$status'"
    }
}