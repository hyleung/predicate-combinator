package com.example

/**
 * Created with IntelliJ IDEA.
 * Date: 15-06-02
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
class Predicate[A](val pred:A => Boolean) extends (A => Boolean) {
	def apply(v1: A): Boolean = pred(v1)
	def &&(that:A => Boolean):Predicate[A] = new Predicate[A](x => pred(x) && that(x))
	def ||(that:A => Boolean):Predicate[A] = new Predicate[A](x => pred(x) || that(x))
}

object Predicate{
	def apply[A] (p: A => Boolean):Predicate[A] = new Predicate[A](p)
}
