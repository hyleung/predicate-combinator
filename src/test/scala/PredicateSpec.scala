import com.example.Predicate
import org.scalatest._

class PredicateSpec extends FlatSpec with Matchers {
	behavior of "|| operator"
	"|| operator" should "filter list for elements that satisfy any predicate" in {
		val even = Predicate[Int]( i => i % 2 == 0 )
		val divisibleBy5 = Predicate[Int]( i => i % 5 == 0)

		List(3, 5, 2).filter(even || divisibleBy5 ) should be (List(5, 2))
		List(3, 5, 7).filter(even || divisibleBy5 ) should be (List(5))
		List(3, 1, 7).filter(even || divisibleBy5 ) should be (List())
	}
}
