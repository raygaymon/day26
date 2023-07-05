package day26.day26.Controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import day26.day26.Model.Show;
import day26.day26.Service.ShowService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/findShow")
public class ShowController {
    @Autowired
    ShowService service;

    @GetMapping
    public String homepage () {
        return "homepage";
    }

    @PostMapping("/finding")
    public String getShowWithName (@RequestBody String name, Model m, HttpSession session) {

        String toSearch = name.replace("+", " ").trim();
		List<Document> docs = service.getShowByName(toSearch.substring(5));
        for (Document d : docs) {
            System.out.println(d.getString("name"));
        }
        System.out.println(toSearch);

        if(docs.isEmpty()) {
            m.addAttribute("error", "Show with that name does not exist");
            return "homepage";
        } else {
            session.setAttribute("docs", docs);
            System.out.println("its working");
            return "redirect:/findShow/results";
        }
    }

    @GetMapping("/findAll")
    public String getAllShows (Model m, HttpSession session) {

		List<Document> docs = service.getAllShows();

        if(docs.isEmpty()) {
            m.addAttribute("error", "Something wrong here");
            return "homepage";
        } else {

            List<Show> showsFound = new ArrayList<>();

            for (Document d : docs){
                Show s = new Show();
                s.setName(d.getString("name"));
                s.setType(d.getString("type"));
                s.setRuntime(d.getInteger("runtime"));
                //get genres
                s.setGenre(d.getList("genres", String.class));

                Document url = d.get("image", Document.class);
                s.setImageURL(url.getString("medium"));

                //get average rating
                Document rating = d.get("rating", Document.class);
                
                s.setAverageRating(rating.get("average"));
                showsFound.add(s);
            }

            if(showsFound.isEmpty()){
                System.out.println("somethign fucked up");

                m.addAttribute("error", "name posted but nothing came back so either movie no exist or smth fucked up");
                return "homepage";
            } else {
                m.addAttribute("showsFound", showsFound);
                System.out.println("Show list added");
                return "results";
            }
        }
    }

    @GetMapping("/results")
    public String displayShows (Model m, HttpSession session) {
        List<Document> docs = (List<Document>) session.getAttribute("docs");
        List<Show> showsFound = new ArrayList<>();

        for (Document d : docs){
            Show s = new Show();
            s.setName(d.getString("name"));
            s.setType(d.getString("type"));
            s.setRuntime(d.getInteger("runtime"));
            //get genres
            s.setGenre(d.getList("genres", String.class));

            Document url = d.get("image", Document.class);
            s.setImageURL(url.getString("medium"));

            //get average rating
            Document rating = d.get("rating", Document.class);
            s.setAverageRating(rating.get("average"));
            showsFound.add(s);
        }

        if(showsFound.isEmpty()){
            System.out.println("somethign fucked up");

            m.addAttribute("error", "name posted but nothing came back so either movie no exist or smth fucked up");
            return "homepage";
        } else {
            m.addAttribute("showsFound", showsFound);
            System.out.println("Show list added");
            return "results";
        }

        
    }
}
