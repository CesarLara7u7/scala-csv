package com.cesar

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.cesar.api.csv.Routes
import com.google.inject.Guice
import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object MainApp {
  private val config: Config = ConfigFactory.load().resolve()

  private def startServer(routes: Route)(implicit
                                         actorSystem: ActorSystem[Nothing], ec: ExecutionContext
  ): Unit = {
    val eventualBinding = Http().newServerAt(getAddres, getPort).bind(routes)
    eventualBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        actorSystem.log.info(s"""Listening server at http://${address.getHostString}:${address.getPort}""")
      case Failure(exception) =>
        actorSystem.log.error("Failed to bind HTTP endpoint, terminating system", exception)
        actorSystem.terminate()
    }
  }

  def main(args: Array[String]): Unit = {
    val module = Guice.createInjector(new Module(getActorSystem))
    val routes = module.getInstance(classOf[Routes]).allRoutes
    startServer(routes)(getActorSystem, getExecutionContext)
  }

  private def getAddres: String = {
    config.getString("app.address")
  }

  private def getPort: Int = {
    config.getInt("app.port")
  }

  private def getExecutionContext: ExecutionContext = getActorSystem.executionContext

  private def getActorSystem: ActorSystem[Nothing] = {
    ActorSystem(Behaviors.empty, "csv-api")
  }

  private def helloWorldRoute: Route = {
    path("hello-world") {
      complete(StatusCodes.OK, "Hola mundo")
    }
  }

}