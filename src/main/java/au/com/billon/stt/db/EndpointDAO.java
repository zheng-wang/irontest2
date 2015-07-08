package au.com.billon.stt.db;

import au.com.billon.stt.models.Endpoint;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Trevor Li on 6/30/15.
 */
@RegisterMapper(EndpointMapper.class)
public interface EndpointDAO {
    @SqlUpdate("create table IF NOT EXISTS endpoint (id INT PRIMARY KEY auto_increment, name varchar(50) UNIQUE not null, description varchar(500), url varchar(200), username varchar(20), password varchar(20)," +
            "created timestamp DEFAULT CURRENT_TIMESTAMP, updated timestamp DEFAULT CURRENT_TIMESTAMP)")
    void createTableIfNotExists();

    @SqlUpdate("insert into endpoint (name, description, url, username, password) values (:name, :description, :url, :username, :password)")
    @GetGeneratedKeys
    long insert(@BindBean Endpoint endpoint);

    @SqlUpdate("update endpoint set name = :name, description = :description, url = :url, username = :username, password = :password, updated = CURRENT_TIMESTAMP where id = :id")
    int update(@BindBean Endpoint endpoint);

    @SqlUpdate("delete from endpoint where id = :id")
    void deleteById(@Bind("id") long id);

    @SqlQuery("select * from endpoint")
    List<Endpoint> findAll();

    @SqlQuery("select * from endpoint where id = :id")
    Endpoint findById(@Bind("id") long id);
}