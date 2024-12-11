package com.cesar.api.csv

import com.google.inject.AbstractModule

class DataModule extends AbstractModule{

  override def configure(): Unit = {

  }

  def connectDatabase(): Unit = {
    new Datab
  }
}
