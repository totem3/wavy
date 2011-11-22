import twitter4j._
import twitter4j.auth._

import java.io.File
import java.io.PrintWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.awt.Desktop;
import java.net.URI;

trait OAuth {
  val twitter:Twitter
  val dir = new File(".").getAbsoluteFile().getParent()
  val fname = "config"
  val file = dir + "/" + fname

  var accessToken:AccessToken = null
  
  def tryAuth = {
    var at = loadAccessToken
    if(at.isEmpty) {
      auth
    } else {
      accessToken = at.get
      twitter.setOAuthAccessToken(accessToken)
    }
  }

  def auth = {
    println("auth")
    val requestToken = twitter.getOAuthRequestToken
    println("requestToken got")

    while(accessToken == null) {
      println("Grant access to your account:")
      browseUrl(new URI(requestToken.getAuthorizationURL()))
      print("Enter the PIN: ")
      val pin = readLine
      try {
	if(pin.length>0) {
	  accessToken = twitter.getOAuthAccessToken(requestToken,pin)
	} else {
	  accessToken = twitter.getOAuthAccessToken()
	}
      } catch {
	case te:TwitterException => {
	  println("Unable to get the access token.")
	  te.printStackTrace
	}
      }
    }
    storeAccessToken(accessToken)
  }

  //exepction
  def storeAccessToken(at:AccessToken) = {
    val writer = new PrintWriter(file)
    try { 
      writer.println("token="+at.getToken)
      writer.println("secret="+at.getTokenSecret)
    } finally {
      writer.close
    }
  }
  
  //exepction
  def loadAccessToken:Option[AccessToken] = {
    if (new File(file).exists) {
      val reader = new BufferedReader(new InputStreamReader(
        new FileInputStream(new File(file))))
      var line:String = null
      var config = Map("" -> "")
      while({line=reader.readLine; line!=null}) {
        val sp = line.split("=")
        config = config + (sp(0) -> sp(1))
      }
      Option(new AccessToken(config("token"), config("secret")))
    } else {
      None
    }
  }

  //exception
  def browseUrl(url:URI) = {
    val desktop = Desktop.getDesktop
    desktop.browse(url)
  }
}
