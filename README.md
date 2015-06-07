#Predicate Combinator + Chains

Implements Predicate combinator operators (as described [here](http://frankthomas.name/blog/2012/08/combining_predicates_in_scala/)), 
as well as predicate chain filtering. 

##Predicates

###Usage

	val even = Predicate[Int](x => x % 2 == 0)
	val divisible_by_5 = Predicate[Int](x => x % 5 == 0)
	
	List.range(1,11).filter(even and divisible_by_5) should be (List(10))

###Function literal implicit conversion 

	import Predicate.Implicits.predicateConverter
	
	val even = (x:Int) => x % 2 == 0
	val divisible_by_5 = (x:Int) => x % 5 == 0
	
	List.range(1,11).filter(even and divisible_by_5) should be (List(10))

##Predicate Chains

###Usage

	val chain = PredicateChain(even, divisibleBy5)
	chain.length should be (2)
	
or...
	
	val chain = even ~> divisibleBy5
	chain.length should be (2)
	
###Implicit conversion of `Traversible[A]` 

Automagically adds `filter(PredicateChain[A]):Traversible[A]` on any `Traversible[A]`.

	import PredicateChain.Implicits.convertToChainFilterable
	
	val chain = even ~> divisibleBy5
	List(3,4,5,2,10).filter(chain) should be (List(4,2,10))
	
###The _"Kitchen Sink"_ example
	
Uses *both* implicit conversions, plus the fancy `~>` operator (yes, I'm going to Scala abuse hell).
	
	import PredicateChain.Implicits.convertToChainFilterable
	import Predicate.Implicits.predicateConverter
	
	List(3,4,5,2,10)
		.filter(
			((x:Int) => x % 2 == 0) ~>
			((y:Int) => y % 5 == 0)) should be (List(4,2,10))
