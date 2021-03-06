package com.example

/**
 * Created with IntelliJ IDEA.
 * Date: 15-06-02
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
class Predicate[A](val pred:A => Boolean) extends (A => Boolean) {
	def apply(v1: A): Boolean = pred(v1)
	def and(that:A => Boolean):Predicate[A] = new Predicate[A](x => pred(x) && that(x))
	def or(that:A => Boolean):Predicate[A] = new Predicate[A](x => pred(x) || that(x))
	def unary_! = new Predicate[A](x => !pred(x))

	def ~>(next:Predicate[A]):PredicateChain[A] = new PredicateChain(Seq(this, next))
}

object Predicate{
	def apply[A] (p: A => Boolean):Predicate[A] = new Predicate[A](p)
	object Implicits {
		implicit def predicateConverter[A](p: A => Boolean):Predicate[A] = new Predicate[A](p)
	}
}
