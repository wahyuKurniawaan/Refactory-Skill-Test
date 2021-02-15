
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun main() {

    print("Masukan Nama Kasir : ")
    val name = readLine()
    print("Masukan Nama Restoran : ")
    val restoName = readLine()

    val menu : ArrayList<String> = arrayListOf()
    val price : ArrayList<Int> = arrayListOf()
    inputMenu(menu, price)
    println()

    if (name != null && restoName != null) {
        header(name, restoName)
    }
    var total = 0
    for (i in 0 until menu.size) {
        val dec = DecimalFormat("#,###")
        println("\n" + textLayout(menu[i],
            "Rp.${dec.format(price[i]).toString().replace(',', '.')}",
            30,
            '.'))
        total += price[i]
        if (i==menu.size-1) {
            println("\n\n" + textLayout("Total",
                "Rp.${dec.format(total).toString().replace(',', '.')}",
                30,
                '.'))
        }
    }

}

fun inputMenu( menu: ArrayList<String>, price: ArrayList<Int>) {
    print("Masukan Menu : ")
    val food = readLine()
    if (food != null) {
        menu.add(food)
    }

    print("Masukan Harga : ")
    val priceFood = readLine()
    if (priceFood != null) {
        price.add(priceFood.toInt())
    }
    print("Apakah ingin masukan data lagi?(Y/N) ")
    val confirm = readLine()?.toLowerCase()
    if (confirm == "y" || confirm == "ya" || confirm == "" ) {
        inputMenu(menu, price)
    }
}

fun header(name: String, restoName:String){
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    if (restoName.length <= 30) println(alignCenter(restoName, 30))
    else {
        val longRestoName = restoName.chunked(30)
        longRestoName.forEach { println(alignCenter(it, 30)) }
    }
    println()
    println(textLayout("Tanggal : ", currentDate, 30, ' '))
    println()

    when {
        name.length <= 17 -> {
            println(textLayout("Nama Kasir : ", name, 30, ' '))
        }
        name.length <= 30 -> {
            println("Nama Kasir : \n ${alignCenter(name, 30)}")
        }
        else -> {
            val longName = name.chunked(30)
            println("Nama Kasir : ")
            longName.forEach { println(alignCenter(it, 30)) }
        }
    }
    println()

    for (line in 1..30) print("=")
    println()
}

fun textLayout(left: String, right: String, width: Int, blank_char: Char) : String {
    val countLeft = left.length
    val countRight = right.length
    var textLeft = ""
    var countBlank: Int
    if (countLeft > width - 15) {
        val textLeftSplit = left.split("")
        for (a in 0..14) {
            textLeft += textLeftSplit[a]
        }
        countBlank = 30 - textLeft.length
    } else {
        textLeft = left
        countBlank = 30 - textLeft.length
    }
    countBlank -= countRight
    var blank = ""
    for (space in 1..countBlank) {
        blank += blank_char
    }
    return "$textLeft$blank$right"
}

fun alignCenter(text: String, width: Int): String {
    val widthText = text.length
    val countBlank = (width - widthText) / 2
    var blank = ""

    for (i in 1..countBlank){
        blank += " "
    }

    return "$blank$text$blank"
}