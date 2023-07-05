package day26.day26.Service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import day26.day26.Repository.ShowRepo;

@Service
public class ShowService {
    @Autowired
    ShowRepo repo;
    
    public List<Document> getShowByName (String name) {
        System.out.println("service is running");
        return repo.findShowByName(name);
    }

    public List<Document> getAllShows(){
        System.out.println("Running get all shows");
        return repo.findAllShows();
    }
}
