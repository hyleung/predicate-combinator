import com.example.{Predicate, PredicateChain}
import org.scalatest.{Matchers, FlatSpec}


/**
 * Created with IntelliJ IDEA.
 * Date: 15-06-04
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
class PredicateChainSpec extends FlatSpec with Matchers {
	val even = divisibleBy(2)
	val divisibleBy5 = divisibleBy(5)
	behavior of "Predicate chain"
	it should "be built from a Seq of predicate" in {
		val chain = new PredicateChain(Seq(even, divisibleBy5))
		chain.length should be (2)
	}

	it should "be built from a * of predicate" in {
		val chain = PredicateChain(even, divisibleBy5)
		chain.length should be (2)
	}
	it should "be built from use of the ~> operator" in {
		val chain = even ~> divisibleBy5
		chain.length should be (2)
	}
	it should "filter lists" in {
		val even = divisibleBy(2)
		val divisibleBy5 = divisibleBy(5)
		val chain = PredicateChain(even, divisibleBy5)
		chain.execute(List(3,4,5,2,10)) should be (List(4,2,10))
		chain.execute(List(3,5,2)) should be (List(2))
		chain.execute(List(3,5,7)) should be (List(5))
		chain.execute(List(3,1,7)) should be (List())
	}
	it should "filter lists with combinators" in {
		val ten_or_greater:Predicate[Int] = Predicate(x => x >= 10)
		val twenty_or_less:Predicate[Int] = Predicate(x => x < 20)
		val divisibleBy5 = divisibleBy(5)
		val chain = (ten_or_greater and twenty_or_less) ~> divisibleBy5
		chain.execute(Range(1,20)) should be (Range(10,20))
	}
	it should "convert Traversable to 'chain filterable'" in {
		import PredicateChain.Implicits.convertToChainFilterable
		val chain = even ~> divisibleBy5
		List(3,4,5,2,10).filter(chain) should be (List(4,2,10))
	}
	it should "work with the kitchen sink" in {
		import PredicateChain.Implicits.convertToChainFilterable
		import Predicate.Implicits.predicateConverter
		List(3,4,5,2,10)
			.filter(
				((x:Int) => x % 2 == 0) ~>
				((y:Int) => y % 5 == 0)) should be (List(4,2,10))
	}

	def divisibleBy(i:Int):Predicate[Int] = Predicate( x => x % i == 0)
}
