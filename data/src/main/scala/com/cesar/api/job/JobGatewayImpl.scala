package com.cesar.api.job

import io.scalaland.chimney.dsl._
import jakarta.inject.{Inject, Singleton}

@Singleton
class JobGatewayImpl @Inject()(repository: JobRepository) extends JobGateway {

  override def saveOrUpdate(job: Job): Int = repository.saveOrUpdate(job.transformInto[JobEntity])

  override def getAll: Seq[Job] = repository.getAll.map(_.transformInto[Job])

  override def saveJobs(jobs: Seq[Job]): Unit = repository.saveJobs(jobs.map(_.transformInto[JobEntity]))
}
