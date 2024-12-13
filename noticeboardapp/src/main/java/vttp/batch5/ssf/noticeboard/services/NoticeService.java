package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepo;

	@Value("${publishing.url}")
	private String url;
	// TODO: Task 3
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type
	public String postToNoticeServer(Notice n) {
		long dateToLong = n.getPostDate().getTime();
		JsonArrayBuilder categoriesArr = Json.createArrayBuilder();
		for (String s : n.getCategories())
			categoriesArr.add(s);

		JsonObject j = Json.createObjectBuilder()
				.add("title", n.getTitle())
				.add("poster", n.getPoster())
				.add("postDate", dateToLong)
				.add("categories", categoriesArr.build())
				.add("text", n.getText())
				.build();

		RequestEntity<String> req = RequestEntity
				.post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(j.toString(), String.class);
				// System.out.printf(">>>REQ: %s", req);

		RestTemplate template = new RestTemplate();
		try {
			ResponseEntity<String> resp = template.exchange(req, String.class);
			String payload = resp.getBody();
			JsonReader reader = Json.createReader(new StringReader(payload));
			JsonObject jObj = reader.readObject();

			if (jObj.containsKey("id")) {
				noticeRepo.insertNotices(jObj);
				n.setId(jObj.getString("id"));
				// System.out.printf(">>>JSON String: %s", jObj.toString());
				return jObj.toString();
			} else {
				return jObj.getString("message");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public void insertNotices(JsonObject json) {
		noticeRepo.insertNotices(json);
	}

	public boolean getRandomKey() {
		boolean isSuccess = false;
		try {
			noticeRepo.getRandomKey();
			isSuccess = true;
			return isSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			return isSuccess;
		}
	}
}
