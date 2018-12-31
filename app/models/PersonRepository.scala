package models
import java.sql.{Date, Timestamp, Types}

import javax.inject.{Inject, Singleton}
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

import scala.concurrent.{ExecutionContext, Future}
import org.joda.time.{DateTime, DateTimeZone}
import slick.basic.DatabaseConfig
import slick.jdbc.{GetResult, PositionedParameters, SetParameter}
/**
 * A repository for people.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class PersonRepository @Inject()( person: Person)(implicit ec: ExecutionContext) {
  @Inject
  var storeRepository:StoreRepository=_
  implicit def jodaTimeMapping: BaseColumnType[DateTime] = MappedColumnType.base[DateTime, Timestamp](
    dateTime => new Timestamp(dateTime.getMillis),
    timeStamp => new DateTime(timeStamp.getTime)
  )

   class PeopleTable(tag: Tag) extends Table[Person1](tag, "UUIDTest") {

    def id = column[String]("id", O.PrimaryKey,O.AutoInc)

    def name = column[String]("name")

    def storefk=foreignKey("fk_store",storeID,storeRepository.storequery)(_.id)

    def age = column[Int]("age")

    def createdddate = column[DateTime]("date")

    def storeID  = column[String]("store_id")

    def * = (id, name, age,createdddate,storeID) <> ((Person1.apply _).tupled, Person1.unapply)
  }

  private val people = TableQuery[PeopleTable]


  def create(name: String, age: Int,num:Long,store:String): Future[Person1] ={
    import java.util.UUID
    val uuid = UUID.randomUUID.toString
    val date:DateTime= new DateTime(num)
    implicit object SetDateTime extends SetParameter[DateTime] {
      def apply(d: DateTime, p: PositionedParameters): Unit =
        p setTimestamp (new Timestamp(d.getMillis))
    }
    val s=sqlu"insert into UUIDTest values (${uuid},${name},${age},${date},${store})"
    connection.dbConfig.db.run(s)
    Future.successful(Person1(uuid,name,age,date,store))
  }
}
