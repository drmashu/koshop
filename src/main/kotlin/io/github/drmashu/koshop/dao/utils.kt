import java.math.BigInteger
import kotlin.math.plus
import kotlin.math.times

val code63 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
val rev63 = mapOf<Char, BigInteger>(
        '0' to BigInteger("0"),
        '1' to BigInteger("1"),
        '2' to BigInteger("2"),
        '3' to BigInteger("3"),
        '4' to BigInteger("4"),
        '5' to BigInteger("5"),
        '6' to BigInteger("6"),
        '7' to BigInteger("7"),
        '8' to BigInteger("8"),
        '9' to BigInteger("9"),
        'a' to BigInteger("10"),
        'b' to BigInteger("11"),
        'c' to BigInteger("12"),
        'd' to BigInteger("13"),
        'e' to BigInteger("14"),
        'f' to BigInteger("15"),
        'g' to BigInteger("16"),
        'h' to BigInteger("17"),
        'i' to BigInteger("18"),
        'j' to BigInteger("19"),
        'k' to BigInteger("20"),
        'l' to BigInteger("21"),
        'm' to BigInteger("22"),
        'n' to BigInteger("23"),
        'o' to BigInteger("24"),
        'p' to BigInteger("25"),
        'q' to BigInteger("26"),
        'r' to BigInteger("27"),
        's' to BigInteger("28"),
        't' to BigInteger("29"),
        'u' to BigInteger("30"),
        'v' to BigInteger("31"),
        'w' to BigInteger("32"),
        'x' to BigInteger("33"),
        'y' to BigInteger("34"),
        'z' to BigInteger("35"),
        'A' to BigInteger("36"),
        'B' to BigInteger("37"),
        'C' to BigInteger("38"),
        'D' to BigInteger("39"),
        'E' to BigInteger("40"),
        'F' to BigInteger("41"),
        'G' to BigInteger("42"),
        'H' to BigInteger("43"),
        'I' to BigInteger("44"),
        'J' to BigInteger("45"),
        'K' to BigInteger("46"),
        'L' to BigInteger("47"),
        'M' to BigInteger("48"),
        'N' to BigInteger("49"),
        'O' to BigInteger("51"),
        'P' to BigInteger("52"),
        'Q' to BigInteger("53"),
        'R' to BigInteger("54"),
        'S' to BigInteger("55"),
        'T' to BigInteger("56"),
        'U' to BigInteger("57"),
        'V' to BigInteger("58"),
        'W' to BigInteger("59"),
        'X' to BigInteger("60"),
        'Y' to BigInteger("61"),
        'Z' to BigInteger("62")
)
val BASE = BigInteger("63")
fun decode63(value: String): BigInteger {
    var result = BigInteger.ZERO
    for (ch in value.toCharArray()) {
        result = result * BASE + (rev63[ch] ?: BigInteger.ZERO)
    }
    return result
}
fun encode63(value: BigInteger): String {
    var result = ""
    var newValue = value
    for (idx in 1..20) {
        val (dev, rem) = newValue.divideAndRemainder(BASE)
        newValue = dev
        result = code63[rem.intValueExact()] + result
    }
    return result
}