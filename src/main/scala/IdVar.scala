import twitter4j._

object IdVar {
  def next(currentVar:Var):Var = {
    val current = currentVar.name
    val last:String = nextChar(current.last)
    val head:String = last match { 
      case "a" => nextChar(current.head)
      case _ => current.head.toString
    }
    Var(head + last)
  }

  def nextChar(c:Char):String = {
    c match { 
      case 'z' => "a"
      case _ => (c+1).toChar.toString
    }
  }
}

case class Var(name:String) {
  require(name.length==2)
}
