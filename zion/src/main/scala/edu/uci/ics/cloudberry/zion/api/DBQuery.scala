package edu.uci.ics.cloudberry.zion.api

import org.joda.time.{DateTime, Interval}
import play.api.libs.json._

sealed trait Statement

sealed trait Predicate extends Statement {
  //TODO make a field type
  def fieldName: String
}

// Only working on AND relation so far
case class KeywordPredicate(val fieldName: String, keywords: Seq[String]) extends Predicate {
  require(keywords.forall(_.length > 0))
}

// Only working on Contains relation so far
case class TimePredicate(val fieldName: String, intervals: Seq[Interval]) extends Predicate {
}

// Only use work on the int id so far.
case class IdSetPredicate(val fieldName: String, idSets: Seq[Int]) extends Predicate


case class GroupOn(val fieldNames: String, level: SummaryLevel) extends Statement

case class AggregateOn(aggFunction: AggFunctionTypes.Value, val fieldName: String) extends Statement

case class Groupby(groupOn: GroupOn, aggregateOns: Seq[AggregateOn]) extends Statement

//TODO add the aggregation function parameters, currently only support count
case class DBQuery(summaryLevel: SummaryLevel, val predicates: Seq[Predicate]) extends XQLVisitable {
  override def accept(visitor: XQLVisitor): Unit = visitor.visit(this)
}

class DBUpdateQuery()


