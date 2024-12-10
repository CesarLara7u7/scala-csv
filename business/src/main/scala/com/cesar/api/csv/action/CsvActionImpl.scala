package com.cesar.api.csv.action
import akka.http.scaladsl.common.StrictForm
import com.github.tototoshi.csv.CSVReader

import scala.util.Try
import jakarta.inject.{Inject, Singleton}
import jdk.jpackage.internal.IOUtils
import org.slf4j.LoggerFactory

import java.io.{ByteArrayInputStream, File, FileOutputStream}

@Singleton
class CsvActionImpl @Inject()() extends  CsvAction{

  private val log = LoggerFactory.getLogger(getClass)
  override def readCsv(fileData: StrictForm.FileData): Try[Unit] = Try {
    val inputStream = new ByteArrayInputStream(fileData.entity.data.toArray)
    log.info(inputStream.readAllBytes().mkString(","))
  }
}
