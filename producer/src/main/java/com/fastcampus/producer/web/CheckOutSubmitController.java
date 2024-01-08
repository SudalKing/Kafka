package com.fastcampus.producer.web;

import com.fastcampus.producer.dto.CheckOutDto;
import com.fastcampus.producer.service.SaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CheckOutSubmitController {

    private final SaveService saveService;

    @PostMapping("/submit-check-out")
    public String submitCheckOut(CheckOutDto checkOutDto, Model model) {
        log.info(checkOutDto.toString());

        Long checkOutId = saveService.saveCheckOutData(checkOutDto);
        model.addAttribute("checkOutId", checkOutId);

        return "submitComplete";
    }
}
