package com.cesar

import com.cesar.api.ApiModule
import com.google.inject.AbstractModule

class Module extends AbstractModule{

  override def configure(): Unit = {

    install(new ApiModule())
  }
}
