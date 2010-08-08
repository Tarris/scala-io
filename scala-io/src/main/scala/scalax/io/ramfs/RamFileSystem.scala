/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2009-2010, Jesse Eichar             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scalax.io.ramfs

import scala.resource.ManagedResource
import scalax.io.{
  FileSystem, Path, FileOps,Codec,PathMatcher,DirectoryStream
}
import scalax.io.attributes.FileAttribute

import Path.AccessModes._
import java.io.FileNotFoundException
import java.net.{
  URL,URI
}
import java.util.UUID

class RamFileSystem extends FileSystem {
  private var fsTree = new DirNode(separator)
  
  val id = UUID.randomUUID
  val root = new RamPath("",fsTree.name, this)
  var pwd = root
  
  def separator: String = "/"
  def apply(path: String): RamPath = {
    if(path startsWith separator) apply("",path)
    else apply(pwd.toAbsolute.path, path)
  }
  def apply(relativeTo:String , path: String): RamPath = {
    def process(path:String) = {
      val p = path.replaceAll(separator+"+", separator)
      if((p endsWith separator) && (p.length > 1)) p.drop(1)
      else p
    }
    new RamPath(process(relativeTo), process(path), this)
  } 
  def roots:List[RamPath] = List (root)
  def createTempFile(prefix: String = randomPrefix, 
                   suffix: String = null, 
                   dir: String = null,
                   deleteOnExit : Boolean = true
                   /*attributes:List[FileAttributes] TODO */ ) : Path = new RamPath("temp",UUID.randomUUID.toString,this)

  def createTempDirectory(prefix: String = randomPrefix,
                        suffix: String = null, 
                        dir: String = null,
                        deleteOnExit : Boolean = true
                        /*attributes:List[FileAttributes] TODO */) : Path  = new RamPath("temp",UUID.randomUUID.toString,this)
                        
  def matcher(pattern:String, syntax:String = PathMatcher.StandardSyntax.GLOB): PathMatcher = null // TODO

  def uri(path:RamPath = root):URI = new URI("ramfs://"+id+path)
  override def toString = "Ram File System"
  
  private[ramfs] def lookup(path:RamPath) = {
    val absolutePath = "/" :: path.toAbsolute.segments
    fsTree.lookup(absolutePath)
  }
  private[ramfs] def create(path:RamPath, fac:NodeFac, createParents:Boolean = true) : Boolean = {
    val absolute = path.toAbsolute
    absolute.parent match {
      case Some(p) if p.notExists && !createParents => 
        throw new FileNotFoundException("Parent directory "+p+" does not exist")
      case _ => ()
    }

    val x = fsTree.create(absolute.segments,fac)
    true
  }
  private[ramfs] def delete(path:RamPath, force:Boolean) : Boolean = {
    if(path.exists) {
      def delete(p:Path) = force || (p.canWrite && p.parent.forall {_.canWrite})
    
      if(delete(path) && path != root) {
        val deletions = for { parent <- path.parent
              parentNode <- parent.node
              node <- path.node
            } yield {
              parentNode.asInstanceOf[DirNode].children -= node
              true
            }
        deletions.isDefined
      } else if(path == root) {
        fsTree = new DirNode(separator)
        true
      } else {
        println("1-cannot delete "+path+" force: "+force)
        false
      }
    } else {
      println("2-cannot delete "+path)
      false
    }
  }
  

}
