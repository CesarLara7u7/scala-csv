package com.cesar.api.job

import jakarta.inject.{Inject, Singleton}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class JobRepositoryImpl @Inject()(database: Database)() extends JobRepository {
  val table: TableQuery[JobTable] = TableQuery[JobTable]


  override def saveOrUpdate(jobEntity: JobEntity): Int = Await.result({
    database.run(table.insertOrUpdate(jobEntity))
  }, Duration.Inf)

  override def getAll: Seq[JobEntity] = Await.result({
    database.run(table.result)
  }, Duration.Inf)

  override def saveJobs(jobs: Seq[JobEntity]): Unit = Await.result({
    database.run(table.insertAll(jobs))
  }, Duration.Inf)
}
