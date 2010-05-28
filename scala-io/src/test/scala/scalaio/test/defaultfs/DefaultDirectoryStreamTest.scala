/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2009-2010, Jesse Eichar             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scalaio.test.defaultfs

import org.junit.Assert._
import org.junit.{Test,Ignore}
import scalax.io._
import scalaio.test.{
  AbstractDirectoryStreamTests, Node
}

class DefaultDirectoryStreamTest extends AbstractDirectoryStreamTests with DefaultFixture{
  protected def fixtures(depth:Int) : (Path, Node) = fixture.tree(depth)
}