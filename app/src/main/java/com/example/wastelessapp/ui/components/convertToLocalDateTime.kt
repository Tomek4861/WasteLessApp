import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun convertToLocalDateTime(dateString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val localDate = LocalDate.parse(dateString, formatter)

    return LocalDateTime.of(localDate, LocalTime.MIDNIGHT)
}
