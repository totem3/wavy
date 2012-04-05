import twitter4j._

object Wavy extends OAuth {
  val twitter = new TwitterFactory().getInstance
  val twitterStream = new TwitterStreamFactory().getInstance

  def main(args:Array[String]) = {
    tryAuth()
    stream()
    def loop {
      val in = readLine
      in match {
	case "q" => quit
	case "public" => public; loop
	case "friend" => friend; loop
	case n => update(n); loop
      }
    }
    loop
  }

  def quit():Unit = {
    println("Quit")
    twitterStream.shutdown
  }

  def public():Unit = {
    println("Public Timeline")
    val public = twitter.getPublicTimeline
    for(i<- (0 until public.size).reverse) {
      println(public.get(i).getUser().getScreenName() + ": " +
	      public.get(i).getText())
    }
  }

  def friend():Unit = {
    println("Friends Timeline")
    val friend = twitter.getHomeTimeline
    for(i<- (0 until friend.size).reverse) {
      println(friend.get(i).getUser().getScreenName() + ": " +
	      friend.get(i).getText())
    }
  }

  def update(status:String):Unit = {
    println("update this?[y/N]: "+status)
    val yn = readLine
    yn match { 
      case "y" => twitter.updateStatus(status); println("Successfully updated")
      case _ => println("not updated")
    }
  }

  def stream():Unit = {
    twitterStream.setOAuthAccessToken(accessToken)
    val listener = new UserStreamListener() {
      def onStatus(status:Status) = {
        println("@" + status.getUser().getScreenName() +
                " - " + status.getText())
      }
  
      def onDeletionNotice(directMessageId:Long, userId:Long) = {}
      def onDeletionNotice(statusDeletionNotice:StatusDeletionNotice) = {
        println("Got a status deletion notice id:" + 
                   statusDeletionNotice.getStatusId())
      }
  
      def onTrackLimitationNotice(numberOfLimitedStatuses:Int) = {
        println("Got track limitation notice:" + numberOfLimitedStatuses)
      }
  
      def onScrubGeo(userId:Long, upToStatusId:Long) = {
        println("Got scrub_geo event userId:" + userId + 
                " upToStatusId:" + upToStatusId)
      }
  
      def onException(ex:Exception) = {
        ex.printStackTrace()
      }
      def onFriendList(friendIds:Array[Long]) = {}
      def onFavorite(source:User, target:User, favoritedStatus:Status) = {}
      def onUnfavorite(source:User, target:User, unfavoritedStatus:Status) = {}
      def onFollow(source:User, followedUser:User) = {}
      def onRetweet(source:User, target:User, retweetedStatus:Status) = {}
      def onDirectMessage(directMessage:DirectMessage) = {}
      def onUserListMemberAddition(addedMember:User, listOwner:User, list:UserList) = {}
      def onUserListMemberDeletion(deletedMember:User, listOwner:User, list:UserList) = {}
      def onUserListSubscription(subscriber:User, listOwner:User, list:UserList) = {}
      def onUserListUnsubscription(subscriber:User, listOwner:User, list:UserList) = {}
      def onUserListCreation(listOwner:User, list:UserList) = {}
      def onUserListUpdate(listOwner:User, list:UserList) = {}
      def onUserListDeletion(listOwner:User, list:UserList) = {}
      def onUserProfileUpdate(updatedUser:User) = {}
      def onBlock(source:User, blockedUser:User) = {}
      def onUnblock(source:User, unblockedUser:User) = {}
    };

    twitterStream.addListener(listener)
    twitterStream.user
  }

  def retweet():Unit = {
    
  }
}
