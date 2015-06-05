import com.example.{Predicate, PredicateChain}
import org.scalatest.{Matchers, FlatSpec}


/**
 * Created with IntelliJ IDEA.
 * Date: 15-06-04
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
class PredicateChainSpec extends FlatSpec with Matchers {
	behavior of "Predicate chain"
	"'~>' operator" should "assemble chain" in {
		val even = divisibleBy(2)
		val divisibleBy5 = divisibleBy(5)
		val chain = even ~> divisibleBy5
		chain.length should be (2)
	}
	"Predicate chain" should "filter lists" in {
		val even = divisibleBy(2)
		val divisibleBy5 = divisibleBy(5)
		val chain = even ~> divisibleBy5
		chain.execute(List(3,4,5,2,10)) should be (List(4,2,10))
		chain.execute(List(3,5,2)) should be (List(2))
		chain.execute(List(3,5,7)) should be (List(5))
		chain.execute(List(3,1,7)) should be (List())
	}
	"Predicate chain with combinators" should "filter lists" in {
		val ten_or_greater:Predicate[Int] = Predicate(x => x >= 10)
		val twenty_or_less:Predicate[Int] = Predicate(x => x < 20)
		val divisibleBy5 = divisibleBy(5)
		val chain = (ten_or_greater and twenty_or_less) ~> divisibleBy5
		chain.execute(Range(1,20)) should be (Range(10,20))
	}
	def divisibleBy(i:Int):Predicate[Int] = Predicate( x => x % i == 0)
}
