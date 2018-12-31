package models

import java.sql.Timestamp

import javax.inject.Inject
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import slick.jdbc.meta.MTable
import slick.jdbc.{PositionedParameters, SetParameter}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
class StoreRepository  @Inject()(store: store )(implicit ec: ExecutionContext){
  protected implicit def implicitPersonWrites = store.writes
  implicit def jodaTimeMapping: BaseColumnType[DateTime] = MappedColumnType.base[DateTime, Timestamp](
    dateTime => new Timestamp(dateTime.getMillis),
    timeStamp => new DateTime(timeStamp.getTime)
  )

   class PeopleTable(tag: Tag) extends Table[store1](tag, "store") {

    def id = column[String]("Id", O.PrimaryKey)

    def name = column[String]("name")

    def address = column[String]("age")

    def createdddate = column[DateTime]("date")

    def * = (id, name, address) <> ((store1.apply _).tupled, store1.unapply)
  }

  val storequery = TableQuery[PeopleTable]

  def create(name: String, address: String): Future[store1] ={
    import java.util.UUID
    val uuid = UUID.randomUUID.toString
    //val date:DateTime= new DateTime(ZoneOffset.UTC)
    val tablesExist: DBIO[Boolean] = MTable.getTables.map { tables =>
      val names = Vector(storequery.baseTableRow.tableName)
      names.intersect(tables.map(_.name.name)) == names
    }
      def createTableIfNotInTables(tables: Vector[MTable]): Future[Unit] = {
        if (!tables.exists(_.name.name == storequery.baseTableRow.tableName)) {
          connection.db.run(storequery.schema.create)
        } else {
          Future()
        }
      }
      val createTableIfNotExist: Future[Unit] = connection.db.run(MTable.getTables).flatMap(createTableIfNotInTables)
      Await.result(createTableIfNotExist, Duration.Inf)
    implicit object SetDateTime extends SetParameter[DateTime] {
      def apply(d: DateTime, p: PositionedParameters): Unit =
        p setTimestamp (new Timestamp(d.getMillis))
    }
    val s=sqlu"insert into store values (${uuid},${name},${address})"
    connection.dbConfig.db.run(s)
    Future.successful(store1(uuid,name,address))
  }
}
