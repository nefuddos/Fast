package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
	@RequestMapping("/weather/{area}")
	public int getWeather(@PathVariable String area) {
		Weather obj = new Weather();
		return obj.getWeather(area);
	}
}
