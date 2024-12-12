package com.cesar.api.job

trait JobRepository {

  def saveOrUpdate(jobEntity: JobEntity): Int

  def getAll: Seq[JobEntity]

  def saveJobs(jobs: Seq[JobEntity]): Unit
}
