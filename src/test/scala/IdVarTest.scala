import org.scalatest.FunSuite
 
class IdVarTest extends FunSuite {
 
  test("next should return next var") {
    val result = IdVar.next("aa") 
    assert(result === "ab")
  }

  test("next of xz should be (x+1)a") {
    val result = IdVar.next("az")
    assert(result === "ba")
  }

  test("nextChar should return (c+1)") {
    assert(IdVar.nextChar('a') === "b")
  }

  test("nextChar of z should be a") {
    assert(IdVar.nextChar('z') === "a")
  }
}
