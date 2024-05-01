import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
data class Notification(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    val userId: Long,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    var read: Boolean = false
) {
    constructor() : this(0, 0, "", LocalDateTime.now(), false)
}