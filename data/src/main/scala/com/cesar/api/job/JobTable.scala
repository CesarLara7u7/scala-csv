package com.cesar.api.job

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class JobTable (tag: Tag) extends Table[JobEntity](tag, "job") {

  def id = column[Int]("id", O.PrimaryKey)

  def job = column[String]("job")

  override def * : ProvenShape[JobEntity] = (id,job).mapTo[JobEntity]
}
