/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2009-2010, Jesse Eichar             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scalaio.test.fs

import scala.collection.immutable.Vector

import scalax.test.sugar.AssertionSugar
import scalaio.test.{
  AbstractDirectoryStreamTests, Node
}

import scalax.io._
import Path.AccessModes._

import org.junit.Assert._
import org.junit.{
  Test, Before, After, Rule, Ignore
}
import org.junit.rules.TemporaryFolder
import util.Random

import java.io.IOException

abstract class FsDirectoryStreamTests extends AbstractDirectoryStreamTests with Fixture {
  protected def fixtures(depth:Int=4) : (Path, Node) = fixture.tree(depth)
}