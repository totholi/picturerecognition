package ch.zhaw.mdmproject.picturerecognition;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class PictureRecognitionController {

    private Inference inference = new Inference();

    @GetMapping("/ping")
    public String ping() {
        return "Classification app is up and running!";
    }

    @PostMapping(path = "/analyze")
    public String predict(@RequestParam("image") MultipartFile image) throws Exception {
        System.out.println(image);
        return inference.predict(image.getBytes()).toJson();
    }

	@PostMapping(path = "/jdlanalyze")
	public String predictDJL(@RequestParam("image") MultipartFile image) throws Exception {
		InputStream is = new ByteArrayInputStream(image.getBytes());
		var uri = "http://model-service:8080/predictions/restnet18_v1";
		var webClient = WebClient.create();
		InputStreamResource resource = new InputStreamResource(is);
		var result = webClient.post()
							.uri(uri)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.body(BodyInserters.fromResource(resource))
							.retrieve()
							.bodyToMono(String.class)
							.block();
		return result;
	}

}