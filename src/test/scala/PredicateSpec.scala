import com.example.Predicate
import org.scalatest._

class PredicateSpec extends FlatSpec with Matchers {
	behavior of "Predicate operator"
	"'or' operator" should "filter list for elements that satisfy any predicate" in {
		val even = divisibleBy(2)
		val divisibleBy5 = divisibleBy(5)

		List(3, 5, 2).filter(even or divisibleBy5 ) should be (List(5, 2))
		List(3, 5, 7).filter(even or divisibleBy5 ) should be (List(5))
		List(3, 1, 7).filter(even or divisibleBy5 ) should be (List())
	}
	"'and' operator" should "filter list for elements that satisfy all predicates" in {
		val even = divisibleBy(2)
		val divisibleBy5 = divisibleBy(5)

		List(3, 5, 2).filter(even and divisibleBy5 ) should be (List())
		List(3, 5, 7, 10).filter(even and divisibleBy5 ) should be (List(10))
	}
	"! operator" should "negate a predicate" in {
		val even = divisibleBy(2)
		val odd = !even
		val divisibleBy5 = divisibleBy(5)

		List(3, 5, 2).filter(odd and divisibleBy5 ) should be (List(5))
	}
	"example from README" should "actually work" in {
		val even = Predicate[Int](x => x % 2 == 0)
		val divisible_by_5 = Predicate[Int](x => x % 5 == 0)

		List.range(1,11).filter(even and divisible_by_5) should be (List(10))
	}
	"using implicit wrapper" should "also work" in {
		import Predicate.Implicits.predicateConverter
		val even = (x:Int) => x % 2 == 0
		val divisible_by_5 = (x:Int) => x % 5 == 0

		List.range(1,11).filter(even and divisible_by_5) should be (List(10))
	}


	def divisibleBy(i:Int):Predicate[Int] = Predicate( x => x % i == 0)

}
