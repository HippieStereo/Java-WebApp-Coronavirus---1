package eu.jlpc.controllers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import eu.jlpc.models.LocationStats;
import eu.jlpc.services.CoronaVirusDataService;

@Controller
public class HomeController {

	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<LocationStats> locationStats = coronaVirusDataService.getAllStats();
		
		int totalCases = locationStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		
		BigDecimal bigDecimal = new BigDecimal(totalCases);

		NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));
		
		String totalReportedCases = formatter.format(bigDecimal.longValue());
		
		model.addAttribute("locationStats", locationStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		
		int totalNewCases = locationStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		
		model.addAttribute("totalNewCases", totalNewCases);
		
		return "home";
		
	}
	
}
