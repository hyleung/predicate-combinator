package com.example

import scala.collection.immutable.Stream.Empty

/**
 * Created with IntelliJ IDEA.
 * Date: 15-06-04
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
class PredicateChain[A](chain:Seq[Predicate[A]]) {
	def execute(p:Traversable[A]):Traversable[A]  = {
		@annotation.tailrec
		def exec(p: Traversable[A], c: Seq[Predicate[A]]): Traversable[A] =
			if (c.isEmpty) List.empty
			else {
				val filtered = p.filter(c.head)
				filtered match {
					case Empty => exec(p, c.tail)
					case _ => filtered
				}
			}
		exec(p, chain)
	}
	def length:Int = chain.length

	def ~>(next:Predicate[A]):PredicateChain[A] = new PredicateChain(chain :+ next)
}

class PredictChainFilterable[T](target:Traversable[T]) {
	def filter(pc:PredicateChain[T]):Traversable[T] = pc.execute(target)
}

object PredicateChain {
	def apply[A](p:Predicate[A]) = new PredicateChain(Seq(p))
	def apply[A](ps:Predicate[A]*) = new PredicateChain[A](ps.toList)
	object Implicits {
		implicit def convertToChainFilterable[A](t:Traversable[A]):PredictChainFilterable[A] = new PredictChainFilterable[A](t)
	}
}