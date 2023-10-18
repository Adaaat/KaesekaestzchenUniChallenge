import java.lang.Exception

var counter1 = 0
var counter2 = 0

val feld = listOf(
    mutableListOf(0,0,0,0,0,0,0,0,0),
    mutableListOf(0,0,0,0,0,0,0,0,0),
    mutableListOf(0,0,0,0,0,0,0,0,0),
    mutableListOf(-1,0,-1,0,-1,0,-1,0,-1)
)

val punkte = listOf(
    mutableListOf(0,0,0,0),
    mutableListOf(0,0,0,0),
    mutableListOf(0,0,0,0)
)

// ist für die Darstellung der waagerechten Kanten zuständig
fun cRow (y: Int,x: Int): String {
    val spieler = feld[y][x]
    return when(spieler) {
        1 -> "-----"
        2 -> "====="
        else -> "     "
    }
}

// ist für die Darstellung der senkrechten Kanten zuständig
fun cColumn (y: Int,x: Int): String {
    val spieler = feld[y][x]
    return when(spieler) {
        1 -> "|"
        2 -> "\u2016"
        else -> " "
    }
}

// schaut, ob das Spiel vorbei ist bzw, ob alle felder bestzt sind
fun end(): Boolean {
    for(wert in 0..3){
        if(0 in feld[wert]){
            return false
        }
    }
    return true
}

// schaut ob ein Kasten vollständig ist und wem diesr zuzuschreiben ist
fun kasten(y: Int,x: Int, spieler: Int): String{
    var chX = -1

    if(x != 8 && y != 3){
        if (feld[y][x+1] != 0 && feld[y][x+2] != 0 && feld[y+1][x+1] != 0){  // überprüft das rechte Kästchen
            if (x == 2){
                chX = 1
            } else if (x ==4){
                chX = 2
            } else if (x == 6){
                chX = 3
            }
            else if (x == 0){
                chX = 0
            }

        }
    }
    if (chX == -1) {
        return " "
    }
    if (punkte[y][chX] == 0) {
        punkte[y][chX]= spieler
        if (spieler == 1){
            counter1++
        } else {
            counter2++
        }
    }

    return when(punkte[y][chX]) {
        1 -> "O"
        2 -> "X"
        else -> " "
    }
}


// schaut ob mit dem letzten Zug ein Kasten vervollständigt wurde und gibt einen boolischen Wert zurück
fun point(y: Int, x: Int): Boolean{
    if (x % 2 == 0) {
        if(x != 8){
            if (feld[y][x+1] != 0 && feld[y][x+2] != 0 && feld[y+1][x+1] != 0){  // überprüft das rechte Kästchen
                return true
            }
        }
        if(x!=0){
            if (feld[y][x-1] != 0 && feld[y][x-2] != 0 && feld[y+1][x-1] != 0){  // überprüft das linke Kästchen
                return true
            }
        }

    } else {
        if (y != 3){
            if (feld[y][x+1] != 0 && feld[y][x-1] != 0 && feld[y+1][x] != 0){  // überprüft das untere Kästchen
                return true
            }
        }
        if(y != 0){
            if (feld[y-1][x+1] != 0 && feld[y-1][x-1] != 0 && feld[y-1][x] != 0){  // überprüft das obere Kästchen
                return true
            }
        }
    }
    return false
}


println("""
    
        0  1  2  3  4  5  6  7  8
    A   +     +     +     +     +
                
    B   +     +     +     +     +
         
    C   +     +     +     +     +
         
    D   +     +     +     +     +
   
    Spiel beenden mit: exit
    
    """.trimIndent()
)

var aktuellerSpieler = 1
while (true){
    println("Spieler $aktuellerSpieler ist am Zug! (Eingabe in der Form: Buchstabe,Zahl)")
    var eingabe = readLine()!!
    if(eingabe == "exit"){
        break
    }

    //easter egg
    if (eingabe == "ich gewinne"){
        println("Spieler${aktuellerSpieler} gewinnt")
        break
    }

    val pos = eingabe.split(",").toMutableList()
    var y: Int
    var x: Int
    pos[0] = pos[0].uppercase()


    if(pos[0] == "A"){
        y=0
    } else if (pos[0] == "B"){
        y=1
    } else if (pos[0] == "C"){
        y=2
    } else if (pos[0] == "D"){
        y=3
    } else {
        println("Bei den Eingabe handelt es sich nicht um ein gültiges Feld!! Wähle erneut \n")
        continue
    }

    // catched alle ungültigen Eingaben
    try{
        x = pos[1].toInt()
        if(feld[y][x] == -1){
            throw Exception("Bei den Eingabe handelt es sich nicht um ein gültiges Feld!! Wähle erneut \n")
        }

    } catch(e: Exception){
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
    0  1  2  3  4  5  6  7  8
A   +${cRow(0,1)}+${cRow(0,3)}+${cRow(0,5)}+${cRow(0,7)}+
    ${cColumn(0,0)}  ${kasten(0,0, aktuellerSpieler)}  ${cColumn(0,2)}  ${kasten(0,2, aktuellerSpieler)}  ${cColumn(0,4)}  ${kasten(0,4, aktuellerSpieler)}  ${cColumn(0,6)}  ${kasten(0,6, aktuellerSpieler)}  ${cColumn(0,8)}             Spieler1: $counter1     
B   +${cRow(1,1)}+${cRow(1,3)}+${cRow(1,5)}+${cRow(1,7)}+
    ${cColumn(1,0)}  ${kasten(1,0, aktuellerSpieler)}  ${cColumn(1,2)}  ${kasten(1,2, aktuellerSpieler)}  ${cColumn(1,4)}  ${kasten(1,4, aktuellerSpieler)}  ${cColumn(1,6)}  ${kasten(1,6, aktuellerSpieler)}  ${cColumn(1,8)}             Spieler2: $counter2 
C   +${cRow(2,1)}+${cRow(2, 3)}+${cRow(2, 5)}+${cRow(2, 7)}+
    ${cColumn(2,0)}  ${kasten(2,0, aktuellerSpieler)}  ${cColumn(2,2)}  ${kasten(2,2, aktuellerSpieler)}  ${cColumn(2,4)}  ${kasten(2,4, aktuellerSpieler)}  ${cColumn(2,6)}  ${kasten(2,6, aktuellerSpieler)}  ${cColumn(2,8)}                                       
D   +${cRow(3,1)}+${cRow(3,3)}+${cRow(3,5)}+${cRow(3,7)}+

    """.trimIndent())

    // schaut welcher Spieler gewonnen hat
    if (end()){
        if (counter1 != counter2){
            if (counter1 < counter2){
                println("Spieler 2 hat gewonnen! $counter2 zu $counter1")
            } else{
                println("Spieler 1 hat gewonnen! $counter1 zu $counter2")
            }
        } else {
            println("Es endet unentschieden!!")
        }

        break
    }

    if(point(y,x)){
        continue
    }

    if (aktuellerSpieler == 1){
        aktuellerSpieler = 2
    } else {
        aktuellerSpieler = 1
    }

}