/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003-2009, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scalax.io

import java.io.Closeable

/**
 * An iterable that permits iterating over a directory tree starting at a root Path.  
 * <p>
 * When a method is called the root Path is checked to determine if it is a Directory.  If not
 * a NotDirectoryException is thrown.
 * </p>
 * <p>
 * If an IOException is encountered while iterating a ConcurrentModificationException is thrown with
 * case IOException
 * </p>
 * @see NotDirectoryException
 */
abstract class DirectoryStream[T] extends Iterable[T] {
  /**
   * Iterates over the contents of the directory passing each element to the
   * function.
   * <p>
   * The partial function does not need to be complete, all Path's that do not have matches in the function
   * will be ignored.  For example: <code>contents {case File(p)=>println(p+" is a file")}</code> would match
   * all Files.  To assist in matching Paths see the {@link Extractors} and
   * {@link FileSystem.matcher(String,String)}
   * </p>
   * @param function the function that is used to process each entry in the directory
   *
   * @return nothing
   *
   * @see Path.Matching
   * @see FileSystem#matcher(String,String)
   */
  def filterEach (function: PartialFunction[Path,Unit]): Unit

  /**
   * Iterates over the contents of the directory passing each element to the
   * function and returns the result of the computation.
   * <p>
   * The partial function does not need to be complete, all Path's that do not have matches in the function
   * will be ignored.  For example: <code>contents {case File(p)=>println(p+" is a file")}</code> would match
   * all Files.  To assist in matching Paths see the {@link Extractors} and
   * {@link FileSystem.matcher(String,String)}
   * </p>
   *
   * @param initial the value that is passed to the first call of function
   * @param function the function that is used to process each entry in the directory
   *
   * @return The result from the last call to PartialFunction or None if there were no matches
   *
   * @see #filterEach(PartialFunction)
   * @see Path.Matching
   * @see FileSystem#matcher(String,String)
   */
  def filterFold[R] (initial:R)(function: PartialFunction[(R, Path),R]): Option[R]
}

/**
 * A DirectoryStream that defines operations on files that are located relative to an open directory.
 * A SecureDirectoryStream is intended for use by sophisticated or security sensitive applications
 * requiring to traverse file trees or otherwise operate on directories in a race-free manner.
 * Race conditions can arise when a sequence of file operations cannot be carried out in isolation.
 * Each of the file operations defined by this interface specify a relative path. All access to the file
 * is relative to the open directory irrespective of if the directory is moved or replaced by an attacker
 * while the directory is open. A SecureDirectoryStream may also be used as a virtual working directory.
 */
abstract class SecureDirectoryStream[T] extends DirectoryStream[T] {
  // TODO methods
}