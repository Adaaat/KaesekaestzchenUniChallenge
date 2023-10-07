import java.io.IOException
import java.lang.Exception

val feld = listOf(
    mutableListOf(0,-1,-1,0,-1,-1,0),
    mutableListOf(-1,0,-1,0,-1,0,-1),
    mutableListOf(-1,-1,0,0,0,-1,-1),
    mutableListOf(0,0,0,-1,0,0,0),
    mutableListOf(-1,-1,0,0,0,-1,-1),
    mutableListOf(-1,0,-1,0,-1,0,-1),
    mutableListOf(0,-1,-1,0,-1,-1,0)
)
fun c (y: Int,x: Int): String {
    val spieler = feld[y][x]
    return when(spieler) {
        1 -> "O"
        2 -> "X"
        else -> " "
    }
}

fun draw(): Boolean{
    for(wert in 0..6){
        if(0 in feld[wert]){
            return false
        }
    }
    return true
}



fun winRow (y: Int, spieler: Int): Boolean{
    if(y != 3) {
        for (x in 0..6) {
            if (!(feld[y][x] == spieler || feld[y][x] == -1)) {
                return false
            }
        }
    } else {
        for (x in 0..2) {
            if (!(feld[y][x] == spieler || feld[y][x] == -1)) {
                break
            }
        }
        for (x in 4..6) {
            if (!(feld[y][x] == spieler || feld[y][x] == -1)) {
                return false
            }
        }
        return true
    }
    return true
}

fun winColumn(x: Int, spieler: Int): Boolean{
    if(x != 3) {
        for (y in 0..6) {
            if (!(feld[y][x] == spieler || feld[y][x] == -1)) {
                return false
            }
        }
    } else {
        for (y in 0..2) {
            if (!(feld[y][x] == spieler || feld[y][x] == -1)) {
                break
            }
        }
        for (y in 4..6) {
            if (!(feld[y][x] == spieler || feld[y][x] == -1)) {
                return false
            }
        }
        return true
    }
    return true
}



println("""
        Mühle:
              0    1   2   3   4   5   6
                   
         0    [ ]_________[ ]_________[ ]  
         1    |   [ ]_____[ ]_____[ ]   |                 
         2    |   |   [ ]_[ ]_[ ]   |   |
              |   |   |         |   |   |
         3    [ ]_[ ]_[ ]     [ ]_[ ]_[ ]   
              |   |   |         |   |   |
         4    |   |   [ ]_[ ]_[ ]   |   |
         5    |   [ ]_____[ ]_____[ ]   |               
         6    [ ]_________[ ]_________[ ]                      
        
        Spiel beenden mit: exit
    """.trimIndent()
)


var aktuellerSpieler = 1
while (true) {


    println("Spieler ${aktuellerSpieler} ist am Zug! (Eingabe in der Form: y,x)")
    var eingabe = readLine()!!
    if(eingabe == "exit"){
        break
    }

    val pos = eingabe.split(",")
    var y: Int
    var x: Int


   try{
       y = pos[0].toInt()
       x = pos[1].toInt()

       if( feld[y][x] == -1){
           throw Exception("Bei den Eingabe handelt es sich nicht um ein gültiges Feld!! Wähle erneut \n")
       }

   } catch(ofe: Exception ){
       println("Bei den Eingabe handelt es sich nicht um ein gültiges Feld!! Wähle erneut \n")
       continue
   }


    if (feld[y][x] == 0){
        feld[y][x] = aktuellerSpieler;

    } else {
        println("Das Feld ist bereits belegt! Wähle ein freies Feld! \n")
        continue
    }


    println("""
   
         Mühle:
              0    1   2   3   4   5   6
                   
         0    [${c(0,0)}]_________[${c(0,3)}]_________[${c(0,6)}]  
         1    |   [${c(1,1)}]_____[${c(1,3)}]_____[${c(1,5)}]   |                 
         2    |   |   [${c(2,2)}]_[${c(2,3)}]_[${c(2,4)}]   |   |
              |   |   |         |   |   |
         3    [${c(3,0)}]_[${c(3,1)}]_[${c(3,2)}]     [${c(3,4)}]_[${c(3,5)}]_[${c(3,6)}]   
              |   |   |         |   |   |
         4    |   |   [${c(4,2)}]_[${c(4,3)}]_[${c(4,4)}]   |   |
         5    |   [${c(5,1)}]_____[${c(5,3)}]_____[${c(5,5)}]   |               
         6    [${c(6,0)}]_________[${c(6,3)}]_________[${c(6,6)}]                      
        
    """.trimIndent()
    )
    if (winColumn(x, aktuellerSpieler) || winRow(y, aktuellerSpieler)){
        println("Spieler $aktuellerSpieler hat gewonnen!!")

        break
    }
    if(draw()){
        println("Das Spiel endet unentschieden, da alle Felder belegt sind!")
        break
    }
    if (aktuellerSpieler == 1){
        aktuellerSpieler = 2
    } else {
        aktuellerSpieler = 1
    }

}
