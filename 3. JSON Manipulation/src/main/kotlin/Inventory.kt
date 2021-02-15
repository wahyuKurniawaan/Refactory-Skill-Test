data class Inventory(
    val inventory_id : Int,
    val name : String,
    val type : String,
    val tags : List<String>,
    val purchased_at : Long,
    val placement : Inventory.Place
) {
    data class Place(
        val room_id : Int,
        val name : String
    )
}