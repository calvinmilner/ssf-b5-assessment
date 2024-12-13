package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers
@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    private NoticeService noticeServ;

    @GetMapping("/")
    public ModelAndView getNotice() {
        ModelAndView mav = new ModelAndView("notice");
        mav.addObject("notice", new Notice());
        return mav;
    }

    @PostMapping("/notice")
    public ModelAndView postNotice(@Valid @ModelAttribute("notice") Notice notice, BindingResult bindings) {
        ModelAndView mav = new ModelAndView();
        // System.out.print(notice.getCategories());
        if (bindings.hasErrors()) {
            mav.setViewName("notice");
            return mav;
        }
        String info = noticeServ.postToNoticeServer(notice);
        // JsonReader readInfo = Json.createReader(new StringReader(info));
        // JsonObject j = readInfo.readObject();
        // System.out.println(notice.getId());
        if (notice.getId() == null) {
            mav.setViewName("error");
            mav.addObject("message", info);
            return mav;
        }

        mav.setViewName("success");
        mav.addObject("id", notice.getId());
        return mav;
    }

    @GetMapping("/status")
    @ResponseBody
    public ResponseEntity<String> getHealth() {
        if (noticeServ.getRandomKey() == true) {
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("{}");
        }
        return ResponseEntity.status(503).contentType(MediaType.APPLICATION_JSON).body("{}");
    }
}
