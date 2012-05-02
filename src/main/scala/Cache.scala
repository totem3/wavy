import twitter4j._

object Cache {
  val cache:scala.collection.mutable.Map[String, Status] = scala.collection.mutable.Map.empty

  def put(k:String,v:Status) =  {
    cache.put(k,v)
  }

  def get(key:String):Option[Status] = {
    cache.get(key)
  }
}
