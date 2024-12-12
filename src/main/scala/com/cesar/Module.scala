package com.cesar

import akka.actor.typed.ActorSystem
import com.cesar.api.{ApiModule, BusinessModule, DataModule}
import com.google.inject.{AbstractModule, Provides}
import com.typesafe.config.Config

class Module(ac: ActorSystem[Nothing],config: Config) extends AbstractModule{

  override def configure(): Unit = {

    install(new ApiModule())
    install(new BusinessModule())
    install(new DataModule(config))
  }
  @Provides
  implicit def actorSystem: ActorSystem[Nothing] = ac
}
