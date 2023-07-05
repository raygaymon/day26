package day26.day26.Repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ShowRepo {
    
    @Autowired
    private MongoTemplate template;

    public List<Document> findShowByName (String name) {
        //db.testing.find({name : "name"})

        //set filter
        Criteria c = Criteria.where("name").is(name);
        System.out.printf("criteria created = %s", name); //setting the filter

        //create mongo query with filter
        Query q = Query.query(c);
        System.out.println("Query created");

        //perform query
        List<Document> result = template.find(q, Document.class, "testing");
        for (Document d : result) {
            System.out.println(d.getString("name"));
        }
        System.out.println("added to list");

        return result;
    }

    public List<Document> findAllShows () {
        return template.findAll(Document.class, "testing");
    }
}
