package com.cesar.api.job

trait JobGateway {

  def saveOrUpdate(job: Job): Int

  def getAll: Seq[Job]

  def saveJobs(jobs:Seq[Job]):Unit

}
