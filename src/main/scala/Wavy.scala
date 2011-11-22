import twitter4j._

object Wavy extends OAuth {
  val twitter = new TwitterFactory().getInstance

  def main(args:Array[String]) = {
    tryAuth
    def loop {
      val in = readLine
      in match {
	case "q" => println("Quit")
	case "public" => public; loop
	case "friend" => friend; loop
	case "update" => update; loop
	case _ => loop
      }
    }
    loop
  }

  def public = {
    println("Public Timeline")
    val public = twitter.getPublicTimeline
    for(i<- (0 until public.size).reverse) {
      println(public.get(i).getUser().getScreenName() + ": " +
	      public.get(i).getText())
    }
  }

  def friend = {
    println("Friends Timeline")
    val friend = twitter.getHomeTimeline
    for(i<- (0 until friend.size).reverse) {
      println(friend.get(i).getUser().getScreenName() + ": " +
	      friend.get(i).getText())
    }
  }

  def update = {
    println("What's happening?")
    print("☞ ")
    val status = readLine
    println("update this?[y/n]: "+status)
    val yn = readLine
    yn match { 
      case "y" => twitter.updateStatus(status); println("Successfully updated")
      case _ => println("not updated")
    }
  }
}
