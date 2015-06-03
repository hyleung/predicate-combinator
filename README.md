#Predicate Combinator

Just for practice - implements Predicate combinator operators as described [here](http://frankthomas.name/blog/2012/08/combining_predicates_in_scala/).

##Usage

	val even = Predicate[Int](x => x % 2 == 0)
	val divisible_by_5 = Predicate[Int](x => x % 5 == 0)
	
	List.range(1,11).filter(even and divisible_by_5) should be (List(10))

##With _implicit_ conversion

	import Predicate.Implicits.predicate
	val even = (x:Int) => x % 2 == 0
	val divisible_by_5 = (x:Int) => x % 5 == 0
	
	List.range(1,11).filter(even and divisible_by_5) should be (List(10))
