package com.start.st.domain.chat;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ChatController {
    // 확인용 컨트롤러 삭제 해도 무관
    @GetMapping("/chat")
    public String chatGET(){
        log.info("@ChatController, chat GET()");
        return "chat";
    }
}