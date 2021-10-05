package eu.jlpc.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import eu.jlpc.models.LocationStats;

@Service
public class CoronaVirusDataService {

	private final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<LocationStats> allStats = new ArrayList<>(); 
	
	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		
		List<LocationStats> newStats = new ArrayList<>(); 
		
		HttpClient httpClient = HttpClient.newHttpClient();
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(VIRUS_DATA_URL))
			.build();
		
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		StringReader in = new StringReader(httpResponse.body()); 
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		
		for (CSVRecord record : records) {
			
			LocationStats locationStats = new LocationStats();
			
			locationStats.setProvinceState(record.get("Province/State"));
			locationStats.setCountryRegion(record.get("Country/Region"));
			
			int totalLastDay = Integer.parseInt(record.get(record.size() - 1));
			int totalDayBeforeLast = Integer.parseInt(record.get(record.size() - 2));

			locationStats.setLatestTotalCases(totalLastDay);
			locationStats.setDiffFromPrevDay(totalLastDay - totalDayBeforeLast);
			
		    newStats.add(locationStats);
		    
		}
		
		this.allStats = newStats;
		
	}

	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
}
