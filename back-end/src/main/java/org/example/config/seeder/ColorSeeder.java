package org.example.config.seeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.ColorEntity;
import org.example.mainView.dto.ColorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ColorSeeder {

    private final ColorRepository colorRepository;

    @Bean
    public CommandLineRunner seedColors() {
        return args -> {
            if (colorRepository.count() > 0) {
                log.info("색상 데이터가 이미 존재합니다. 시더를 건너뜁니다.");
                return;
            }

            List<String> colorCodes = List.of(
                    "ff0000", // 빨강
                    "00ff00", // 초록
                    "0000ff", // 파랑
                    "ffff00", // 노랑
                    "ff00ff", // 마젠타
                    "00ffff", // 시안
                    "000000", // 검정
                    "ffffff", // 흰색
                    "808080", // 회색
                    "ffa500"  // 주황
            );

            for (String code : colorCodes) {
                ColorEntity color = new ColorEntity();
                color.setName(code);
                colorRepository.save(color);
                log.info("색상 {} 저장 완료", code);
            }
            log.info("총 {}개의 색상 데이터 시딩 완료", colorCodes.size());
        };
    }
}